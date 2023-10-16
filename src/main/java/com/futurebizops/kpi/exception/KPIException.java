package com.futurebizops.kpi.exception;

import lombok.Data;

@Data
public class KPIException extends RuntimeException {

    private static final long serialVersionUID  = 1L;
    private final String sourceClass;
    private final boolean isSuccess;
   public KPIException(String sourceClass, boolean isSuccess, String details) {
        super(details);
        this.sourceClass = sourceClass;
        this.isSuccess = isSuccess;
    }
}
