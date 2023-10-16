package com.futurebizops.kpi.enums;

public enum PMSearchEnum {

    ALL("ALL"),
    BY_MACH_ID("BY_MACH_ID"),
    BY_PART_ID("BY_PART_ID"),
    BY_JOB_TARGET_COUNT("BY_JOB_TARGET_COUNT"),
    BY_MACH_JOB_STATUS("BY_MACH_JOB_STATUS");

    private String searchBy;
    PMSearchEnum(String searchBy) {
        this.searchBy = searchBy;
    }

    public String getSearchType() {
        return this.searchBy;
    }
}
