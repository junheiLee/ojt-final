package com.ojt_final.office.service.batch;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class BatchResult {

    private int targetSize;
    private int affectedRow;
    private int failedCount;
    private int updatedCount;

    private int createdCount;
    private int unChangedCount;

    @Builder
    public BatchResult(int targetSize, int affectedRow, int failedCount) {

        this.targetSize = targetSize;
        this.affectedRow = affectedRow;
        this.failedCount = failedCount;
        this.updatedCount = affectedRow - targetSize + failedCount;
    }

    public BatchResult calInsertAndMaintainThenSet(int previousCount, int currentCount) {
        this.createdCount = currentCount - previousCount;
        this.unChangedCount = affectedRow - 2 * updatedCount - createdCount;
        return this;
    }
}
