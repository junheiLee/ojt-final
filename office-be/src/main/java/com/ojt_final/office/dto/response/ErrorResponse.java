package com.ojt_final.office.dto.response;

import com.ojt_final.office.dto.response.constant.ResultCode;
import lombok.Getter;

import java.util.Map;

@Getter
public class ErrorResponse extends BaseResponse{

    private Map<String, String> errors;

    public ErrorResponse(ResultCode resultCode) {
        super(resultCode);
    }

    public ErrorResponse(ResultCode resultCode, String message) {
        super(resultCode.name(), message);
    }

    public ErrorResponse(ResultCode resultCode, Map<String, String> errors) {
        super(resultCode);
        this.errors = errors;
    }

    public ErrorResponse(String resultCode, String message, Map<String, String> errors) {
        super(resultCode, message);
        this.errors = errors;
    }
}
