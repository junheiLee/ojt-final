package com.ojt_final.office.global.exception.excel;


import com.ojt_final.office.dto.response.constant.ResultCode;
import com.ojt_final.office.global.exception.CustomException;

public class NoExcelColumnAnnotationsException extends CustomException {

    public NoExcelColumnAnnotationsException(ResultCode resultCode) {
        super(resultCode);
    }
}
