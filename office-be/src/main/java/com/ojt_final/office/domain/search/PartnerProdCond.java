package com.ojt_final.office.domain.search;

import com.ojt_final.office.domain.search.sort.PartnerProdSort;
import lombok.Builder;

import java.util.List;


public class PartnerProdCond extends Cond {

    private final Boolean isLinked;
    private final PartnerProdSort sort;

    @Builder
    public PartnerProdCond(int limit, int offset, Integer category, Boolean isLinked, List<String> sortParams) {
        super(limit, offset, category);
        this.isLinked = isLinked;
        this.sort = PartnerProdSort.fromParams(sortParams);
    }

}
