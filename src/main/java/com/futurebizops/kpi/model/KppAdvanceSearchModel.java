package com.futurebizops.kpi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KppAdvanceSearchModel {

    String kppObjectiveNo;
    String kppObjective;
    String kppPerformanceIndi;
    String kppTargetPeriod;
    Pageable pageable;
}
