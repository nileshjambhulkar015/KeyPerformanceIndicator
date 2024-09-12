package com.futurebizops.kpi.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class DepartmentUpdateRequest {

    @Schema(example = "1", description = "This field is used for department id")
    private Integer deptId;

    @Schema(example = "HR and Admin", description = "This field is used for department name")
    private String deptName;

    @Schema(example = "hr@futurebizos.com", description = "This field is used for department name")
    private String deptMailId;

    @Schema(example = "This is remark", description = "This field is used for department remark")
    private String remark;

    @Schema(example = "A", description = "This field is used for role status")
    private String statusCd;

    @Schema(example = "PM", description = "This field is used for Created User Id")
    private String employeeId;
}
