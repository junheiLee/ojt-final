package com.ojt_final.office.dao;

import com.ojt_final.office.domain.StandardProd;
import com.ojt_final.office.domain.search.Cond;
import com.ojt_final.office.domain.search.StandardProdCond;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Mapper
@Component
public interface StandardProdDao {

    int saveAll(List<StandardProd> prods);

    int countAll();

    List<StandardProd> selectByCond(StandardProdCond cond);

    int countByCond(Cond cond);

    int integrateChange(Set<Integer> standardProdCodes);
}
