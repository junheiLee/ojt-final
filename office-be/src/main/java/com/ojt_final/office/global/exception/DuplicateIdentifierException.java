package com.ojt_final.office.global.exception;

import com.ojt_final.office.global.constant.ResultCode;

public class DuplicateIdentifierException extends CustomException {

    public DuplicateIdentifierException() {
        super(ResultCode.FAILED);
    }
}
