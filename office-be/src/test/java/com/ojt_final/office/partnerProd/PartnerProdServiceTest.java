package com.ojt_final.office.partnerProd;

import com.ojt_final.office.global.dto.search.CondParam;
import com.ojt_final.office.partnerProd.dto.PartnerProdsResponse;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class PartnerProdServiceTest {

    @Autowired
    PartnerProdService partnerProdService;

    @ParameterizedTest(name = "정렬조건={0}")
    @MethodSource("condParams")
    void searchWithCountTest(CondParam condParam, int idx, String name, int pcPrice) {

        //when
        PartnerProdsResponse response = partnerProdService.searchWithCount(condParam);

        //then
        assertThat(response.getProds().get(idx).getName()).isEqualTo(name);
        assertThat(response.getProds().get(idx).getPcPrice()).isEqualTo(pcPrice);

    }

    private static Stream<Arguments> condParams() {

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
                Arguments.arguments(이름_내림차순, 0, "PartnerProduct5", 5),
                Arguments.arguments(오래된순, 0, "PartnerProduct1", 31),
                Arguments.arguments(다중, 1, "PartnerProduct1", 61),
                Arguments.arguments(없음, 0, "PartnerProduct5", 5)
        );
    }
}
