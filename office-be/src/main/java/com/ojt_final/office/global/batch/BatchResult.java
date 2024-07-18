package com.ojt_final.office.global.batch;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * 일괄 처리 결과 객체
 */
@ToString
@Getter
public class BatchResult {

    /**
     * 일괄 처리 시도 객체 수
     */
    private int targetSize;

    /**
     * DB가 반환한 영향받은 Row 수: 생성, 수정, 유지 개수 계산 시 사용
     */
    private int affectedRow;

    /**
     * 처리 실패 객체 수
     */
    private int failedCount;

    /**
     * 수정된 객체 수
     */
    private int updatedCount;

    /**
     * 생성된 객체 수
     */
    private int createdCount;

    /**
     * 유지된 객체 수
     */
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
