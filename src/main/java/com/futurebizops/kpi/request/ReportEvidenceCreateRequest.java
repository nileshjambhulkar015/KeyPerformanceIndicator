package com.futurebizops.kpi.request;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Instant;

@Data
@ToString
public class ReportEvidenceCreateRequest {
    private Integer empId;
    private String evFileName;
    private  byte[] evFile;
    private String evContentType;
    private Instant evMonth;
    private String remark;
    private String statusCd;
}
