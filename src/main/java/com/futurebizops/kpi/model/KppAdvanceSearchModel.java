package com.futurebizops.kpi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.Pageable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class KppAdvanceSearchModel {

    String kppObjectiveNo;
    String kppObjective;
    String kppPerformanceIndica;

    Pageable pageable;
}
