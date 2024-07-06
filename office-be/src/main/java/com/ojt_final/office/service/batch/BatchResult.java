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
        this.updatedCount = calUpdatedCount();
    }

    /**
     * 배치 작업 전 후 개수와 반환받은 affectedRow 기반으로 생성된 객체 수와 유지된 객체 수를 계산해 변수를 지정한다.
     *
     * @param previousCount the previous count of items before the batch operation
     * @param currentCount  the current count of items after the operation
     * @return the updated BatchResult with the calculated counts
     */
    public BatchResult calInsertAndUnchangedCount(int previousCount, int currentCount) {
        this.createdCount = currentCount - previousCount;
        this.unChangedCount = affectedRow - 2 * updatedCount - createdCount;
        return this;
    }

    private int calUpdatedCount() {
        return affectedRow - targetSize + failedCount;
    }
}
