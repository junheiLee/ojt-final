package com.ojt_final.office.partnerProd.dto;

import com.ojt_final.office.partnerProd.PartnerProd;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString
@Getter
@NoArgsConstructor
public class UpdatePartnerProdRequest {

    private String targetCode;
    private String targetPartnerCode;

    private int categoryCode;
    private String name;
    private int pcPrice;
    private int mobilePrice;
    private String imageURl;

    public PartnerProd toEntity() {
        return PartnerProd.builder()
                .code(targetCode)
                .partnerCode(targetPartnerCode)

                .categoryCode(categoryCode)
                .name(name)
                .pcPrice(pcPrice)
                .mobilePrice(mobilePrice)
                .imageUrl(imageURl)
                .build();
    }

}
