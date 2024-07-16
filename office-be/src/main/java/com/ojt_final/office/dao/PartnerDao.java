package com.ojt_final.office.dao;

import com.ojt_final.office.domain.Partner;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface PartnerDao {

    List<Partner> selectAll();
}
