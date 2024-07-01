package com.ojt_final.office.service.module;


import com.ojt_final.office.dao.StandardProductDao;
import com.ojt_final.office.domain.StandardProduct;
import com.ojt_final.office.dto.response.UploadExcelResponse;
import com.ojt_final.office.dto.response.constant.ResultCode;
import com.ojt_final.office.service.batch.BatchProcessor;
import com.ojt_final.office.service.batch.BatchResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ojt_final.office.global.constant.CommonConst.BATCH_SIZE;


@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class StandardProductService implements UploadableService<StandardProduct> {

    private final BatchProcessor batchProcessor;
    private final StandardProductDao standardProductDao;

    @Override
    public UploadExcelResponse saveAll(List<StandardProduct> standardProducts) {

        int previousCount = standardProductDao.countAll();
        BatchResult batchResult
                = batchProcessor.save(BATCH_SIZE, standardProducts, standardProductDao::saveAll)
                .calInsertAndMaintainThenSet(previousCount, standardProductDao.countAll());

        return UploadExcelResponse.builder()
                .code(ResultCode.UPLOAD_RESULT)
                .batchResult(batchResult)
                .build();
    }

    @Override
    public Class<StandardProduct> getTarget() {
        return StandardProduct.class;
    }

    public int updateLinkedChange() {

        //TODO: 변경할 기준 상품 추적해서 WHERE IN 절 추가하기

        return standardProductDao.updateLinkedChange();
    }
}
