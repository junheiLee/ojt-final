package com.ojt_final.office.service.excel;

import com.ojt_final.office.dto.response.UploadExcelResponse;
import com.ojt_final.office.dto.response.constant.ResultCode;
import com.ojt_final.office.global.exception.excel.UnSupportedFileException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public abstract class ExcelHandler {

    protected final ExcelConverter excelConverter = ExcelConverter.INSTANCE;

    /**
     * 상품 정보가 담겨있는 엑셀 파일을 파싱해 DB에 저장하는 기능
     *
     * @param excelFile 상품 정보가 담겨있는 엑셀 파일
     * @return 해당 정보 기반 생성, 수정, 유지, 실패 개수 및 결과 코드를 담은 객체
     */
    protected abstract UploadExcelResponse<Object> saveExcelData(MultipartFile excelFile) throws IOException;


    protected void validExtension(MultipartFile excelFile) {

        if (!excelConverter.supports(excelFile.getOriginalFilename())) {
            throw new UnSupportedFileException(ResultCode.NOT_EXCEL_FILE);
        }
    }
}
