package com.futurebizops.kpi.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
@Builder
public class ReceipesSearchModel {

    private Pageable pageable;
    private Integer pageSize;
    private Integer pageOffset;
    private String sortName;
    private String sortDirection;
    private String fromDate;
    private String toDate;
    private int empId;
    private Integer machineId;
    private Integer partId;
    private String machTargetJobCount;
    private String recepStatus;
    private String statusCd;
}
