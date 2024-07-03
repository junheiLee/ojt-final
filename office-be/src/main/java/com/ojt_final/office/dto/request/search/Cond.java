package com.ojt_final.office.dto.request.search;

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
public class Cond {

    private int page;
    private int limit;
    private int offset;
    private Integer categoryCode;
    private List<String> sorts;

    public Cond setPageInfo(int limit, int offset) {

        this.limit = limit;
        this.offset = offset;

        return this;
    }

    public void setPage(int page) {

        this.page = page;
        this.limit = OUTPUT_LIST_LIMIT_SIZE;
        this.offset = (Math.abs(page) - 1) * OUTPUT_LIST_LIMIT_SIZE;
    }

}
