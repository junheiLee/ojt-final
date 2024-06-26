package com.ojt_final.office.service.batch;

import com.ojt_final.office.dao.CategoryDao;
import com.ojt_final.office.domain.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class BatchServiceTest {

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    BatchService batchService;

    @Test
    void batchSaveTest() {
        //given: 기존 코드와 겹치지 않도록 음수 부여
        Category category1 = Category.builder().code(-1).name("test1").build();
        Category category2 = Category.builder().code(-2).name("test2").build();
        Category category3 = Category.builder().code(-3).name("test2").build();
        Category category4 = Category.builder().code(-4).name("test2").build();

        List<Category> categories = List.of(category1, category2, category3, category4);

        //when
        int count = batchService.save(categories, categoryDao::saveAll, 3);

        //then
        assertThat(count).isEqualTo(categories.size());
    }

}
