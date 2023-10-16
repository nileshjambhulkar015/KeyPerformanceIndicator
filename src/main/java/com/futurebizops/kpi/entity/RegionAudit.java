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

@Table(name = "region_audit")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegionAudit extends AuditEnabledEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "region_aud_id")
    private Integer regionAuditId;

    @Column(name = "region_id")
    private Integer regionId;

    @Column(name = "region_name")
    private String regionName;

    @Column(name = "remark")
    private String remark;

    @Column(name = "status_cd")
    private String statusCd;

    public RegionAudit(RegionEntity regionEntity) {
        super(regionEntity.getCreatedDate(), regionEntity.getCreatedUserId(), regionEntity.getUpdatedDate(), regionEntity.getUpdatedUserId());
        this.regionId = regionEntity.getRegionId();
        this.regionName = regionEntity.getRegionName();
        this.remark = regionEntity.getRemark();
        this.statusCd = regionEntity.getStatusCd();
    }
}
