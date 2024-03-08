package com.futurebizops.kpi.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyMasterUpdateRequest {

    @Schema(example = "1", description = "This field is used for company id")
    private Integer companyId;

    @Schema(example = "1", description = "This field is used for region id")
    private Integer regionId;

    @Schema(example = "1", description = "This field is used for site id")
    private Integer siteId;

    @Schema(example = "Test", description = "This field is used for company name")
    private String companyName;

    @Schema(example = "Test", description = "This field is used for company address")
    private String companyAddress;

    @Schema(example = "1111", description = "This field is used for company mobile number")
    private String companyMbNo;

    @Schema(example = "2024-25", description = "This field is used for company financial year")
    private String companyFinYear;

    @Schema(example = "remark", description = "This field is used for remark")
    private String remark;

    @Schema(example = "A", description = "This field is used for status code")
    private String statusCd;

    @Schema(example = "e111", description = "This field is used for employee id")
    private String employeeId;
}
