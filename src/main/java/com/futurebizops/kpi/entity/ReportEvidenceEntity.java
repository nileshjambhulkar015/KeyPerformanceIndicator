package com.futurebizops.kpi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Table(name = "report_evidence")
@Entity
@Data
@AllArgsConstructor
@DynamicUpdate
@NoArgsConstructor
public class ReportEvidenceEntity extends AuditEnabledEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "re_ev_id")
    private Integer reportEvId;

    @Column(name = "emp_id")
    private Integer empId;

    @Column(name = "ev_file_name")
    private String evFileName;

    @Column(name = "ev_file")
    private  byte[] evFile;

    @Column(name = "ev_content_type")
    private String evContentType;

    @Column(name = "ev_month")
    private Instant evMonth;

    @Column(name = "remark")
    private String remark;

    @Column(name = "status_cd")
    private String statusCd;
}
