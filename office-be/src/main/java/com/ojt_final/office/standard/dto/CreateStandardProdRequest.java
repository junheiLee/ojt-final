package com.ojt_final.office.standard.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString
@Getter
@NoArgsConstructor
public class CreateStandardProdRequest{

    private String name;
    private int categoryCode;
    private String manufactureDate;
    private String description;
    private String imageOrigin;
    @Size(max = 300, message = "SIZE_OVER_300")
    private String imageOriginUrl;

}
