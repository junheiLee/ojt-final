package com.ojt_final.office.service;


import com.ojt_final.office.dao.StandardProductDao;
import com.ojt_final.office.domain.PartnerProduct;
import com.ojt_final.office.domain.StandardProduct;

import com.ojt_final.office.dto.response.UploadExcelResponse;
import com.ojt_final.office.dto.response.constant.ResultCode;
import com.ojt_final.office.global.exception.excel.UnSupportedFileException;
import com.ojt_final.office.service.batch.BatchService;
import com.ojt_final.office.service.excel.ExcelConverter;
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

    private final BatchService batchService;
    private final ExcelConverter excelConverter;
    private final StandardProductDao standardProductDao;

    @Override
    public UploadExcelResponse<Object> saveExcelData(MultipartFile excelFile) throws IOException {

        // 파일이 Excel 확장자(.xlsx, .xls) 확인
        if (!excelConverter.supports(excelFile.getOriginalFilename())) {
            throw new UnSupportedFileException(ResultCode.NOT_EXCEL_FILE);
        }

        List<StandardProduct> standardProducts
                = excelConverter.parseExcel(excelFile.getInputStream(), StandardProduct.class);

        int previousCount = standardProductDao.countAll();
        int affectedRow = batchService.save(standardProducts, standardProductDao::saveAll, BATCH_SIZE);

        // 각각 생성, 수정, 유지 Row 개수를 계산해 Dto에 담아 반환
        return calUploadExcelResponse(standardProducts.size(), previousCount, affectedRow);
    }


}
