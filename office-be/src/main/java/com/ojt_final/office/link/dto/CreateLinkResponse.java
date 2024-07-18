package com.ojt_final.office.link.dto;

import com.ojt_final.office.global.constant.ResultCode;
import com.ojt_final.office.global.dto.BaseResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateLinkResponse extends BaseResponse {

    int createdCount;
    int changedStandardCount;

    @Builder
    public CreateLinkResponse(ResultCode resultCode, int createdCount, int changedStandardCount) {
        super(resultCode);
        this.createdCount = createdCount;
        this.changedStandardCount = changedStandardCount;
    }

}
