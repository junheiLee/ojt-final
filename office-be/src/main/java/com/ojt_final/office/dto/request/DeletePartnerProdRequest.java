package com.ojt_final.office.dto.request;

import com.ojt_final.office.domain.PartnerProd;

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
