package com.ojt_final.office.standard;


import com.ojt_final.office.global.batch.BatchProcessor;
import com.ojt_final.office.global.batch.BatchResult;
import com.ojt_final.office.global.constant.ResultCode;
import com.ojt_final.office.global.dto.BaseResponse;
import com.ojt_final.office.global.dto.search.CondParam;
import com.ojt_final.office.global.excel.ExcelProcessingHandler;
import com.ojt_final.office.global.search.StandardProdCond;
import com.ojt_final.office.link.dto.UploadExcelResponse;
import com.ojt_final.office.standard.dto.CreateStandardProdRequest;
import com.ojt_final.office.standard.dto.StandardProdsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.ojt_final.office.global.constant.CommonConst.BATCH_SIZE;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StandardProdService extends ExcelProcessingHandler<StandardProd> {

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
        List<StandardProd> prods = getProds(cond);

        return create(prods);
    }

    public StandardProdsResponse searchWithCount(CondParam condParam) {

        StandardProdCond cond = condParam.toStandardProdCond();
        int count = standardProdDao.countByCond(cond);
        List<StandardProd> prods = getProds(cond);

        return StandardProdsResponse.builder()
                .resultCode(ResultCode.SUCCESS)
                .totalItemsCount(count)
                .prods(prods)
                .build();
    }

    /**
     *
     * @param createRequest
     * @return
     */
    public BaseResponse save(CreateStandardProdRequest createRequest) {

        return null;
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

    private List<StandardProd> getProds(StandardProdCond cond) {

        return standardProdDao.selectByCond(cond);
    }
}
