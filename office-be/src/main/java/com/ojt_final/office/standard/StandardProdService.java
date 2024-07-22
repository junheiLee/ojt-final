package com.ojt_final.office.standard;


import com.ojt_final.office.global.batch.BatchProcessor;
import com.ojt_final.office.global.batch.BatchResult;
import com.ojt_final.office.global.constant.ResultCode;
import com.ojt_final.office.global.dto.BaseResponse;
import com.ojt_final.office.global.dto.search.CondParam;
import com.ojt_final.office.global.excel.ExcelProcessingHandler;
import com.ojt_final.office.global.exception.DatabaseOperationException;
import com.ojt_final.office.global.exception.ImageStorageException;
import com.ojt_final.office.global.exception.ResourceNotFoundException;
import com.ojt_final.office.global.search.StandardProdCond;
import com.ojt_final.office.link.dto.UploadExcelResponse;
import com.ojt_final.office.standard.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static com.ojt_final.office.global.constant.CommonConst.BATCH_SIZE;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StandardProdService extends ExcelProcessingHandler<StandardProd> {

    private static final String PATH = "C:\\Users\\user\\Desktop\\자료\\OJT\\project\\ojt-final\\office-fe\\public\\assets\\standardImage/";

    private final BatchProcessor batchProcessor;
    private final StandardProdDao standardProdDao;

    @Override
    public Class<StandardProd> getTargetDomain() {
        return StandardProd.class;
    }

    /**
     * Excel 파일을 파싱해 기준 상품 데이터를 저장한다.
     *
     * @param excelFile the Excel file to be parsed
     * @return response containing the result of the upload
     * @throws IOException if an I/O error occurs, particularly when checking the file extension with {@code excelFile.getOriginalFilename()}
     */
    public UploadExcelResponse importExcel(MultipartFile excelFile) throws IOException {

        List<StandardProd> standardProds = parse(excelFile);
        BatchResult batchResult = saveAll(standardProds);

        return UploadExcelResponse.builder()
                .code(ResultCode.UPLOAD)
                .batchResult(batchResult)
                .build();
    }

    public byte[] exportExcel(CondParam condParam) {

        StandardProdCond cond = condParam.toStandardProdCond();
        List<StandardProd> prods = findAllByCond(cond);

        return create(prods);
    }

    public StandardProdsResponse searchWithCount(CondParam condParam) {

        StandardProdCond cond = condParam.toStandardProdCond();
        int count = standardProdDao.countByCond(cond);
        List<StandardProd> prods = findAllByCond(cond);

        return StandardProdsResponse.builder()
                .resultCode(ResultCode.SUCCESS)
                .totalItemsCount(count)
                .prods(prods)
                .build();
    }

    /**
     * 한 건의 기준 상품 데이터를 저장하고, 이미지를 로컬 서버에 저장한다.
     *
     * @param createRequest the request containing the details of the standard product to create.
     * @param imageFile     the image file to associate with the standard product.
     * @return a response indicating the result of the operation.
     */
    @Transactional
    public BaseResponse save(CreateStandardProdRequest createRequest, MultipartFile imageFile) {

        int standardCode = standardProdDao.selectLastSeq() + 1;
        String imageUrl = saveImageFile(standardCode, imageFile);

        StandardProd createProd = createRequest.toEntity(standardCode, imageUrl);
        int affectedRow = standardProdDao.insert(createProd);

        if (affectedRow <= 0) {
            throw new DatabaseOperationException("기준 상품 DB 저장 실패");
        }
        return new BaseResponse(ResultCode.CREATED);
    }

    /**
     * 한 건의 기준 상품 코드를 조회한다.
     *
     * @param code the code of the product.
     * @return a {@link GetStandardProdResponse} object containing the details of the standard product.
     * *         If no product is found with the given codes, the returned object may be null.
     */
    public GetStandardProdResponse get(int code) {

        return new GetStandardProdResponse(ResultCode.SUCCESS, find(code));
    }

    /**
     * 특정 기준 상품을 수정한다.
     * <p>
     * 이미지 파일이 있는 경우 해당 파일을 삭제한 후, 새로운 이미지파일을 저장한다.
     * </p>
     *
     * @param code          the code of the product.
     * @param updateRequest the request containing the details of the standard product to update.
     * @param imageFile     the image file to associate with the standard product.
     * @return a response indicating the result of the operation.
     */
    @Transactional
    public BaseResponse edit(int code, UpdateStandardProdRequest updateRequest, MultipartFile imageFile) {

        StandardProd oldProd = find(code);
        String imageUrl = "";

        if (!imageFile.isEmpty() && !oldProd.getImageUrl().isBlank()) {
            removeImageFile(oldProd.getImageUrl());
            imageUrl = saveImageFile(code, imageFile);
        }
        int affectedRow = standardProdDao.update(updateRequest.toEntity(code, imageUrl));
        if (affectedRow <= 0) {
            throw new DatabaseOperationException("상품 삭제 실패");
        }
        return new BaseResponse(ResultCode.SUCCESS);
    }

    /**
     * 링크된 협력사 상품을 바탕으로 최저가 및 업체를 갱신한다.
     *
     * @param standardProdCodes a list of standard product codes to be renewed
     * @return the number of updated standard products
     */
    public int renewIntegrated(List<Integer> standardProdCodes) {

        Set uniqueStandardCodes = new HashSet(standardProdCodes);
        return standardProdDao.renewIntegrated(uniqueStandardCodes);
    }

    /**
     * 협력사 상품을 삭제한다.
     *
     * @param code the code of the product.
     * @return a response indicating the result of the operation.
     */
    @Transactional
    public DeleteStandardProdResponse delete(int code) {
        StandardProd deletedProd = find(code);

        if (!deletedProd.getImageUrl().isBlank()) {
            removeImageFile(deletedProd.getImageUrl());
        }
        int deletedRow = standardProdDao.delete(code);

        if (deletedRow <= 0) {
            throw new DatabaseOperationException("상품 삭제 실패");
        } else if (deletedRow == 1) {
            return new DeleteStandardProdResponse(ResultCode.SUCCESS, 0);
        } else {
            return new DeleteStandardProdResponse(ResultCode.SUCCESS, deletedRow - 1);
        }
    }

    /**
     * Saves all StandardProd entities in batch mode.
     *
     * @param standardProds the list of StandardProd entities to be saved
     * @return result of the batch save operation
     */
    private BatchResult saveAll(List<StandardProd> standardProds) {

        int previousCount = standardProdDao.countAll(); // 생성된 데이터 수를 구하기 위한 이전 데이터 수
        return batchProcessor.save(BATCH_SIZE, standardProds, standardProdDao::insertAll)
                .calInsertAndUnchangedCount(previousCount, standardProdDao.countAll());
    }

    private List<StandardProd> findAllByCond(StandardProdCond cond) {

        return standardProdDao.selectByCond(cond);
    }

    private StandardProd find(int code) {

        Optional<StandardProd> standardProdOpt = findOpt(code);

        if (standardProdOpt.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        return standardProdOpt.get();
    }

    private Optional<StandardProd> findOpt(int code) {

        return standardProdDao.select(code);
    }

    private String saveImageFile(int standardCode, MultipartFile imageFile) {

        if (imageFile.isEmpty()) {
            throw new MultipartException("빈 이미지 파일 저장 시도");
        }
        String extension = Objects.requireNonNull(imageFile.getContentType()).split("/")[1];
        String imageName = standardCode + "." + extension;
        String fileName = PATH + imageName;

        saveFile(imageFile, fileName);
        return fileName;
    }

    private static void saveFile(MultipartFile imageFile, String fileName) {
        try {
            File file = new File(fileName);
            imageFile.transferTo(file);

        } catch (Exception e) {
            throw new ImageStorageException(e);
        }
    }

    private boolean removeImageFile(String fileName) {
        boolean isRemoved = false;
        try {
            File file = new File(fileName);
            isRemoved = file.delete();

        } catch (Exception e) {
            throw new ImageStorageException(e);
        }
        return isRemoved;
    }
}
