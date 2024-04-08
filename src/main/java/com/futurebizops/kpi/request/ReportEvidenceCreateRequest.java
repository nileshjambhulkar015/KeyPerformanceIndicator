package com.futurebizops.kpi.request;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Instant;

@Data
public class ReportEvidenceCreateRequest {

    private Integer empId;
    private String evFileName;
    private  byte[] evFile;
    private String evContentType;
    private Instant evMonth;
    private String remark;
    private String statusCd;
}
