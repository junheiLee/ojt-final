package com.ojt_final.office.domain.search.sort;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@Getter
public class StandardProdSort extends Direction {

    private String name = NONE;
    private String minPrice = NONE;
    private String minPcPrice = NONE;
    private String minMobilePrice = NONE;
    private String partnerCount = NONE;

    public static StandardProdSort fromParams(List<String> params) {

        if (params == null) {
            params = new ArrayList<>();
        }

        StandardProdSort sortCond = new StandardProdSort();
        params.forEach(param -> FieldSetter.from(param).apply(sortCond));

        return sortCond;
    }

    private enum FieldSetter {

        NAME(s -> s.name = toggle(s.name, ASC)),
        NAME_REVERSE(s -> s.name = toggle(s.name, DESC)),

        MIN_PRICE(s -> s.minPrice = toggle(s.minPrice, ASC)),
        MIN_PRICE_REVERSE(s -> s.minPrice = toggle(s.minPrice, DESC)),

        MIN_PC_PRICE(s -> s.minPcPrice = toggle(s.minPcPrice, ASC)),
        MIN_PC_PRICE_REVERSE(s -> s.minPcPrice = toggle(s.minPcPrice, DESC)),

        MIN_MOBILE_PRICE(s -> s.minMobilePrice = toggle(s.minMobilePrice, ASC)),
        MIN_MOBILE_PRICE_REVERSE(s -> s.minMobilePrice = toggle(s.minMobilePrice, DESC)),

        PARTNER_COUNT(s -> s.partnerCount = toggle(s.partnerCount, ASC)),
        PARTNER_COUNT_REVERSE(s -> s.partnerCount = toggle(s.partnerCount, DESC)),

        NOT_USED(s -> {
        });

        private final Consumer<StandardProdSort> consumer;

        FieldSetter(Consumer<StandardProdSort> consumer) {
            this.consumer = consumer;
        }


        private static String toggle(String current, String direction) {
            return current.equals(NONE) ? direction : current;
        }

        static FieldSetter from(String param) {
            return Arrays.stream(values())
                    .filter(e -> e.name().equals(param))
                    .findAny()
                    .orElse(NOT_USED);
        }

        void apply(StandardProdSort sort) {
            consumer.accept(sort);
        }

    }

}
