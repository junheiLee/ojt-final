package com.ojt_final.office.global.exception;

import com.ojt_final.office.global.constant.ResultCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final ResultCode code;

    public CustomException(ResultCode code) {
        super(code.getMessage());
        this.code = code;
    }

    public CustomException(ResultCode code, String message) {
        super(message);
        this.code = code;
    }
}
