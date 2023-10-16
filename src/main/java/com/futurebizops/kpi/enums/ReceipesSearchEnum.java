package com.futurebizops.kpi.enums;

public enum ReceipesSearchEnum {

    ALL("ALL"),
    BY_ID("BY_ID"),
    BY_EMP_ID("BY_EMP_ID"),
    BY_MACHINE_ID("BY_MACHINE_ID"),
    BY_PART_ID("BY_PART_ID"),
    BY_STATUS("BY_STATUS");

    private String searchBy;
    ReceipesSearchEnum(String searchBy) {
        this.searchBy = searchBy;
    }

    public String getSearchType() {
        return this.searchBy;
    }
}
