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

@Table(name = "uom_audit")
@Entity
@Data
public class UoMAudit extends AuditEnabledEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uom_aud_id")
    private Integer uomAuditId;

    @Column(name = "uom_id")
    private Integer uomId;

    @Column(name = "uom_name")
    private String uomName;

    @Column(name = "remark")
    private String remark;

    @Column(name = "status_cd")
    private String statusCd;

    public UoMAudit(UoMEntity uoMEntity) {
        super(uoMEntity.getCreatedDate(), uoMEntity.getCreatedUserId(), uoMEntity.getUpdatedDate(), uoMEntity.getUpdatedUserId());
        this.uomId = uoMEntity.getUomId();
        this.uomName = uoMEntity.getUomName();
        this.remark = uoMEntity.getRemark();
        this.statusCd = uoMEntity.getStatusCd();
    }
}
