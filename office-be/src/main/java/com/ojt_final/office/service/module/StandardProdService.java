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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.ojt_final.office.global.constant.CommonConst.BATCH_SIZE;


@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class StandardProdService extends AbstractUploadableService<StandardProd> {

    private final BatchProcessor batchProcessor;
    private final StandardProdDao standardProdDao;

    @Override
    public UploadExcelResponse saveExcelData(MultipartFile excelFile) throws IOException {

        List<StandardProd> standardProds = parse(excelFile);
        BatchResult batchResult = saveAll(standardProds);

        return UploadExcelResponse.builder()
                .code(ResultCode.UPLOAD_RESULT)
                .batchResult(batchResult)
                .build();
    }

    @Override
    public Class<StandardProd> getTargetDomain() {
        return StandardProd.class;
    }

    private BatchResult saveAll(List<StandardProd> standardProds) {

        int previousCount = standardProdDao.countAll(); // 생성된 데이터 수를 구하기 위한 이전 데이터 수
        return batchProcessor.save(BATCH_SIZE, standardProds, standardProdDao::saveAll)
                .calInsertAndMaintainThenSet(previousCount, standardProdDao.countAll());
    }

    public StandardProdListResponse getResponseProds(CondParam condParam) {

        StandardProdCond cond = condParam.toStandardProdCond();
        int count = standardProdDao.countByCond(cond);
        List<StandardProd> prods = standardProdDao.selectByCond(cond);

        return StandardProdListResponse.builder()
                .resultCode(ResultCode.SUCCESS)
                .totalItemsCount(count)
                .prods(prods)
                .build();
    }

    public int integrateChange(List<Integer> standardProdCodes) {

        return standardProdDao.integrateChange(standardProdCodes);
    }
}
