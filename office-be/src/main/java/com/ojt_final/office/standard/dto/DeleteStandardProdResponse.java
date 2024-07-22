package com.ojt_final.office.standard.dto;

import com.ojt_final.office.global.constant.ResultCode;
import com.ojt_final.office.global.dto.BaseResponse;
import lombok.Getter;

@Getter
public class DeleteStandardProdResponse extends BaseResponse {

    private int deletedLinkCount;

    public DeleteStandardProdResponse(ResultCode code, int deletedCount) {
        super(code);
        this.deletedLinkCount = deletedCount;
    }
}
