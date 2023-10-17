package com.futurebizops.kpi.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SiteCreateRequest {

    @Schema(example = "1", description = "This field is used for department id")
    private Integer regionId;

    @Schema(example = "Pune", description = "This field is used for designation name")
    private String siteName;

    @Schema(example = "This is remark", description = "This field is used for designation remark")
    private String remark;

    @Schema(example = "A", description = "This field is used for role status")
    private String statusCd;

    @Schema(example = "PM", description = "This field is used for Created User Id")
    private String employeeId;
}
