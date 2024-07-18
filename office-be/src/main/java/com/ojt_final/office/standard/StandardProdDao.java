package com.ojt_final.office.standard;

import com.ojt_final.office.global.search.Cond;
import com.ojt_final.office.global.search.StandardProdCond;
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

    /**
     * 최저가 및 업체 수 갱신
     *
     * @param standardProdCodes the list of standard product codes to be updated
     * @return the number of updated standard products
     */
    int renewIntegrated(Set<Integer> standardProdCodes);
}
