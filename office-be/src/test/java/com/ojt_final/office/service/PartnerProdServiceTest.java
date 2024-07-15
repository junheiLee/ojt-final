package com.ojt_final.office.service;

import com.ojt_final.office.service.module.PartnerProdService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class PartnerProdServiceTest {

    @Autowired
    PartnerProdService partnerProdService;

    @DisplayName("링크 연결 여부 벌크 업데이트 테스트")
    @Test
    void updateAllIsLinkedTest() {
        //given
        List<String> partnerProductCodes = List.of("1", "2", "3"); // 1: 3개, 2: 1개, 3: 1개

        //when
        partnerProdService.updateAllIsLinked(true, partnerProductCodes);

        //then
//        assertThat(result).isEqualTo(5); // 추 후, dao count 생성 후 변경 필요
    }

}
