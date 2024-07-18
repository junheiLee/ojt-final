package com.ojt_final.office.global.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;


@Slf4j
@Component
public class BatchProcessor {

    /**
     * 설정한 개수만큼 한 번에 DB에 접근해 저장,
     * 실패 시, 로그에 실패 항목을 저장
     *
     * @param <T>               the type of items to be batch processed
     * @param batchSize         the size of each batch
     * @param items             the list of items to be batch processed
     * @param batchSaveFunction the function to save a batch of items (param: list of items, return: number of affected rows)
     * @return a {@link BatchResult} object containing the total count of items, number of affected rows, and number of failed items
     */
    @Transactional
    public <T> BatchResult save(int batchSize, List<T> items, Function<List<T>, Integer> batchSaveFunction) {

        int totalItemCount = items.size();
        int affectedRow = 0;
        int failedCount = 0;

        for (int i = 0; i < totalItemCount; i += batchSize) {
            int endIdx = Math.min(i + batchSize, totalItemCount); // 배치 처리 시, 적절한 사이즈로 나누는 끝 인덱스
            List<T> batchItems = items.subList(i, endIdx);

            try {
                affectedRow += batchSaveFunction.apply(batchItems);
                log.info("affectedRow={}", affectedRow);
            } catch (DataAccessException e) { // 일괄 처리 실패 시, log에 보관
                log.warn("[BatchFailed] 실패 항목: {}", batchItems, e);
                failedCount += batchItems.size();
            }
        }

        return BatchResult.builder()
                .targetSize(totalItemCount)
                .affectedRow(affectedRow)
                .failedCount(failedCount)
                .build();
    }

}
