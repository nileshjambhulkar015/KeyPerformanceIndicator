package com.futurebizops.kpi.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeKPPReportResponse {
    Integer empId;
    Integer deptId;
    Integer desigId;
    Instant currentDate;

    List<KeyPerfParam> keyPerfParamEntities;
}
