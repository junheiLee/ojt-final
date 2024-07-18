package com.ojt_final.office.company;

import lombok.*;

@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class Partner {

    private String code;
    private String name;

    @Builder
    public Partner(String code, String name) {
        this.code = code;
        this.name = name;
    }

}
