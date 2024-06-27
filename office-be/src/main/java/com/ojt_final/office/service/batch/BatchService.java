package com.ojt_final.office.service.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;


@Slf4j
@Component
public class BatchService {

    /**
     * 설정한 개수만큼 한 번에 DB에 접근해 저장,
     * 실패 시, 로그에 실패 항목을 저장
     *
     * @param <T>               처리하는 객체 타입
     * @param items             처리할 객체 리스트
     * @param batchSaveFunction 해당 객체 리스트 다건 저장 함수 (파라미터: 처리할 객체 리스트, 리턴: 성공 개수)
     * @return db 반환 row와 실패한 개수를 담은 배치 결과 객체 반환
     */
    @Transactional
    public <T> BatchResult save(int batchSize, List<T> items, Function<List<T>, Integer> batchSaveFunction) {

        int totalSize = items.size();
        int endIdx; // 배치 처리 시, 적절한 사이즈로 나눌 때, 끝 인덱스
        int affectedRow = 0;
        int failedCount = 0;

        for (int i = 0; i < totalSize; i += batchSize) {
            endIdx = Math.min(i + batchSize, totalSize);
            List<T> batchItems = items.subList(i, endIdx);

            try {
                affectedRow += batchSaveFunction.apply(batchItems);

            } catch (DataAccessException e) { // 일괄 처리 실패 시, log에 보관
                log.warn("실패 항목={}", batchItems);
                failedCount += batchItems.size();
            }
        }

        return BatchResult.builder()
                .targetSize(totalSize)
                .affectedRow(affectedRow)
                .failedCount(failedCount)
                .build();
    }

}
