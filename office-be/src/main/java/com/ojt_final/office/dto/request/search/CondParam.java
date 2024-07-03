package com.ojt_final.office.dto.request.search;

import com.ojt_final.office.domain.search.PartnerProdCond;
import com.ojt_final.office.domain.search.StandardProdCond;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.ojt_final.office.global.constant.CommonConst.OUTPUT_LIST_LIMIT_SIZE;

@Slf4j
@ToString
@Getter
@Setter
@NoArgsConstructor
public class CondParam {

    private int page;
    private int limit;
    private int offset;
    private Integer categoryCode;
    private List<String> sorts;

    public StandardProdCond toStandardProdCond() {

        return StandardProdCond.builder()
                .limit(OUTPUT_LIST_LIMIT_SIZE)
                .offset((Math.abs(page) - 1) * OUTPUT_LIST_LIMIT_SIZE)
                .sortParams(sorts)
                .build();
    }

    public PartnerProdCond toPartnerProdCond() {

        return PartnerProdCond.builder()
                .limit(OUTPUT_LIST_LIMIT_SIZE)
                .offset((Math.abs(page) - 1) * OUTPUT_LIST_LIMIT_SIZE)
                .sortParams(sorts)
                .build();
    }
}
