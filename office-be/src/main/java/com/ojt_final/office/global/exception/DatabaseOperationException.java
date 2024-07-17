package com.ojt_final.office.global.exception;

import com.ojt_final.office.dto.response.constant.ResultCode;

public class DatabaseOperationException extends CustomException{

    public DatabaseOperationException(String message) {
        super(ResultCode.FAILED, message);
    }
}
