package com.ojt_final.office.domain.search;

import com.ojt_final.office.domain.search.sort.PartnerProdSort;
import lombok.Builder;

import java.util.List;


public class PartnerProdCond extends Cond {

    private PartnerProdSort sort;

    @Builder
    public PartnerProdCond(int limit, int offset, Integer category, List<String> sortParams) {
        super(limit, offset, category);
        this.sort = PartnerProdSort.fromParams(sortParams);
    }

}
