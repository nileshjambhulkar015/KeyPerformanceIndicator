package com.futurebizops.kpi.response;

import lombok.Data;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Data
public class CummalitiveEmployeeResponse {
    private Integer sumOfEmployeeRatings;
    private Integer sumOfHodRatings;
    private Integer sumOfGMRatings;

    private Integer cummulativeRatings;
    private Float avgCummulativeRatings;

    PageImpl<EmployeeKppStatusResponse> employeeKppStatusResponses;
}
