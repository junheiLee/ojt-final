package com.ojt_final.office.dto.response;

import com.ojt_final.office.domain.Partner;
import com.ojt_final.office.dto.response.constant.ResultCode;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class PartnerListResponse extends BaseResponse{

    private final List<Partner> partners;

    @Builder
    public PartnerListResponse(ResultCode code, List<Partner> partners) {
        super(code);
        this.partners = partners;
    }
}
