package com.ojt_final.office.domain.sort;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public enum PartnerProdSortCond {

    PRODUCT_NAME("sProductName", "ASC"),
    PRODUCT_NAME_REVERSE("sProductName", "DESC"),

    MIN_PRICE("nMinPrice", "ASC"),
    MIN_PRICE_REVERSE("nMinPrice", "DESC"),

    MIN_PC_PRICE("nMinPcPrice", "ASC"),
    MIN_PC_PRICE_REVERSE("nMinPcPrice", "DESC"),

    MIN_MOBILE_PRICE("nMinMobilePrice", "ASC"),
    MIN_MOBILE_PRICE_REVERSE("nMinMobilePrice", "DESC"),

    PARTNER_COUNT("nPartnerCount", "ASC"),
    PARTNER_COUNT_REVERSE("nPartnerCount", "DESC"),

    NONE("", "");

    private final String columnName;
    private final String inOrder;

    PartnerProdSortCond(String columnName, String inOrder) {
        this.columnName = columnName;
        this.inOrder = inOrder;
    }

    public static List<PartnerProdSortCond> fromParams(List<String> sortParams) {

        Set<String> existingColumnNames = new HashSet<>();
        return sortParams.stream()
                .map(PartnerProdSortCond::getByName)
                .filter(sort -> existingColumnNames.add(sort.getColumnName()))
                .collect(Collectors.toList());
    }

    private static PartnerProdSortCond getByName(String param) {

        return Arrays.stream(values())
                .filter(e -> e.name().equals(param))
                .findAny().orElse(NONE);
    }
}
