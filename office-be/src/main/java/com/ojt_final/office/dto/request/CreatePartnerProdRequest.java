package com.ojt_final.office.dto.request;

import com.ojt_final.office.domain.PartnerProd;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString
@Getter
@NoArgsConstructor
public class CreatePartnerProdRequest {

    private String partnerCode;
    private int categoryCode;

    private String code;
    private String name;
    private int pcPrice;
    private int mobilePrice;

    private String url;
    private String imageURl;

    public PartnerProd toEntity() {
        return PartnerProd.builder()
                .partnerCode(partnerCode)
                .categoryCode(categoryCode)
                .code(code)
                .name(name)
                .pcPrice(pcPrice)
                .mobilePrice(mobilePrice)
                .url(url)
                .imageUrl(imageURl)
                .build();
    }

}
