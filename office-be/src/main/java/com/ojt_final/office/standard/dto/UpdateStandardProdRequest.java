package com.ojt_final.office.standard.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ojt_final.office.global.dto.util.CustomDateDeserializer;
import com.ojt_final.office.standard.StandardProd;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.sql.Date;

@Slf4j
@ToString
@Getter
@NoArgsConstructor
public class UpdateStandardProdRequest {

    @NotBlank(message = "EMPTY")
    @Size(max = 300, message = "SIZE_OVER_300")
    private String name;

    @Positive(message = "RANGE_OVER")
    private int categoryCode;

    @PastOrPresent(message = "FUTURE")
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date manufactureDate;

    @Size(max = 5000, message = "SIZE_OVER_5000")
    private String description;

    @NotBlank(message = "EMPTY")
    private String imageOrigin;

    @Size(max = 300, message = "SIZE_OVER_300")
    private String imageOriginUrl;

    /**
     * Converts UpdateStandardProdRequest to {@link StandardProd} entity.
     *
     * @param code     the internally generated standard product code
     * @param imageUrl the URL of the product image stored on the server
     * @return a {@link StandardProd} entity containing the provided information
     */
    public StandardProd toEntity(int code, String imageUrl) {

        return StandardProd.builder()
                .code(code)
                .imageUrl(imageUrl)
                .name(name)
                .categoryCode(categoryCode)
                .imageOrigin(imageOrigin)
                .imageOriginUrl(imageOriginUrl)
                .manufactureDate(manufactureDate)
                .description(description)
                .build();
    }

}
