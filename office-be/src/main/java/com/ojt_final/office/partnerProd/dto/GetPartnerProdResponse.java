package com.ojt_final.office.partnerProd.dto;

import com.ojt_final.office.global.constant.ResultCode;
import com.ojt_final.office.global.dto.BaseResponse;
import com.ojt_final.office.partnerProd.PartnerProd;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GetPartnerProdResponse extends BaseResponse {

    int categoryCode;
    String name;
    int pcPrice;
    int mobilePrice;
    String url;
    String imageUrl;

    @Builder
    public GetPartnerProdResponse(ResultCode code,
                                  PartnerProd partnerProd) {
        super(code);
        if (partnerProd != null) {
            this.categoryCode = partnerProd.getCategoryCode();
            this.name = partnerProd.getName();
            this.pcPrice = partnerProd.getPcPrice();
            this.mobilePrice = partnerProd.getMobilePrice();
            this.url = partnerProd.getUrl();
            this.imageUrl = partnerProd.getImageUrl();
        }
    }
}
