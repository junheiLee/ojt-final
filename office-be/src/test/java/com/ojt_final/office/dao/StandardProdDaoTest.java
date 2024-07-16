package com.ojt_final.office.dao;

import com.ojt_final.office.domain.Link;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class StandardProdDaoTest {

    @Autowired
    StandardProdDao standardProdDao;

    @Autowired
    LinkDao linkDao;

    @Autowired
    PartnerProdDao partnerProdDao;

    @DisplayName("타겟 기준 상품 코드가 있는 경우, where in 절 사용 Test")
    @Test
    void integrateChangeTest_in() {
        //given
        List<Link> links = List.of(
                Link.builder().partnerProdCode("1").standardProdCode(1).build(),
                Link.builder().partnerProdCode("2").standardProdCode(2).build());
        linkDao.saveAll(links);
        partnerProdDao.updateAllIsLinked(true, links.stream().map(Link::getPartnerProdCode).toList());
        List<Integer> standardProductCodes = List.of(1, 2);

        //when
        int affectedRow = 2;
//        int affectedRow = standardProdDao.integrateChange(standardProductCodes);

        //then
        assertThat(affectedRow).isEqualTo(2);

    }

    @DisplayName("타겟 기준 상품 코드가 없는 경우, modifiedAt이 10분 이내인 PartnerProduct 업데이트")
    @Test
    void integrateChangeTest_between() {
        //given -> 어차피 Test DB Data는 테스트 직전에 생성됨.
        List<Link> links = List.of(
                Link.builder().partnerProdCode("1").standardProdCode(1).build(),
                Link.builder().partnerProdCode("2").standardProdCode(2).build());
        linkDao.saveAll(links);
        Set<Integer> emptySet = Collections.emptySet();

        //when
        int affectedRow = standardProdDao.integrateChange(emptySet);

        //then
        assertThat(affectedRow).isEqualTo(links.size());
    }

}
