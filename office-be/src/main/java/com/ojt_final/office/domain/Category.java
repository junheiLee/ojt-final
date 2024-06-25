package com.ojt_final.office.domain;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Category {

    private int code;

    private String name;

    @Builder
    public Category(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

}
