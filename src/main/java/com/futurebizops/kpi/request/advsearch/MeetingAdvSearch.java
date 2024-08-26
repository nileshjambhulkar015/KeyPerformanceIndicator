package com.futurebizops.kpi.request.advsearch;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MeetingAdvSearch {
    @Schema(example = "1", description = "This field is used for complaint date")
    private String meetFromDate;

    @Schema(example = "1", description = "This field is used for complaint date")
    private String meetToDate;

    @Schema(example = "HR and Admin", description = "This field is used for complaint description")
    private String asMeetStatus;
}
