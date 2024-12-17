package com.futurebizops.kpi.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EmployeeUpdateDeptDesigRequest {

    @Schema(example = "1", description = "This field is used for employee id")
    private Integer empId;

    @Schema(example = "1", description = "This field is used for employee id")
    private String empEId;

       @Schema(example = "1", description = "This field is used for employee role id")
    private Integer roleId;

    @Schema(example = "1", description = "This field is used for department id")
    private Integer deptId;

    @Schema(example = "1", description = "This field is used for designation id")
    private Integer desigId;

    @Schema(example = "PM", description = "This field is used for Created User Id")
    private String employeeId;
}
