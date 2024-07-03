package com.ojt_final.office.domain.search.sort;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class StandardSortTest {

    @DisplayName("정렬 조건이 중복일 때, 먼저 들어온 차순으로 설정, 해당 없으면 none Test")
    @Test
    void fromParamsTest(){
        //given
        List<String> params = List.of("NAME_REVERSE", "NAME", "wrongParam");

        //when
        StandardProdSort sort = StandardProdSort.fromParams(params);

        //then
        assertThat(sort.getName()).isEqualTo("DESC");
        assertThat(sort.getMinPrice()).contains("");
    }
}
