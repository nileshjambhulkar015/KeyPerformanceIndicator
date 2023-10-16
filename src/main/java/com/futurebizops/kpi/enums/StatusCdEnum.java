package com.futurebizops.kpi.enums;

public enum StatusCdEnum {

    A("A"),
    I("I");
    private String searchBy;

    StatusCdEnum(String searchBy) {
        this.searchBy = searchBy;
    }

    public String getSearchType() {
        return this.searchBy;
    }
}
