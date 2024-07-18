package com.ojt_final.office.integrate.dto;

import com.ojt_final.office.global.constant.ResultCode;
import com.ojt_final.office.global.dto.BaseResponse;
import lombok.Builder;

public class IntegratedResponse extends BaseResponse {

    private int changedStandardCount;

    @Builder
    public IntegratedResponse(ResultCode code, int changedStandardCount) {
        super(code);
        this.changedStandardCount = changedStandardCount;
    }
}
