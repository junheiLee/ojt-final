package com.ojt_final.office.domain;

import lombok.*;

@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class Link {

    private String partnerProductCode;
    private int standardProductCode;

    @Builder
    public Link(String partnerProductCode, int standardProductCode) {
        this.partnerProductCode = partnerProductCode;
        this.standardProductCode = standardProductCode;
    }
}
