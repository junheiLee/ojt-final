package com.ojt_final.office.dto.response;

import com.ojt_final.office.dto.response.constant.ResultCode;

public class CreatePartnerProdResponse extends BaseResponse {

    private String code;

    public CreatePartnerProdResponse(ResultCode resultCode, String code) {
        super(resultCode);
        this.code = code;
    }

    public CreatePartnerProdResponse(String resultCode, String message, String code) {
        super(resultCode, message);
        this.code = code;
    }
}
