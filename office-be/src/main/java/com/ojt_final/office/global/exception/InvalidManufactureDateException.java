package com.ojt_final.office.global.exception;

import com.ojt_final.office.global.constant.ResultCode;

public class InvalidManufactureDateException extends CustomException {

    public InvalidManufactureDateException() {
        super(ResultCode.INVALID_DATE);
    }
}
