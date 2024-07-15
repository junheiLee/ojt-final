package com.ojt_final.office.service.module;


import com.ojt_final.office.dao.StandardProdDao;
import com.ojt_final.office.domain.StandardProd;
import com.ojt_final.office.domain.search.StandardProdCond;
import com.ojt_final.office.dto.request.search.CondParam;
import com.ojt_final.office.dto.response.StandardProdListResponse;
import com.ojt_final.office.dto.response.UploadExcelResponse;
import com.ojt_final.office.dto.response.constant.ResultCode;
import com.ojt_final.office.service.batch.BatchProcessor;
import com.ojt_final.office.service.batch.BatchResult;
import com.ojt_final.office.service.excel.ExcelProcessingHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    public UploadExcelResponse importExcel(MultipartFile excelFile) throws IOException {

        List<StandardProd> standardProds = parse(excelFile);
        BatchResult batchResult = saveAll(standardProds);

        return UploadExcelResponse.builder()
                .code(ResultCode.UPLOAD_RESULT)
                .batchResult(batchResult)
                .build();
    }

    private BatchResult saveAll(List<StandardProd> standardProds) {

        int previousCount = standardProdDao.countAll(); // 생성된 데이터 수를 구하기 위한 이전 데이터 수
        return batchProcessor.save(BATCH_SIZE, standardProds, standardProdDao::saveAll)
                .calInsertAndUnchangedCount(previousCount, standardProdDao.countAll());
    }

    public byte[] exportExcel(CondParam condParam) {

        StandardProdCond cond = condParam.toStandardProdCond();
        List<StandardProd> prods = getProds(cond);

        return create(prods);
    }

    public StandardProdListResponse getResponseProds(CondParam condParam) {

        StandardProdCond cond = condParam.toStandardProdCond();
        int count = standardProdDao.countByCond(cond);
        List<StandardProd> prods = getProds(cond);

        return StandardProdListResponse.builder()
                .resultCode(ResultCode.SUCCESS)
                .totalItemsCount(count)
                .prods(prods)
                .build();
    }

    private List<StandardProd> getProds(StandardProdCond cond) {

        return standardProdDao.selectByCond(cond);
    }

    public int integrateChange(Set<Integer> standardProdCodes) {

        return standardProdDao.integrateChange(standardProdCodes);
    }
}
