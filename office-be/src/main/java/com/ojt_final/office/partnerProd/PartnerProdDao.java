package com.ojt_final.office.partnerProd;

import com.ojt_final.office.global.search.PartnerProdCond;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Mapper
@Component
public interface PartnerProdDao {

    /**
     * 협력사 상품 목록 저장 (링크 여부 = false)
     *
     * @param prods 협력사 상품 목록
     * @return affected row
     */
    int insertAll(List<PartnerProd> prods);

    /**
     * 협력사 상품 한 건 저장
     *
     * @param prod 협력사 상품
     * @return affected row
     */
    int insert(PartnerProd prod);

    /**
     * 협력사 상품 옵셔널 조회
     *
     * @param partnerCode the code of the partner associated with the product to retrieve
     * @param code        the code of the product to retrieve
     * @return an {@link Optional} containing the retrieved partner product. If no product is found, an empty Optional is returned.
     */
    Optional<PartnerProd> select(@Param("partnerCode") String partnerCode, @Param("code") String code);

    List<PartnerProd> selectAllByCond(PartnerProdCond cond);

    /**
     * 전체 협력사 상품 개수 조회
     *
     * @return 전체 협력사 상품 개수
     */
    int countAll();

    int countByCond(PartnerProdCond cond);

    /**
     * 협력사 상품 수정
     *
     * @param prod the partner product domain object containing the update information
     * @return affected row
     */
    int update(PartnerProd prod);

    int updateAllIsLinked(@Param("isLinked") boolean isLinked, @Param("codes") List<String> codes);

    /**
     * 협력사 상품 삭제
     *
     * @param partnerCode the code of the partner associated with the product to be deleted
     * @param code        the code of the product to be deleted
     * @return the number of rows affected by the deletion operation
     */
    int delete(@Param("partnerCode") String partnerCode, @Param("code") String code);
}
