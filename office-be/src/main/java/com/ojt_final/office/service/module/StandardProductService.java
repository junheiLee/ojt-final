package com.ojt_final.office.service.module;


import com.ojt_final.office.dao.StandardProductDao;
import com.ojt_final.office.domain.StandardProduct;
import com.ojt_final.office.dto.response.UploadExcelResponse;
import com.ojt_final.office.dto.response.constant.ResultCode;
import com.ojt_final.office.service.batch.BatchProcessor;
import com.ojt_final.office.service.batch.BatchResult;
import com.ojt_final.office.service.excel.ExcelHandler;
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
public class StandardProductService extends ExcelHandler {

    private final BatchProcessor batchProcessor;
    private final StandardProductDao standardProductDao;

    @Override
    public UploadExcelResponse saveExcelData(MultipartFile excelFile) throws IOException {

        validExtension(excelFile); // 파일이 Excel 확장자(.xlsx, .xls)인지 확인
        List<StandardProduct> standardProducts
                = excelConverter.parse(excelFile.getInputStream(), StandardProduct.class);

        int previousCount = standardProductDao.countAll();
        BatchResult batchResult
                = batchProcessor.save(BATCH_SIZE, standardProducts, standardProductDao::saveAll)
                .calInsertAndMaintainThenSet(previousCount, standardProductDao.countAll());

        return UploadExcelResponse.builder()
                .code(ResultCode.UPLOAD_RESULT)
                .batchResult(batchResult)
                .build();
    }

    public int updateLinkedChange() {

        //TODO: 변경할 기준 상품 추적해서 WHERE IN 절 추가하기

        return standardProductDao.updateLinkedChange();
    }
}
