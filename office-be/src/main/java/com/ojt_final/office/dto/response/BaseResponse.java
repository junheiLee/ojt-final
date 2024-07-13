package com.ojt_final.office.dto.response;

import com.ojt_final.office.dto.response.constant.ResultCode;
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

    public BaseResponse(ResultCode resultCode) {
        this.code = resultCode.name();
        this.message = resultCode.getMessage();
    }

    public BaseResponse(String resultCode, String message) {
        this.code = resultCode;
        this.message = message;
    }
}
