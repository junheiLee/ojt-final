package com.ojt_final.office.domain.search.sort;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@Getter
public class PartnerProdSort extends Direction {

    private String name = NONE;
    private String createdAt = NONE;

    public static PartnerProdSort fromParams(List<String> params) {

        if (params == null) {
            params = new ArrayList<>();
        }
        PartnerProdSort sortCond = new PartnerProdSort();
        params.forEach(param -> PartnerProdSort.FieldSetter.from(param).apply(sortCond));

        return sortCond;
    }

    private enum FieldSetter {

        NAME(s -> s.name = toggle(s.name, ASC)),
        NAME_REVERSE(s -> s.name = toggle(s.name, DESC)),

        OLDEST(s -> s.createdAt = toggle(s.createdAt, ASC)),
        NEWEST(s -> s.createdAt = toggle(s.createdAt, DESC)),

        NOT_USED(s -> {
        });

        private final Consumer<PartnerProdSort> consumer;

        FieldSetter(Consumer<PartnerProdSort> consumer) {
            this.consumer = consumer;
        }

        private static String toggle(String current, String direction) {
            return current.equals(NONE) ? direction : current;
        }

        static PartnerProdSort.FieldSetter from(String param) {
            return Arrays.stream(values())
                    .filter(e -> e.name().equals(param))
                    .findAny()
                    .orElse(NOT_USED);
        }

        void apply(PartnerProdSort sort) {
            consumer.accept(sort);
        }

    }
}
