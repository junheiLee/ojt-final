package com.ojt_final.office.dao;

import com.ojt_final.office.domain.PartnerProd;
import com.ojt_final.office.domain.search.PartnerProdCond;
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
     * @param partnerProds 협력사 상품 목록
     * @return affected row
     */
    int insertAll(List<PartnerProd> partnerProds);

    int insert(PartnerProd partnerProd);

    Optional<PartnerProd> select(PartnerProd partnerProd);

    List<PartnerProd> selectAllByCond(PartnerProdCond cond);

    /**
     * 전체 협력사 상품 개수 조회
     *
     * @return 전체 협력사 상품 개수
     */
    int countAll();

    int countByCond(PartnerProdCond cond);

    int update(PartnerProd partnerProd);

    int updateAllIsLinked(@Param("isLinked") boolean isLinked, @Param("codes") List<String> codes);

    int delete(PartnerProd prod);
}
