package com.ojt_final.office.global.search;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class Cond {

    private int limit;
    private int offset;
    private Integer categoryCode;

    public Cond(int limit, int offset, Integer categoryCode) {
        this.limit = limit;
        this.offset = offset;
        this.categoryCode = categoryCode;
    }

}
