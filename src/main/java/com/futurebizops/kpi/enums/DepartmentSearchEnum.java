package com.futurebizops.kpi.enums;

public enum DepartmentSearchEnum {

    ALL("ALL"),
    BY_ID("BY_ID"),
    BY_NAME("BY_NAME"),
    BY_STATUS("BY_STATUS");

    private String searchBy;
    DepartmentSearchEnum(String searchBy) {
        this.searchBy = searchBy;
    }

    public String getSearchType() {
        return this.searchBy;
    }
}
