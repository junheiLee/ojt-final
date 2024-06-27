package com.ojt_final.office.service.batch;

import com.ojt_final.office.dao.CategoryDao;
import com.ojt_final.office.dao.PartnerProductDao;
import com.ojt_final.office.dao.PartnerProductDaoTest;
import com.ojt_final.office.domain.Category;
import com.ojt_final.office.domain.PartnerProduct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.ojt_final.office.global.constant.CommonConst.BATCH_SIZE;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class BatchServiceTest {

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    PartnerProductDao partnerProductDao;

    @Autowired
    BatchService batchService;

    @DisplayName("BatchService 정상 동작 확인")
    @Test
    void batchSaveTest() {
        //given: 기존 코드와 겹치지 않도록 음수 부여
        Category category1 = Category.builder().code(-1).name("test1").build();
        Category category2 = Category.builder().code(-2).name("test2").build();
        Category category3 = Category.builder().code(-3).name("test2").build();
        Category category4 = Category.builder().code(-4).name("test2").build();

        List<Category> categories = List.of(category1, category2, category3, category4);

        //when
        BatchResult batchResult = batchService.save(3, categories, categoryDao::saveAll);

        //then
        assertThat(batchResult.getAffectedRow()).isEqualTo(categories.size());
    }

    @DisplayName("Batch 결과 생성, 수정, 유지, 실패 숫자 확인")
    @ParameterizedTest(name = "insert: {1}, update: {2}, maintain: {3}, failed: {4}")
    @MethodSource("batchListAndResult")
    void batchSaveResultTest(List<PartnerProduct> products, int insert, int update, int maintain, int failed) {
        //given
        int previousCount = partnerProductDao.countAll();

        //when
        BatchResult batchResult
                = batchService.save(10, products, partnerProductDao::saveAll)
                .calInsertAndMaintainThenSet(previousCount, partnerProductDao.countAll());
        System.out.println(batchResult.toString());

        //then
        assertThat(batchResult.getInsertCount()).isEqualTo(insert);
        assertThat(batchResult.getUpdateCount()).isEqualTo(update);
        assertThat(batchResult.getMaintainCount()).isEqualTo(maintain);
        assertThat(batchResult.getFailedCount()).isEqualTo(failed);
    }

    // 주의사항: 같은 UNIQUE KEY update, maintain 동시에 넣으면, update 된 후 maintain으로 update 됨.
    private static Stream<Arguments> batchListAndResult() {

        List<PartnerProduct> insert = List.of(
                buildProductForTest("-1", "1", 1, "INSERT"),
                buildProductForTest("-2", "1", 1, "INSERT")
        );
        List<PartnerProduct> update = List.of(
                buildProductForTest("1", "1", 2, "UPDATE"),
                buildProductForTest("2", "2", 3, "UPDATE")
        );
        List<PartnerProduct> maintain = List.of(
                buildProductForTest("1", "1", 1, "PartnerProduct1"),
                buildProductForTest("2", "2", 2, "PartnerProduct2"),
                buildProductForTest("3", "3", 3, "PartnerProduct3")
        );
        List<PartnerProduct> failed = List.of(
                buildProductForTest("-1", "1", -1, "PartnerProduct1"),
                buildProductForTest("2", "-2", 2, "PartnerProduct2")
        );

        Map<String, List<PartnerProduct>> targetMap = new HashMap<>();
        targetMap.put("insert", insert);
        targetMap.put("update", update);
        targetMap.put("maintain", maintain);
        targetMap.put("failed", failed);

        return Stream.of(
                Arguments.arguments(
                        List.of(targetMap.get("insert").get(0),
                                targetMap.get("insert").get(1)
                        ),
                        2, 0, 0, 0
                ),
                Arguments.arguments(
                        List.of(targetMap.get("update").get(0),
                                targetMap.get("update").get(1)
                        ),
                        0, 2, 0, 0
                ), Arguments.arguments(
                        List.of(targetMap.get("maintain").get(0),
                                targetMap.get("maintain").get(1)
                        ),
                        0, 0, 2, 0
                ),
                Arguments.arguments(
                        List.of(targetMap.get("update").get(0),
                                targetMap.get("maintain").get(1),
                                targetMap.get("failed").get(0)
                        ),
                        0, 0, 0, 3
                ),
                Arguments.arguments(
                        List.of(targetMap.get("insert").get(0),
                                targetMap.get("update").get(0),
                                targetMap.get("maintain").get(1)
                        ),
                        1, 1, 1, 0
                ),
                Arguments.arguments(
                        List.of(targetMap.get("update").get(0),
                                targetMap.get("update").get(1),
                                targetMap.get("maintain").get(2)
                        ),
                        0, 2, 1, 0
                )

        );
    }

    private static PartnerProduct buildProductForTest(String code, String partnerCode, int categoryCode, String name) {
        return PartnerProduct.builder()
                .code(code).partnerCode(partnerCode).categoryCode(categoryCode).name(name)
                .pcPrice(0).mobilePrice(0).url("https://test").imageUrl("https://image.test")
                .build();
    }

}
