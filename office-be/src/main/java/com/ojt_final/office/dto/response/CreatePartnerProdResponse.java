package com.ojt_final.office.dto.response;

import com.ojt_final.office.domain.PartnerProd;
import com.ojt_final.office.dto.response.constant.ResultCode;
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
