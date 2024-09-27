package com.futurebizops.kpi.response;

import lombok.Data;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Data
public class CummalitiveEmployeeResponse {
    private Double sumOfEmployeeRatings;
    private Double sumOfHodRatings;
    private Double sumOfGMRatings;

    private Double cummulativeRatings;
    private Integer totalMonths;
    private Double avgCummulativeRatings;

    PageImpl<EmployeeKppStatusResponse> employeeKppStatusResponses;
}
