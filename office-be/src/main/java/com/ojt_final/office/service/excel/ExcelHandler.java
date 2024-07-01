package com.ojt_final.office.service.excel;

import com.ojt_final.office.dto.response.constant.ResultCode;
import com.ojt_final.office.global.exception.excel.UnSupportedFileException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ExcelHandler {

    public final ExcelConverter excelConverter = ExcelConverter.INSTANCE;

    public void validExtension(MultipartFile excelFile) {

        if (!excelConverter.supports(excelFile.getOriginalFilename())) {
            throw new UnSupportedFileException(ResultCode.NOT_EXCEL_FILE);
        }
    }
}
