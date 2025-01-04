package com.futurebizops.kpi.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EmployeeUpdateRequest {

    @Schema(example = "1", description = "This field is used for employee id")
    private Integer empId;

    @Schema(example = "e111", description = "This field is used for employee middle name")
    private String empEId;

    @Schema(example = "1", description = "This field is used for employee role id")
    private Integer roleId;

    @Schema(example = "1", description = "This field is used for department id")
    private Integer deptId;

    @Schema(example = "1", description = "This field is used for designation id")
    private Integer desigId;

    @Schema(example = "1", description = "This field is used for Employee Type id")
    private Integer empTypeId;

    @Schema(example = "1", description = "This field is used for reporting employee id")
    private Integer reportingEmpId;

    @Schema(example = "1", description = "This field is used for region id")
    private Integer regionId;

    @Schema(example = "1", description = "This field is used for site id")
    private Integer siteId;

    @Schema(example = "1", description = "This field is used for site id")
    private Integer companyId;

    @Schema(example = "Nilesh", description = "This field is used for employee first name")
    private String empFirstName;

    @Schema(example = "Sambhaji", description = "This field is used for employee middle name")
    private String empMiddleName;

    @Schema(example = "Jambhulkar", description = "This field is used for employee last name")
    private String empLastName;

    @Schema(example = "9503232728", description = "This field is used for employee mobile no")
    private String empMobileNo;

    @Schema(example = "9503232728", description = "This field is used for employee emergency number")
    private String empEmerMobileNo;

    @Schema(example = "nileshj@gmail.com", description = "This field is used for employee photo")
    private String emailId;

    @Schema(example = "Hinjawadi, Pune", description = "This field is used for employee temporary address")
    private String tempAddress;

    @Schema(example = "Hinjawadi,, Pune", description = "This field is used for employee permenent address")
    private String permAddress;

    @Schema(example = "Adding new employee", description = "This field is used for employee remark")
    private String remark;

    @Schema(example = "A", description = "This field is used for employee statusCd")
    private String statusCd;

    @Schema(example = "PM", description = "This field is used for Created User Id")
    private String employeeId;
}
