package com.futurebizops.kpi.request.advsearch;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.repository.query.Param;

@Data
@ToString
public class AnnouncementAdvSearch {
    @Schema(example = "2024-08-30T14:49", description = "This field is used for complaint date")
    private String asAnnounFromDate;

    @Schema(example = "2024-08-30T14:49", description = "This field is used for complaint date")
    private String asAnnounToDate;

    @Schema(example = "Pending", description = "This field is used for complaint description")
    private String asAnnounStatus;

    @Schema(example = "1", description = "This field is used for complaint description")
    private Integer asAnnounTypeId;

    @Schema(example = "1", description = "This field is used for complaint description")
    private String statusCd;
}
