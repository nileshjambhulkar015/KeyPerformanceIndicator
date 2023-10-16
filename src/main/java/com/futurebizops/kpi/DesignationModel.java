package com.futurebizops.kpi;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DesignationModel {

    private Integer deptId;
    private String desigName;
    private String statusCd;
    private String sortName;
    private String sortDirection;
    private Integer pageSize;
    private Integer pageOffset;
}
