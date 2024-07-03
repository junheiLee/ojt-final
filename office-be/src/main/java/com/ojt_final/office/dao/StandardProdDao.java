package com.ojt_final.office.dao;

import com.ojt_final.office.domain.StandardProd;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface StandardProdDao {

    int saveAll(List<StandardProd> products);

    int countAll();

    int integrateChange(List<Integer> standardProductCodes);
}
