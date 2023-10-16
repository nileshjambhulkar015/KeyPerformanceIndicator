package com.futurebizops.kpi.model;

import com.futurebizops.kpi.enums.PMSearchEnum;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
@Builder
public class PMSearchModel {

    private Pageable pageable;
    private Integer pageSize;
    private Integer pageOffset;
    private String sortName;
    private String sortDirection;
    private String fromDate;
    private String toDate;
    private PMSearchEnum searchEnum;
    private Integer machineId;
    private Integer partId;
    private String machTargetJobCount;
    private String machJobStatus;
}
