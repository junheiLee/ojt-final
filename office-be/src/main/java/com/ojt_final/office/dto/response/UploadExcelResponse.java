package com.ojt_final.office.dto.response;

import com.ojt_final.office.dto.response.constant.ResultCode;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UploadExcelResponse<E> extends BaseResponse {

    private final int insertCount;
    private final int updateCount;
    private final int maintainCount;

    @Builder
    public UploadExcelResponse(ResultCode code,
                               int insertCount, int updateCount, int maintainCount) {
        super(code.name(), String.format(code.getMessage(), insertCount, updateCount, maintainCount));
        this.insertCount = insertCount;
        this.updateCount = updateCount;
        this.maintainCount = maintainCount;
    }

}
