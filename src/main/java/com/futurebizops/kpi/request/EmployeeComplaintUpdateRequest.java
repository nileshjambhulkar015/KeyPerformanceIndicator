package com.futurebizops.kpi.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;

@Data
@ToString
public class EmployeeComplaintUpdateRequest {

    @Schema(example = "1", description = "This field is used for complaint date")
    private Integer empCompId;

    @Schema(example = "1", description = "This field is used for complaint date")
    private Integer empId;

    @Schema(example = "e111", description = "This field is used for complaint date")
    private String empEId;

    @Schema(example = "e111", description = "This field is used for complaint date")
    private String empEmailId;

    @Schema(example = "1", description = "This field is used for complaint date")
    private Integer roleId;

    @Schema(example = "1", description = "This field is used for complaint date")
    private Integer deptId;

    @Schema(example = "1", description = "This field is used for complaint date")
    private Integer desigId;

    @Schema(example = "1", description = "This field is used for complaint type")
    private Integer compTypeDeptId;

    @Schema(example = "1", description = "This field is used for complaint type")
    private Integer compTypeId;

    @Schema(example = "HR and Admin", description = "This field is used for complaint description")
    private String compDesc;


    @Schema(example = "HR and Admin", description = "This field is used for complaint description")
    private String compStatus;


    @Schema(example = "01-08-2024 04:24", description = "This field is used for complaint description")
    private String compResolveDateTime;

    @Schema(example = "1", description = "This field is used for complaint type")
    private Integer compResolveEmpId;

    private String compResolveEmpName;


    private String compResolveEmpEId;

    @Schema(example = "This is remark", description = "This field is used for department remark")
    private String remark;

    @Schema(example = "A", description = "This field is used for role status")
    private String statusCd;

    @Schema(example = "e1422", description = "This field is used for Created User Id")
    private String employeeId;

}
