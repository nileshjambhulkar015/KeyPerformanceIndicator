package com.futurebizops.kpi.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
public class CumulativeUpdateRequest {

    @Schema(example = "2024-25", description = "This field is used for remark")
    private String finYear;

    @Schema(example = "e111", description = "This field is used for remark")
    private Integer empId;

    @Schema(example = "Positive attitude", description = "This field is used for remark")
    private String empKeyStrength;

    @Schema(example = "Area of improvement", description = "This field is used for remark")
    private String empAreaOfImprovement;

    @Schema(example = "Training needs", description = "This field is used for remark")
    private String empTrainginDevelopmentNeeds;

    @Schema(example = "e111", description = "This field is used for employee id")
    private String employeeId;
}
