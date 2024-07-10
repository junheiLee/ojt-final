package com.ojt_final.office.dto.response;

import com.ojt_final.office.dto.response.constant.ResultCode;
import com.ojt_final.office.service.batch.BatchResult;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents the response after uploading and processing an Excel file.
 * Extends {@link BaseResponse} to include base response fields.
 */
@ToString(callSuper = true)
@Getter
public class UploadExcelResponse extends BaseResponse {

    private final int createdCount;
    private final int updatedCount;
    private final int unchangedCount;
    private final int failedCount;

    @Setter
    private int standardChangedCount;

    /**
     * Constructs an UploadExcelResponse with the given result code and batch result.
     *
     * @param code        the result code indicating the outcome of the request
     * @param batchResult the batch result containing counts of created, updated, unchanged, and failed records
     */
    @Builder
    public UploadExcelResponse(ResultCode code,
                               BatchResult batchResult) {
        super(code.name(),
                String.format(code.getMessage(),
                        batchResult.getCreatedCount(),
                        batchResult.getUpdatedCount(),
                        batchResult.getUnChangedCount(),
                        batchResult.getFailedCount()));

        this.createdCount = batchResult.getCreatedCount();
        this.updatedCount = batchResult.getUpdatedCount();
        this.unchangedCount = batchResult.getUnChangedCount();
        this.failedCount = batchResult.getFailedCount();
    }

}
