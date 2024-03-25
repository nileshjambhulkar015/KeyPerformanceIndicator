package com.futurebizops.kpi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.time.Instant;

@Table(name = "evidence_audit")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvidenceAudit extends AuditEnabledEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ev_aud_id")
    private Integer evAuditId;

    @Column(name = "emp_id")
    private Integer empId;

    @Column(name = "ev_file_name")
    private String evFileName;

  //  @Lob
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

    public EvidenceAudit(EvidenceEntity evidenceEntity) {
        super(evidenceEntity.getCreatedDate(), evidenceEntity.getCreatedUserId(), evidenceEntity.getUpdatedDate(), evidenceEntity.getUpdatedUserId());
        this.empId = evidenceEntity.getEmpId();
        this.evFileName = evidenceEntity.getEvFileName();
        this.evFile = evidenceEntity.getEvFile();
        this.evContentType = evidenceEntity.getEvContentType();
        this.evMonth = evidenceEntity.getEvMonth();
        this.remark = evidenceEntity.getRemark();
        this.statusCd = evidenceEntity.getStatusCd();
    }
}
