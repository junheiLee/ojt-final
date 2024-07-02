package com.ojt_final.office.dao;

import com.ojt_final.office.domain.PartnerProduct;
import com.ojt_final.office.dto.request.search.Condition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface PartnerProductDao {

    int saveAll(List<PartnerProduct> partnerProducts);

    int countAll();

    int updateAllIsLinked(@Param("isLinked") boolean isLinked, @Param("codes") List<String> codes);

    int countPage(Condition condition);
}
