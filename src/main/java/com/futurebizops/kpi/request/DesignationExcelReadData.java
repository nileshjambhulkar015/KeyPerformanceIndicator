package com.futurebizops.kpi.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class DesignationExcelReadData {
    @Schema(example = "1", description = "This field is used for role id")
    private Integer roleId;

    @Schema(example = "1", description = "This field is used for department id")
    private Integer deptId;

    @Schema(example = "HR Manager", description = "This field is used for designation name")
    private String desigName;

    @Schema(example = "This is remark", description = "This field is used for designation remark")
    private String remark;

    @Schema(example = "A", description = "This field is used for role status")
    private String statusCd;

    @Schema(example = "PM", description = "This field is used for Created User Id")
    private String employeeId;

}
