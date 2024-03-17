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

@Table(name = "designation_audit")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DesignationAudit extends AuditEnabledEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "desig_aud_id")
    private Integer desigAuditId;

    @Column(name = "desig_id")
    private Integer desigId;

    @Column(name = "dept_id")
    private Integer deptId;

    @Column(name = "desig_name")
    private String desigName;

    @Column(name = "remark")
    private String remark;

    @Column(name = "status_cd")
    private String statusCd;


    public DesignationAudit(DesignationEntity designationEntity) {
        super(designationEntity.getCreatedDate(), designationEntity.getCreatedUserId(), designationEntity.getUpdatedDate(), designationEntity.getUpdatedUserId());
        this.desigId = designationEntity.getDesigId();
        this.deptId = designationEntity.getDeptId();
        this.desigName = designationEntity.getDesigName();
        this.remark = designationEntity.getRemark();
        this.statusCd = designationEntity.getStatusCd();
    }
}
