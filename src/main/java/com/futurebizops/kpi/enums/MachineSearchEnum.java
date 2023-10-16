package com.futurebizops.kpi.enums;

public enum MachineSearchEnum {

    ALL("ALL"),
    BY_ID("BY_ID"),
    BY_NAME("BY_NAME"),
    BY_MACHINE_CAPACITY("BY_MACHINE_CAPACITY"),
    BY_STATUS("BY_STATUS");

    private String searchBy;
    MachineSearchEnum(String searchBy) {
        this.searchBy = searchBy;
    }

    public String getSearchType() {
        return this.searchBy;
    }
}
