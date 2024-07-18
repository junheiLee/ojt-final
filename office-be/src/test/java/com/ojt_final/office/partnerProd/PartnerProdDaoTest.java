package com.ojt_final.office.partnerProd;

import com.ojt_final.office.global.search.PartnerProdCond;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class PartnerProdDaoTest {

    @Autowired
    PartnerProdDao partnerProdDao;

    @DisplayName("PK 중복 값 INSERT 시, affectedRow = 2 Test")
    @Test
    void insertAllTest() {
        final int insertValue = 1;
        final int updateValue = 2;

        //given
        List<PartnerProd> partnerProds = getInsertAllTestList();
        int insertCount = (int) partnerProds.stream().filter(e -> e.getName().equals("INSERT")).count();
        int updateCount = (int) partnerProds.stream().filter(e -> e.getName().equals("UPDATE")).count();

        //when
        int affectedRow = partnerProdDao.insertAll(partnerProds);

        //then
        assertThat(affectedRow).isEqualTo(insertValue * insertCount + updateValue * updateCount);
    }

    private List<PartnerProd> getInsertAllTestList() {

        PartnerProd insert
                = PartnerProd.builder()
                .code("insert").partnerCode("1").categoryCode(1)
                .name("INSERT").url("test").imageUrl("test").build();
        PartnerProd insertTemp
                = PartnerProd.builder()
                .code("update").partnerCode("1").categoryCode(1)
                .name("INSERT").url("test").imageUrl("test").build();
        PartnerProd update
                = PartnerProd.builder()
                .code("update").partnerCode("1").categoryCode(1)
                .name("UPDATE").url("test").imageUrl("test").build();

        return List.of(insert, insertTemp, update);
    }

    @DisplayName("링크/미링크 상품 Count Test")
    @Test
    void countByCondTest() {
        //given
        int totalCount = partnerProdDao.countAll();
        PartnerProdCond linked = PartnerProdCond.builder().isLinked(true).build();
        PartnerProdCond unLinked = PartnerProdCond.builder().isLinked(false).build();

        List<String> codes = List.of("3", "4", "5");
        partnerProdDao.updateAllIsLinked(true, codes);

        //when
        int linkedCount = partnerProdDao.countByCond(linked);
        int unlinkedCount = partnerProdDao.countByCond(unLinked);

        //then
        assertThat(linkedCount).isEqualTo(codes.size());
        assertThat(linkedCount + unlinkedCount).isEqualTo(totalCount);
    }

}
