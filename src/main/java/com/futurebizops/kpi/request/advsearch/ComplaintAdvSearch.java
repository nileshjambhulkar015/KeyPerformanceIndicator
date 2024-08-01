package com.futurebizops.kpi.request.advsearch;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ComplaintAdvSearch {


    @Schema(example = "1", description = "This field is used for complaint date")
    private String compFromDate;

    @Schema(example = "1", description = "This field is used for complaint date")
    private String compToDate;

    @Schema(example = "1", description = "This field is used for complaint date")
    private Integer empId;

    @Schema(example = "1", description = "This field is used for complaint date")
    private Integer asDeptId;

    @Schema(example = "1", description = "This field is used for complaint date")
    private String asCompId;

    @Schema(example = "HR and Admin", description = "This field is used for complaint description")
    private String asCompStatus;
}
