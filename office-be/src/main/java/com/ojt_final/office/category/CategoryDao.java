package com.ojt_final.office.category;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CategoryDao {

    /**
     * 카테고리 목록 저장
     *
     * @param categories 카테고리 이름, 코드 목록
     * @return affected row
     */
    int insertAll(List<Category> categories);

    /**
     * 카테고리 목록 조회
     *
     * @return 카테고리 이름, 코드 목록
     */
    List<Category> selectAll();

    /**
     * 카테고리 전체 개수 조회
     *
     * @return 카테고리 개수
     */
    int countAll();
}
