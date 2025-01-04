package com.futurebizops.kpi.request.advsearch;

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
public class KPPAdvanceSearchRequest {

    String kppObjectiveNo;
    String kppObjective;
    String kppPerformanceIndica;

}
