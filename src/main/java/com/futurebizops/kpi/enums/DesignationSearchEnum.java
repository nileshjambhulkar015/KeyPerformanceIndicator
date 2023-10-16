package com.futurebizops.kpi.enums;

public enum DesignationSearchEnum {

    ALL("ALL"),
    BY_ID("BY_ID"),
    BY_DEPT_ID("BY_DEPT_ID"),
    BY_NAME("BY_NAME"),
    BY_STATUS("BY_STATUS");

    private String searchBy;
    DesignationSearchEnum(String searchBy) {
        this.searchBy = searchBy;
    }

    public String getSearchType() {
        return this.searchBy;
    }
}
