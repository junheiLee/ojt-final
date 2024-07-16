package com.ojt_final.office.global.exception.excel;

import com.ojt_final.office.global.exception.CustomException;

import static com.ojt_final.office.dto.response.constant.ResultCode.MISSING_HEADERS;

public class NoExcelColumnAnnotationsException extends CustomException {

    public NoExcelColumnAnnotationsException() {
        super(MISSING_HEADERS);
    }
}
