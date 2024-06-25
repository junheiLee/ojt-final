package com.ojt_final.office.dao;

import com.ojt_final.office.domain.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class CategoryDaoTest {

    @Autowired
    CategoryDao categoryDao;

    @Test
    void insertAllAndSelectAllTest() {
        //given
        Category category1 = Category.builder().code(1).name("test1").build();
        Category category2 = Category.builder().code(2).name("test2").build();

        List<Category> categories = List.of(category1, category2);

        //when
        int insertCount = categoryDao.insertAll(categories);
        List<Category> results = categoryDao.selectAll();

        //then
        assertThat(insertCount).isEqualTo(categories.size());
        assertThat(results.size()).isEqualTo(categories.size());
        assertThat(results).contains(category1);
    }

}
