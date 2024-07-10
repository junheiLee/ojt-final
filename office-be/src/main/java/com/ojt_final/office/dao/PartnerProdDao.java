package com.ojt_final.office.dao;

import com.ojt_final.office.domain.PartnerProd;
import com.ojt_final.office.domain.search.Cond;
import com.ojt_final.office.domain.search.PartnerProdCond;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Mapper
@Component
public interface PartnerProdDao {

    int saveAll(List<PartnerProd> partnerProds);

    int save(PartnerProd partnerProd);

    Optional<PartnerProd> find(String code, String partnerCode);

    List<PartnerProd> findByCond(PartnerProdCond cond);

    int countAll();

    int countByCond(Cond cond);

    int update(PartnerProd partnerProd);

    int updateAllIsLinked(@Param("isLinked") boolean isLinked, @Param("codes") List<String> codes);


}
