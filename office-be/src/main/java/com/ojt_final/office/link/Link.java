package com.ojt_final.office.link;

import lombok.*;

@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class Link {

    private String partnerProdCode;
    private int standardProdCode;

    @Builder
    public Link(String partnerProdCode, int standardProdCode) {
        this.partnerProdCode = partnerProdCode;
        this.standardProdCode = standardProdCode;
    }
}
