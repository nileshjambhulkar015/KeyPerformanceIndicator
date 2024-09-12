package com.futurebizops.kpi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "complaint_type_audit")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComplaintTypeAudit extends AuditEnabledEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comp_type_AUD_ID")
    private Integer compTypeAuditId;

    @Column(name = "comp_type_id")
    private Integer compTypeId;


    @Column(name = "dept_id")
    private Integer deptId;

    @Column(name = "comp_type_NAME")
    private String compTypeName;

    @Column(name = "remark")
    private String remark;

    @Column(name = "status_cd")
    private String statusCd;

    public ComplaintTypeAudit(ComplaintTypeEntity complaintEntity) {
        super(complaintEntity.getCreatedDate(), complaintEntity.getCreatedUserId(), complaintEntity.getUpdatedDate(), complaintEntity.getUpdatedUserId());

        this.deptId= complaintEntity.getDeptId();

        this.compTypeId = complaintEntity.getCompTypeId();
        this.compTypeName = complaintEntity.getCompTypeName();
        this.remark = complaintEntity.getRemark();
        this.statusCd = complaintEntity.getStatusCd();
    }
}
