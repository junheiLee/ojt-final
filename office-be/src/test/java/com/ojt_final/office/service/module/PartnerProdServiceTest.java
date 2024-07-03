package com.ojt_final.office.service.module;

import com.ojt_final.office.dto.request.search.CondParam;
import com.ojt_final.office.dto.response.PartnerProdListResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class PartnerProdServiceTest {

    @Autowired
    PartnerProdService partnerProdService;

    @ParameterizedTest(name = "정렬조건={0}")
    @MethodSource("condParam")
    void getResponseProductsTest(CondParam condParam, int idx, String name){

        //when
        PartnerProdListResponse response = partnerProdService.getResponseProducts(condParam);

        //then
        assertThat(response.getProducts().get(idx).getName()).isEqualTo(name);

    }

    private static Stream<Arguments> condParam() {

        CondParam 이름_내림차순 = new CondParam();
        이름_내림차순.setPage(1);
        이름_내림차순.setSorts(List.of("NAME_REVERSE"));

        CondParam 오래된순 = new CondParam();
        오래된순.setPage(1);
        오래된순.setSorts(List.of("OLDEST"));

        CondParam 다중 = new CondParam();
        다중.setPage(1);
        다중.setSorts(List.of("NAME", "OLDEST"));

        CondParam 없음 = new CondParam();
        없음.setPage(1);

        return Stream.of(
                Arguments.arguments(이름_내림차순, 0, "PartnerProduct5"),
                Arguments.arguments(오래된순, 0, "PartnerProduct1"),
                Arguments.arguments(다중, 1, "PartnerProduct1-p3"),
                Arguments.arguments(없음, 0, "PartnerProduct5")
        );
    }
}
