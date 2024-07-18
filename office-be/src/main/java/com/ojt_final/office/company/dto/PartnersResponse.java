package com.ojt_final.office.company.dto;

import com.ojt_final.office.company.Partner;
import com.ojt_final.office.global.constant.ResultCode;
import com.ojt_final.office.global.dto.BaseResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class PartnersResponse extends BaseResponse {

    private final List<Partner> partners;

    @Builder
    public PartnersResponse(ResultCode code, List<Partner> partners) {
        super(code);
        this.partners = partners;
    }
}
