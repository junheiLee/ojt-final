package com.ojt_final.office.domain.search.sort;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * PartnerProd 리스트의 정렬 기준을 나타내는 클래스.
 * 각 필드의 정렬 방향을 포함하도록 Direction 클래스를 상속한다.
 */
@Getter
public class PartnerProdSort extends Direction {

    private String name = NONE;
    private String createdAt = NONE;

    /**
     * 정렬 파라미터 리스트를 받아 정렬 조건을 설정한 PartnerProdSort 객체를 생성해 반환한다.
     *
     * @param params the list of sorting parameters
     * @return a PartnerProdSort instance with sorting criteria set
     */
    public static PartnerProdSort fromParams(List<String> params) {

        PartnerProdSort sort = new PartnerProdSort();

        if (params != null) {
            params.forEach(param -> FieldSetter.from(param).setField(sort));
        }
        return sort;
    }

    /**
     * 각 필드 Setter Enum.
     */
    private enum FieldSetter {

        NAME(sort -> sort.name = toggle(sort.name, ASC)),
        NAME_REVERSE(sort -> sort.name = toggle(sort.name, DESC)),

        OLDEST(sort -> sort.createdAt = toggle(sort.createdAt, ASC)),
        NEWEST(sort -> sort.createdAt = toggle(sort.createdAt, DESC)),

        NOT_USED(sort -> {
        });

        private final Consumer<PartnerProdSort> setter;

        FieldSetter(Consumer<PartnerProdSort> setter) {
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
         * 해당 PartnerProdSort 객체의 필드 방향을 설정한다.
         *
         * @param sort the PartnerProdSort instance to apply the sorting direction to
         */
        void setField(PartnerProdSort sort) {
            setter.accept(sort);
        }

    }
}
