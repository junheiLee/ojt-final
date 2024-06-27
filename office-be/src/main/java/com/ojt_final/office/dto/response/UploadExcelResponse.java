package com.ojt_final.office.dto.response;

import com.ojt_final.office.dto.response.constant.ResultCode;
import com.ojt_final.office.service.batch.BatchResult;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UploadExcelResponse<E> extends BaseResponse {

    private final int insertCount;
    private final int updateCount;
    private final int maintainCount;
    private final int failedCount;

    @Builder
    public UploadExcelResponse(ResultCode code,
                               BatchResult batchResult) {
        super(code.name(),
                String.format(code.getMessage(),
                        batchResult.getInsertCount(),
                        batchResult.getUpdateCount(),
                        batchResult.getMaintainCount(),
                        batchResult.getFailedCount()));

        this.insertCount = batchResult.getInsertCount();
        this.updateCount = batchResult.getUpdateCount();
        this.maintainCount = batchResult.getMaintainCount();
        this.failedCount = batchResult.getFailedCount();
    }

}
