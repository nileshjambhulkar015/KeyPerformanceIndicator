package com.futurebizops.kpi.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum PageDirection {
    ASC("ASC"),
    DESC("DESC");

    private String code;

    private PageDirection(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    //To check value present in Enum
    public static Boolean isValid(String code) {
        return Arrays.stream(PageDirection.values())
                .map(PageDirection::getCode)
                .collect(Collectors.toSet())
                .contains(code);
    }

    ///Get Value
    public static PageDirection get(String code) {
        String trimmedCode = StringUtils.trimToNull(code);
        for (PageDirection pageDirection : values()) {
            if (pageDirection.code.equals(trimmedCode)) {
                return pageDirection;
            }
        }
        return null;
    }
}
