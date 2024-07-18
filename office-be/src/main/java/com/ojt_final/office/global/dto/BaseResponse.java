package com.ojt_final.office.global.dto;

import com.ojt_final.office.global.constant.ResultCode;
import lombok.Getter;
import lombok.Setter;

/**
 * JSON 형식의 Response에 반드시 들어가야하는 항목
 */
@Getter
@Setter
public class BaseResponse {

    private String code;
    private String message;

    public BaseResponse(ResultCode code) {
        this.code = code.name();
        this.message = code.getMessage();
    }

    public BaseResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
