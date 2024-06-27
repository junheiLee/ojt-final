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
    private int updateCount;

    private int insertCount;
    private int maintainCount;

    @Builder
    public BatchResult(int targetSize, int affectedRow, int failedCount) {

        this.targetSize = targetSize;
        this.affectedRow = affectedRow;
        this.failedCount = failedCount;
        this.updateCount = affectedRow - targetSize + failedCount;
    }

    public BatchResult calInsertAndMaintainThenSet(int previousCount, int currentCount) {
        this.insertCount = currentCount - previousCount;
        this.maintainCount = affectedRow - 2 * updateCount - insertCount;
        return this;
    }
}
