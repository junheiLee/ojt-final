package com.ojt_final.office.partnerProd.dto;

import com.ojt_final.office.partnerProd.PartnerProd;

public class DeletePartnerProdRequest {

    private boolean isLinked;
    private String partnerProdCode;
    private String partnerCode;

    public PartnerProd toEntity() {
        return PartnerProd.builder()
                .isLinked(isLinked)
                .code(partnerProdCode)
                .partnerCode(partnerCode)
                .build();
    }
}
