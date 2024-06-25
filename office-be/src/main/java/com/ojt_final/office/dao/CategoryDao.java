package com.ojt_final.office.dao;

import com.ojt_final.office.domain.Category;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CategoryDao {

    int insertAll(List<Category> categories);

    List<Category> selectAll();
}
