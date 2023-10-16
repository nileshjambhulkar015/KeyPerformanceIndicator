package com.futurebizops.kpi.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EmployeeLoginUpdateRequest {

    @Schema(example = "1", description = "This field is used for employee id")
    private Integer empId;

    @Schema(example = "1", description = "This field is used for employee roler id")
    private Integer roleId;

    @Schema(example = "1", description = "This field is used for employee password")
    private String empPassword;

    @Schema(example = "This is remark", description = "This field is used for role remark")
    private String remark;

    @Schema(example = "A", description = "This field is used for role status")
    private String statusCd;

    @Schema(example = "PM", description = "This field is used for Created User Id")
    private String employeeId;
}
