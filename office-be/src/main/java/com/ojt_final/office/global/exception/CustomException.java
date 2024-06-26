package com.ojt_final.office.global.exception;

import com.ojt_final.office.dto.response.constant.ResultCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final ResultCode resultCode;

    public CustomException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.resultCode = resultCode;
    }
}
