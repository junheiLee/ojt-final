package com.ojt_final.office.standard;

import com.ojt_final.office.global.search.Cond;
import com.ojt_final.office.global.search.StandardProdCond;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
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

    /**
     * 기준 상품 한 건 저장
     *
     * @param prod 기준 상품
     * @return affected row
     */
    int insert(StandardProd prod);

    /**
     * 기준 상품 옵셔널 조회
     *
     * @param code the code of the product to retrieve
     * @return an {@link Optional} containing the retrieved standard product. If no product is found, an empty Optional is returned.
     */
    Optional<StandardProd> select(int code);

    List<StandardProd> selectByCond(StandardProdCond cond);

    /**
     * 가장 큰 기준 상품 코드 조회
     * <p>
     * 새로운 기준 상품 입력 시, 코드 생성에 사용
     * </p>
     *
     * @return 마지막 기준 상품 코드
     */
    int selectLastSeq();

    /**
     * 전체 기준 상품 개수 조회
     *
     * @return 전체 기준 상품 개수
     */
    int countAll();

    int countByCond(Cond cond);

    /**
     * 기준 상품 수정
     *
     * @param prod the standard product domain object containing the update information
     * @return affected row
     */
    int update(StandardProd prod);

    /**
     * 최저가 및 업체 수 갱신
     *
     * @param standardProdCodes the list of standard product codes to be updated
     * @return the number of updated standard products
     */
    int renewIntegrated(Set<Integer> standardProdCodes);

    /**
     * 기준 상품 삭제
     * <p>
     * 연결된 파트너 상품이 있는 경우 해당 링크들도 삭제한다.
     * </p>
     *
     * @param code the code of the product to be deleted
     * @return the number of rows affected by the deletion operation
     */
    int delete(int code);
}
