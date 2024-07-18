package com.ojt_final.office.global.search;

import com.ojt_final.office.global.search.sort.StandardProdSort;
import lombok.Builder;

import java.util.List;

public class StandardProdCond extends Cond {

    private StandardProdSort sort;

    @Builder
    public StandardProdCond(int limit, int offset, Integer category, List<String> sortParams) {
        super(limit, offset, category);
        this.sort = StandardProdSort.fromParams(sortParams);
    }

}
