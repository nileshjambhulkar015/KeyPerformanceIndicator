package com.futurebizops.kpi.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Column;

@Data
public class EmployeeExcelReadData {

    @Schema(example = "Permenent / Contigent", description = "This field is used for employee Type name")
    private String empTypeName;

    @Schema(example = "Pune", description = "This field is used for region name")
    private String regionName;

    @Schema(example = "Pimpari", description = "This field is used for site name")
    private String siteName;

    @Schema(example = "Nilesh Pvt Ltd", description = "This field is used for company name")
    private String companyName;

    @Schema(example = "HOD", description = "This field is used for employee role id")
    private String roleName;

    @Schema(example = "Production", description = "This field is used for department id")
    private String deptName;

    @Schema(example = "HOD", description = "This field is used for designation id")
    private String desigName;

    @Schema(example = "Nilesh", description = "This field is used for employee first name")
    private String empFirstName;

    @Schema(example = "Sambhaji", description = "This field is used for employee middle name")
    private String empMiddleName;

    @Schema(example = "Jambhulkar", description = "This field is used for employee last name")
    private String empLastName;

    @Schema(example = "e144", description = "This field is used for employee e id")
    private String empEid;

    @Schema(example = "9503232728", description = "This field is used for employee mobile no")
    private String empMobileNo;

    @Schema(example = "9503232728", description = "This field is used for employee emergency number")
    private String empEmerMobileNo;

    @Schema(example = "Hinjawadi, Pune", description = "This field is used for employee temporary address")
    private String tempAddress;

    @Schema(example = "Hinjawadi,, Pune", description = "This field is used for employee permenent address")
    private String permAddress;

    @Schema(example = "nileshj@gmail.com", description = "This field is used for employee photo")
    private String emailId;

    @Schema(example = "Male", description = "This field is used for employee gender")
    private String empGender;

    @Schema(example = "A+", description = "This field is used for employee blood group")
    private String empBloodgroup;

    @Schema(example = "e33", description = "This field is used for reporting employee id")
    private String reportingEmpEid;

    @Schema(example = "e33", description = "This field is used for reporting employee id")
    private String gmEmpId;

    @Schema(example = "A", description = "This field is used for employee statusCd")
    private String statusCd;

    @Schema(example = "PM", description = "This field is used for Created User Id")
    private String createdByEmployeeId;

    @Schema(example = "2024-06-01", description = "This field is used for date of birth of employee")
    private String empDOB;
}