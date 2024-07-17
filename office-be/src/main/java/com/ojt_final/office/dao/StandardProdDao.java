package com.ojt_final.office.dao;

import com.ojt_final.office.domain.StandardProd;
import com.ojt_final.office.domain.search.Cond;
import com.ojt_final.office.domain.search.StandardProdCond;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Mapper
@Component
public interface StandardProdDao {

    /**
     * 기준 상품 목록 저장 (최저가는 기입 불가)
     *
     * @param prods 기준 상품 목록
     * @return affected row
     */
    int insertAll(List<StandardProd> prods);

    List<StandardProd> selectByCond(StandardProdCond cond);

    /**
     * 전체 기준 상품 개수 조회
     *
     * @return 전체 기준 상품 개수
     */
    int countAll();

    int countByCond(Cond cond);

    int integrateChange(Set<Integer> standardProdCodes);
}
