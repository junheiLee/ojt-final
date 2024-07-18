package com.ojt_final.office.link;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface LinkDao {

    int insertAll(List<Link> links);

    List<Integer> selectStandardCodes(List<String> partnerProdCodes);

    int deleteByPartnerProdCodes(List<String> partnerProdCodes);

    int deleteAllByStandardCode(int standardCode);
}
