package com.ojt_final.office.domain.sort;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.ojt_final.office.domain.sort.StandardSortCondition.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class StandardSortConditionTest {


    @DisplayName("같은 컬럼의 오름차순/내림차순 중복 Param, 정의되지 않은 Param Test")
    @Test
    void fromParamsTest(){
        //given
        List<String> params = List.of(PRODUCT_NAME.name(), PRODUCT_NAME_REVERSE.name(), "wrongParam");

        //when
        List<StandardSortCondition> conditions = StandardSortCondition.fromParams(params);

        //then
        assertThat(conditions.size()).isEqualTo(2);
        assertThat(conditions).contains(NONE);
    }
}
