package com.ojt_final.office.standard.dto;

import com.ojt_final.office.global.constant.ResultCode;
import com.ojt_final.office.global.dto.BaseResponse;
import com.ojt_final.office.standard.StandardProd;
import lombok.Getter;

import static com.ojt_final.office.global.constant.CommonConst.DATE_FORMAT;

@Getter
public class GetStandardProdResponse extends BaseResponse {

    private String name;
    private int categoryCode;
    private String imageOrigin;
    private String imageOriginUrl;
    private String imageUrl;
    private String manufactureDate;
    private String description;

    public GetStandardProdResponse(ResultCode code, StandardProd standardProd) {
        super(code);
        if (standardProd != null) {
            this.name = standardProd.getName();
            this.categoryCode = standardProd.getCategoryCode();
            this.imageOrigin = standardProd.getImageOrigin();
            this.imageOriginUrl = standardProd.getImageOriginUrl();
            this.imageUrl = standardProd.getImageUrl();
            this.manufactureDate = DATE_FORMAT.format(standardProd.getManufactureDate());
            this.description = standardProd.getDescription();
        }
    }
}
