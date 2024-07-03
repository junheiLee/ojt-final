package com.ojt_final.office.dao;

import com.ojt_final.office.domain.PartnerProd;
import com.ojt_final.office.domain.search.Cond;
import com.ojt_final.office.domain.search.PartnerProdCond;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface PartnerProdDao {

    int saveAll(List<PartnerProd> partnerProds);

    int countAll();

    List<PartnerProd> selectByCond(PartnerProdCond cond);

    int countAllByCond(Cond cond);

    int updateAllIsLinked(@Param("isLinked") boolean isLinked, @Param("codes") List<String> codes);


}
