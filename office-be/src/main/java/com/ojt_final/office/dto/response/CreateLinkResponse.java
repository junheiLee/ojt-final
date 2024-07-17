package com.ojt_final.office.dto.response;

import com.ojt_final.office.dto.response.constant.ResultCode;
import lombok.Builder;

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
