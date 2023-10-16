package com.futurebizops.kpi.enums;

public enum ReceipeStatusEnum {
    PENDING("PENDING"),
    ASSIGNED("ASSIGNED"),
    COMPLETED("COMPLETED");
    private String code;

    ReceipeStatusEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
