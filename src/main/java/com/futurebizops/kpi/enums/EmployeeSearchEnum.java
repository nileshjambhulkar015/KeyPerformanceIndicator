package com.futurebizops.kpi.enums;

public enum EmployeeSearchEnum {

    ALL("ALL"),
    BY_ID("BY_ID"),
    BY_NAME("BY_NAME"),
    BY_DEPT_ID("BY_DEPT_ID"),
    BY_DESIG_ID("BY_DESIG_ID"),
    BY_ROLE_ID("BY_ROLE_ID"),
    BY_MOBILE_NO("BY_MOBILE_NO"),
    BY_GENDER("BY_GENDER"),
    BY_STATUS("BY_STATUS");

    private String searchBy;
    EmployeeSearchEnum(String searchBy) {
        this.searchBy = searchBy;
    }

    public String getSearchType() {
        return this.searchBy;
    }
}
