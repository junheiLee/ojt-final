package com.ojt_final.office.dao;

import com.ojt_final.office.domain.PartnerProduct;
import com.ojt_final.office.dto.request.Condition;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface PartnerProductDao {

    int saveAll(List<PartnerProduct> partnerProducts);

    int countAll();

    int countPage(Condition condition);
}
