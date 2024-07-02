package com.ojt_final.office.dao;

import com.ojt_final.office.domain.Link;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class LinkDaoTest {

    @Autowired
    LinkDao linkDao;

    @BeforeEach
    void save() {
        List<Link> links = List.of(
                Link.builder().partnerProductCode("4").standardProductCode(4).build(),
                Link.builder().partnerProductCode("5").standardProductCode(5).build());

        linkDao.saveAll(links);
    }

    @DisplayName("저장, 파트너상품코드로 중복 없는 기준상품 코드 반환 Test")
    @Test
    void saveAndFindAllStandardCodes() {
        //given
        List<Link> links = List.of(
                Link.builder().partnerProductCode("1").standardProductCode(1).build(),  // 3개
                Link.builder().partnerProductCode("2").standardProductCode(1).build(),  // 1개 (기준상품 중복)
                Link.builder().partnerProductCode("3").standardProductCode(2).build()); // 1개
        List<String> partnerProductCodes = links.stream().map(Link::getPartnerProductCode).toList();

        //when
        int savedCount = linkDao.saveAll(links);
        List<Integer> linkedChangeStandardCodes = linkDao.findAllByPartnerProductCodes(partnerProductCodes);

        //then
        assertThat(savedCount).isEqualTo(3);
        assertThat(linkedChangeStandardCodes.size()).isEqualTo(2);
        assertThat(linkedChangeStandardCodes).contains(1, 2);
    }

    @DisplayName("파트너상품 코드와 일치하는 모든 링크 제거 Test")
    @Test
    void deleteAllTest() {
        //given
        List<String> partnerProductCodes = List.of("4", "5");

        //when
        int deletedCount = linkDao.deleteAll(partnerProductCodes);
        List<Integer> foundStandardProductCodes = linkDao.findAllByPartnerProductCodes(partnerProductCodes);

        //then
        assertThat(deletedCount).isEqualTo(2);
        assertThat(foundStandardProductCodes).isEmpty();
    }
}
