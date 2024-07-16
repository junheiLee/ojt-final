package com.ojt_final.office.global.exception.excel;


import com.ojt_final.office.dto.response.constant.ResultCode;
import com.ojt_final.office.global.exception.CustomException;

public class UnSupportedFileException extends CustomException {

    public UnSupportedFileException() {
        super(ResultCode.NOT_EXCEL_FILE);
    }
}
