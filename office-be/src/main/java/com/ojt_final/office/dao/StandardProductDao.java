package com.ojt_final.office.dao;

import com.ojt_final.office.domain.StandardProduct;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface StandardProductDao {

    int saveAll(List<StandardProduct> products);

    int countAll();

    int updateLinkedChange(List<Integer> standardProductCodes);
}
