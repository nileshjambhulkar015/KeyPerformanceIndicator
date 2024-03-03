package com.futurebizops.kpi.response;

import com.futurebizops.kpi.dto.EmployeeKppMasterDto;
import lombok.Data;

import java.util.List;

@Data
public class EmployeeKppCumulativeResponse {

    private Double totalCumulative;
    private Integer totalMonths;
    private Double averageCumulative;
    List<EmployeeKppMasterDto> employeeKppMasterDtos;
}
