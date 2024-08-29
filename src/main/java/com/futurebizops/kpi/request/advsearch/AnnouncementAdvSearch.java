package com.futurebizops.kpi.request.advsearch;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AnnouncementAdvSearch {
    @Schema(example = "1", description = "This field is used for complaint date")
    private String announFromDate;

    @Schema(example = "1", description = "This field is used for complaint date")
    private String announToDate;

    @Schema(example = "HR and Admin", description = "This field is used for complaint description")
    private String asAnnounStatus;
}
