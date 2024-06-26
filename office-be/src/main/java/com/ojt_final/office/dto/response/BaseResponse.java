package com.ojt_final.office.dto.response;

import com.ojt_final.office.dto.response.constant.ResultCode;
import lombok.Getter;
import lombok.Setter;

/**
 * JSON 형식의 Response에 반드시 들어가야하는 항목
 * code와 message만 들어갈 경우 생성자로 객체를 생성
 */
@Getter
@Setter
public class BaseResponse {

    private String resultCode;
    private String message;

    public BaseResponse(ResultCode resultCode) {
        this.resultCode = resultCode.name();
        this.message = resultCode.getMessage();
    }

    public BaseResponse(String resultCode, String message) {
        this.resultCode = resultCode;
        this.message = message;
    }
}
