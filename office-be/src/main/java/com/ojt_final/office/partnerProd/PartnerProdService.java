package com.ojt_final.office.partnerProd;

import com.ojt_final.office.global.batch.BatchProcessor;
import com.ojt_final.office.global.batch.BatchResult;
import com.ojt_final.office.global.constant.ResultCode;
import com.ojt_final.office.global.dto.search.CondParam;
import com.ojt_final.office.global.excel.ExcelProcessingHandler;
import com.ojt_final.office.global.exception.DatabaseOperationException;
import com.ojt_final.office.global.exception.DuplicateIdentifierException;
import com.ojt_final.office.global.exception.ResourceNotFoundException;
import com.ojt_final.office.global.search.PartnerProdCond;
import com.ojt_final.office.link.dto.UploadExcelResponse;
import com.ojt_final.office.partnerProd.dto.CreatePartnerProdRequest;
import com.ojt_final.office.partnerProd.dto.CreatePartnerProdResponse;
import com.ojt_final.office.partnerProd.dto.GetPartnerProdResponse;
import com.ojt_final.office.partnerProd.dto.PartnerProdsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.ojt_final.office.global.constant.CommonConst.BATCH_SIZE;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PartnerProdService extends ExcelProcessingHandler<PartnerProd> {

    private final BatchProcessor batchProcessor;
    private final PartnerProdDao partnerProdDao;

    @Override
    public Class<PartnerProd> getTargetDomain() {
        return PartnerProd.class;
    }

    /**
     * Excel 파일을 파싱해 협력사 상품 데이터를 저장한다.
     *
     * @param excelFile the Excel file to be parsed
     * @return response containing the result of the upload
     * @throws IOException if an I/O error occurs, particularly when checking the file extension with {@code excelFile.getOriginalFilename()}
     */
    public UploadExcelResponse importExcel(MultipartFile excelFile) throws IOException {

        List<PartnerProd> partnerProds = parse(excelFile);
        BatchResult batchResult = saveAll(partnerProds);

        return UploadExcelResponse.builder()
                .code(ResultCode.UPLOAD)
                .batchResult(batchResult)
                .build();
    }

    public byte[] exportExcel(CondParam condParam) {

        PartnerProdCond cond = condParam.toPartnerProdCond();
        List<PartnerProd> prods = findAllByCond(cond);

        return create(prods);
    }

    public PartnerProdsResponse searchWithCount(CondParam condParam) {

        PartnerProdCond cond = condParam.toPartnerProdCond();
        int count = partnerProdDao.countByCond(cond);
        List<PartnerProd> prods = findAllByCond(cond);

        return PartnerProdsResponse.builder()
                .resultCode(ResultCode.SUCCESS)
                .totalItemsCount(count)
                .prods(prods)
                .build();
    }

    /**
     * 새로운 협력사 상품을 저장한다.
     *
     * <p>
     * This method converts the request DTO to a PartnerProd entity, validates that there
     * are no duplicates, and attempts to insert the entity into the database.
     * </p>
     *
     * @param createPartnerProdRequest the request containing the PartnerProd data
     * @return the response containing the result code and identity of the created PartnerProd
     * @throws DuplicateIdentifierException if a duplicate PartnerProd is found
     * @throws DatabaseOperationException   if the database operation fails to insert the PartnerProd
     */
    public CreatePartnerProdResponse save(CreatePartnerProdRequest createPartnerProdRequest) {

        PartnerProd partnerProd = createPartnerProdRequest.toEntity();
        validateNoDuplicate(partnerProd);
        int count = partnerProdDao.insert(partnerProd);

        if (count <= 0) {
            throw new DatabaseOperationException("협력사 상품 DB 저장 실패");
        }
        return new CreatePartnerProdResponse(ResultCode.SUCCESS, partnerProd);
    }

    private void validateNoDuplicate(PartnerProd partnerProd) {

        findOpt(partnerProd).ifPresent(existingProd -> {
            throw new DuplicateIdentifierException();
        });
    }

    /**
     * 협력사 코드와 해당 상품 코드로 협력사 상품을 조회한다.
     *
     * @param partnerCode the code of the partner. This parameter is used to identify the partner.
     * @param code        the code of the product. This parameter is used to identify the specific product.
     * @return a {@link GetPartnerProdResponse} object containing the details of the partner product.
     * *         If no product is found with the given codes, the returned object may be null.
     */
    public GetPartnerProdResponse get(String partnerCode, String code) {

        PartnerProd searchProd = PartnerProd.builder().partnerCode(partnerCode).code(code).build();
        Optional<PartnerProd> partnerProdOpt = partnerProdDao.select(searchProd);

        if (partnerProdOpt.isEmpty()) {
            throw new ResourceNotFoundException();
        } else {
            return new GetPartnerProdResponse(ResultCode.SUCCESS, partnerProdOpt.get());
        }
    }

    /**
     * 특정 협력사 상품을 수정한 후 기준 상품 갱신이 필요한지 여부를 결정한다.
     *
     * @param partnerProd the partner product entity to be updated
     * @return {@code true} if the updated partner product requires an integrated renewal;
     *         {@code false} otherwise
     * @throws ResourceNotFoundException if the partner product is not found in the database
     */
    public boolean requiresRenewalAfterUpdate(PartnerProd partnerProd) {
        Optional<PartnerProd> partnerProdOpt = findOpt(partnerProd);

        if (partnerProdOpt.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        partnerProdDao.update(partnerProd);
        return partnerProd.requiresIntegratedProgram(partnerProdOpt.get());
    }

    /**
     * 파트너 상품 코드에 해당하는 모든 링크 여부 변경
     * TODO: 배치 필요 여부 고민하기
     *
     * @param isLinked 연결 여부
     * @param codes    대상 파트너 상품 코드 리스트
     * @return 변경된 데이터 수
     */
    public int updateAllIsLinked(boolean isLinked, List<String> codes) {

        return partnerProdDao.updateAllIsLinked(isLinked, codes);
    }

    public ResultCode checkIfLinked(PartnerProd partnerProd) {
        Optional<PartnerProd> prodOpt = findOpt(partnerProd);
        if (prodOpt.isEmpty()) {
            throw new RuntimeException("임시 잘못된 사용자 요청: 존재하는 리소스가 없음:404 ");
        }
        if (prodOpt.get().isLinked()) {
            return ResultCode.LINKED;
        } else {
            return ResultCode.UNLINKED;
        }

    }

    public void delete(PartnerProd partnerProd) {

        int deletedRow = partnerProdDao.delete(partnerProd);

        if (deletedRow <= 0) {
            throw new RuntimeException("임시 항복을 삭제하지 못했다.");
        }
    }

    /**
     * Saves all PartnerProd entities in batch mode.
     *
     * @param partnerProds the list of PartnerProd entities to be saved
     * @return result of the batch save operation
     */
    private BatchResult saveAll(List<PartnerProd> partnerProds) {
        int previousCount = partnerProdDao.countAll();   // 생성된 데이터 수를 구하기 위한 이전 데이터 수
        return batchProcessor
                .save(BATCH_SIZE, partnerProds, partnerProdDao::insertAll)
                .calInsertAndUnchangedCount(previousCount, partnerProdDao.countAll());
    }

    private List<PartnerProd> findAllByCond(PartnerProdCond cond) {

        return partnerProdDao.selectAllByCond(cond);
    }

    private Optional<PartnerProd> findOpt(PartnerProd partnerProd) {

        return partnerProdDao.select(partnerProd);
    }

}
