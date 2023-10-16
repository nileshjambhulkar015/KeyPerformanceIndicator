package com.futurebizops.kpi.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorResponse {

    private String sourceClass;
    private boolean isSuccess;
    private String details;
}
