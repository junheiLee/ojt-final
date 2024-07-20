package com.ojt_final.office.partnerProd.dto;

import com.ojt_final.office.partnerProd.PartnerProd;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString
@Getter
@NoArgsConstructor
public class CreatePartnerProdRequest {

    @NotEmpty(message = "EMPTY")
    @Size(max = 50, message = "SIZE_OVER_50")
    private String partnerCode;

    @Positive(message = "RANGE_OVER")
    private int categoryCode;

    @NotEmpty(message = "EMPTY")
    @Size(max = 50, message = "SIZE_OVER_50")
    private String code;

    @NotEmpty(message = "EMPTY")
    private String name;

    @PositiveOrZero(message = "NEGATIVE")
    private int pcPrice;

    @PositiveOrZero(message = "NEGATIVE")
    private int mobilePrice;

    @NotEmpty(message = "EMPTY")
    @Size(max = 300, message = "SIZE_OVER_300")
    private String url;

    @Size(max = 300, message = "SIZE_OVER_300")
    private String imageURl;

    /**
     * Converts CreatePartnerProdRequest to {@link PartnerProd} entities
     *
     * @return a {@link PartnerProd} entities
     */
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
