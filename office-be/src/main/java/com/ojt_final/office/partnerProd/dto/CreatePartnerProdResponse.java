package com.ojt_final.office.partnerProd.dto;

import com.ojt_final.office.global.constant.ResultCode;
import com.ojt_final.office.global.dto.BaseResponse;
import com.ojt_final.office.partnerProd.PartnerProd;
import lombok.Getter;

@Getter
public class CreatePartnerProdResponse extends BaseResponse {

    private String prodCode;
    private String partnerCode;

    public CreatePartnerProdResponse(ResultCode resultCode, PartnerProd partnerProd) {
        super(resultCode);
        this.prodCode = partnerProd.getCode();
        this.partnerCode = partnerProd.getPartnerCode();
    }

    public CreatePartnerProdResponse(String resultCode, String message, String prodCode) {
        super(resultCode, message);
        this.prodCode = prodCode;
    }
}
