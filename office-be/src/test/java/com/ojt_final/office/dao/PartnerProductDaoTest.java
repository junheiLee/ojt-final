package com.ojt_final.office.dao;

import com.ojt_final.office.domain.PartnerProduct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class PartnerProductDaoTest {

    @Autowired
    PartnerProductDao partnerProductDao;

    @DisplayName("INSERT ON DUPLICATE KEY UPDATE 중 중복 발생 시, UPDATE Test")
    @Test
    void saveAllTest() {
        final int insertValue = 1;
        final int updateValue = 2;

        //given
        List<PartnerProduct> partnerProducts = getSaveAllTestList();
        int insertCount = (int) partnerProducts.stream().filter(e -> e.getName().equals("INSERT")).count();
        int updateCount = (int) partnerProducts.stream().filter(e -> e.getName().equals("UPDATE")).count();

        //when
        int affectedRow = partnerProductDao.saveAll(partnerProducts);

        //then
        assertThat(affectedRow).isEqualTo(insertValue * insertCount + updateValue * updateCount);
    }


    private List<PartnerProduct> getSaveAllTestList() {

        PartnerProduct insert
                = PartnerProduct.builder()
                .code("insert").partnerCode("1").categoryCode(1)
                .name("INSERT").url("test").imageUrl("test").build();
        PartnerProduct insertTemp
                = PartnerProduct.builder()
                .code("update").partnerCode("1").categoryCode(1)
                .name("INSERT").url("test").imageUrl("test").build();
        PartnerProduct update
                = PartnerProduct.builder()
                .code("update").partnerCode("1").categoryCode(1)
                .name("UPDATE").url("test").imageUrl("test").build();

        return List.of(insert, insertTemp, update);
    }

}
