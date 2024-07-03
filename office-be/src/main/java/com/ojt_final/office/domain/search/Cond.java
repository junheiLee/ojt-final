package com.ojt_final.office.domain.search;

import lombok.Getter;

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
