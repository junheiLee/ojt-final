package com.ojt_final.office.link.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ToString
@Getter
@NoArgsConstructor
public class DeleteLinkRequest {

    @NotEmpty(message = "EMPTY")
    private List<
            @NotEmpty(message = "EMPTY")
            @Size(max = 50, message = "SIZE_OVER_50")
                    String> partnerProdCodes;

}
