package com.futurebizops.kpi.request.yearlykpprequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.time.Instant;
import java.util.List;

@Data
@ToString
public class FinishKppFeedbackRequest {
    private String finYear;

    @Schema(example = "1", description = "This field is used for Employee E Id")
    private Integer empId;

    private String empKppStatus;

    private String hodKppStatus;

    private String gmKppStatus;

    @Schema(example = "PM", description = "This field is used for Created User Id")
    private String employeeId;
}
