package com.ojt_final.office.domain.search.sort;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * StandardProd 리스트의 정렬 기준을 나타내는 클래스.
 * 각 필드의 정렬 방향을 포함하도록 Direction 클래스를 상속한다.
 */
@Getter
public class StandardProdSort extends Direction {

    private String name = NONE;
    private String minPrice = NONE;
    private String minPcPrice = NONE;
    private String minMobilePrice = NONE;
    private String partnerCount = NONE;

    /**
     * 정렬 파라미터 리스트로 StandardProdSort 객체를 생성해 반환한다.
     *
     * @param params the list of sorting parameters
     * @return a StandardProdSort instance with sorting criteria set
     */
    public static StandardProdSort fromParams(List<String> params) {

        StandardProdSort sort = new StandardProdSort();

        if (params != null) {
            params.forEach(param -> FieldSetter.from(param).setField(sort));
        }
        return sort;
    }

    /**
     * 각 필드 Setter Enum.
     */
    enum FieldSetter {

        NAME(sort -> sort.name = toggle(sort.name, ASC)),
        NAME_REVERSE(sort -> sort.name = toggle(sort.name, DESC)),

        MIN_PRICE(sort -> sort.minPrice = toggle(sort.minPrice, ASC)),
        MIN_PRICE_REVERSE(sort -> sort.minPrice = toggle(sort.minPrice, DESC)),

        MIN_PC_PRICE(sort -> sort.minPcPrice = toggle(sort.minPcPrice, ASC)),
        MIN_PC_PRICE_REVERSE(sort -> sort.minPcPrice = toggle(sort.minPcPrice, DESC)),

        MIN_MOBILE_PRICE(sort -> sort.minMobilePrice = toggle(sort.minMobilePrice, ASC)),
        MIN_MOBILE_PRICE_REVERSE(sort -> sort.minMobilePrice = toggle(sort.minMobilePrice, DESC)),

        PARTNER_COUNT(sort -> sort.partnerCount = toggle(sort.partnerCount, ASC)),
        PARTNER_COUNT_REVERSE(sort -> sort.partnerCount = toggle(sort.partnerCount, DESC)),

        NOT_USED(s -> {});

        private final Consumer<StandardProdSort> setter;

        FieldSetter(Consumer<StandardProdSort> setter) {
            this.setter = setter;
        }

        /**
         * 파라미터에 해당하는 필드 setter를 반환한다.
         *
         * @param param the sorting parameter
         * @return the corresponding FieldSetter, or NOT_USED if no match is found
         */
        static FieldSetter from(String param) {
            return Arrays.stream(values())
                    .filter(e -> e.name().equalsIgnoreCase(param))
                    .findAny()
                    .orElse(NOT_USED);
        }

        /**
         * 해당 StandardProdSort 객체의 필드 방향을 설정한다.
         *
         * @param sort the StandardProdSort instance to apply the sorting direction to
         */
        void setField(StandardProdSort sort) {
            setter.accept(sort);
        }

    }

}
