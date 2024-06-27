package com.ojt_final.office.dao;

import com.ojt_final.office.domain.Category;
import org.junit.jupiter.api.DisplayName;
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

    @DisplayName("최초 Dao 설정 확인을 위한 Test")
    @Test
    void insertAllAndSelectAllTest() {
        //given: 기존 카테고리 코드와 겹치지 않도록 음수 부여
        Category category1 = Category.builder().code(-1).name("test1").build();
        Category category2 = Category.builder().code(-2).name("test2").build();

        List<Category> categories = List.of(category1, category2);

        //when
        List<Category> beforeInsert = categoryDao.selectAll();
        int insertCount = categoryDao.saveAll(categories);
        List<Category> results = categoryDao.selectAll();

        //then
        assertThat(insertCount).isEqualTo(categories.size());
        assertThat(results.size() - beforeInsert.size()).isEqualTo(categories.size());
        assertThat(results).contains(category1);
    }

    @DisplayName("INSERT 중 중복 발생 시, UPDATE Test")
    @Test
    void saveAllTest() {
        final int insertValue = 1;
        final int updateValue = 2;

        //given: 기존 카테고리 코드와 겹치지 않도록 음수 부여
        Category insertFirst = Category.builder().code(-1).name("insert").build();
        Category insertSecond = Category.builder().code(-2).name("insert").build();
        Category updateFirst = Category.builder().code(-1).name("update").build();

        List<Category> categories = List.of(insertFirst, insertSecond, updateFirst);
        int insertCount = (int) categories.stream().filter(category -> category.getName().equals("insert")).count();
        int updateCount = (int) categories.stream().filter(category -> category.getName().equals("update")).count();

        //when
        int affectedRow = categoryDao.saveAll(categories);
        List<Category> results = categoryDao.selectAll();

        //then
        assertThat(affectedRow).isEqualTo(insertValue * insertCount + updateValue * updateCount);
        assertThat(results).contains(insertSecond, updateFirst);
    }

    @DisplayName("INSERT 중 중복되어도 데이터가 변경되지 않을 때")
    @Test
    void saveAllDuplicateTest() {

        //given
        Category category1 = Category.builder().code(1).name("Category1").build();
        Category category2 = Category.builder().code(2).name("Category2").build();
        Category category3 = Category.builder().code(3).name("Category3").build();

        List<Category> categories = List.of(category1, category2, category3);


        //when
        int affectedRow = categoryDao.saveAll(categories);
        List<Category> results = categoryDao.selectAll();

        //then
        assertThat(affectedRow).isEqualTo(3);
    }
}
