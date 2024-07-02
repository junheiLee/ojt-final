package com.ojt_final.office.dao;

import com.ojt_final.office.domain.Link;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class StandardProductDaoTest {

    @Autowired
    StandardProductDao standardProductDao;

    @Autowired
    LinkDao linkDao;

    @Autowired
    PartnerProductDao partnerProductDao;

    @DisplayName("타겟 기준 상품 코드가 있는 경우, where in 절 사용 Test")
    @Test
    void integrateChangeTest_in() {
        //given
        List<Link> links = List.of(
                Link.builder().partnerProductCode("1").standardProductCode(1).build(),
                Link.builder().partnerProductCode("2").standardProductCode(2).build());
        linkDao.saveAll(links);
        partnerProductDao.updateAllIsLinked(true, links.stream().map(Link::getPartnerProductCode).toList());
        List<Integer> standardProductCodes = List.of(1, 2);

        //when
        int affectedRow = standardProductDao.integrateChange(standardProductCodes);

        //then
        assertThat(affectedRow).isEqualTo(2);

    }

    @DisplayName("타겟 기준 상품 코드가 없는 경우, modifiedAt이 10분 이내인 PartnerProduct 업데이트")
    @Test
    void integrateChangeTest_between() {
        //given -> 어차피 Test DB Data는 테스트 직전에 생성됨.
        List<Link> links = List.of(
                Link.builder().partnerProductCode("1").standardProductCode(1).build(),
                Link.builder().partnerProductCode("2").standardProductCode(2).build());
        linkDao.saveAll(links);
        List<Integer> emptyList = Collections.emptyList();

        //when
        int affectedRow = standardProductDao.integrateChange(emptyList);

        //then
        assertThat(affectedRow).isEqualTo(links.size());
    }

}
