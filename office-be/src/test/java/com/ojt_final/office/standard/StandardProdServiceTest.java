package com.ojt_final.office.standard;

import com.ojt_final.office.global.dto.search.CondParam;
import com.ojt_final.office.link.Link;
import com.ojt_final.office.link.LinkDao;
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
public class StandardProdServiceTest {

    @Autowired
    LinkDao linkDao;

    @Autowired
    StandardProdService standardProdService;

    @ParameterizedTest(name = "정렬조건={0}")
    @MethodSource("condParams")
    void getResponseProdsTest(CondParam condParam, List<Integer> expect) {
        //given
        List<Link> links = List.of(
                Link.builder().partnerProdCode("1").standardProdCode(1).build(),
                Link.builder().partnerProdCode("2").standardProdCode(2).build(),
                Link.builder().partnerProdCode("3").standardProdCode(3).build(),
                Link.builder().partnerProdCode("4").standardProdCode(4).build(),
                Link.builder().partnerProdCode("5").standardProdCode(5).build());
        linkDao.insertAll(links);
        standardProdService.renewIntegrated(List.of(1, 2, 3, 4, 5));

        //when
        List<Integer> result = standardProdService.searchWithCount(condParam)
                .getProds().stream()
                .map(StandardProd::getCode)
                .toList();

        //then
        assertThat(result).isEqualTo(expect);
    }

    private static Stream<Arguments> condParams() {

        CondParam 기본 = new CondParam(); // 상품코드 큰 순
        기본.setSorts(List.of());
        List<Integer> 기본결과 = List.of(55, 5, 4, 3, 2, 1);

        CondParam 이름DESC_최저가ASC = new CondParam();
        이름DESC_최저가ASC.setSorts(List.of("NAME_REVERSE", "MIN_PC_PRICE_REVERSE"));
        List<Integer> 이름내림_최저가올림_결과 = List.of(5, 55, 4, 3, 2, 1); //minPcPrice: 5=5, 55=0, 4=4, 3=3, 2=2, 1=1

        return Stream.of(
                Arguments.arguments(기본, 기본결과),
                Arguments.arguments(이름DESC_최저가ASC, 이름내림_최저가올림_결과)
        );
    }
}
