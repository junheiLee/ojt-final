package com.ojt_final.office.dao;

import com.ojt_final.office.domain.Link;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface LinkDao {

    int saveAll(List<Link> links);

    List<Integer> findStandardCodesByPartnerProdCodes(List<String> partnerProductCodes);

    int deleteAll(List<String> partnerProductCodes);
}
