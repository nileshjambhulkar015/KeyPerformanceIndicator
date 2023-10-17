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

@Table(name = "site_audit")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SiteAudit extends AuditEnabledEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "site_aud_id")
    private Integer siteAuditId;

    @Column(name = "site_id")
    private Integer siteId;

    @Column(name = "region_id")
    private Integer regionId;

    @Column(name = "site_name")
    private String siteName;

    @Column(name = "remark")
    private String remark;

    @Column(name = "status_cd")
    private String statusCd;

    public SiteAudit(SiteEntity siteEntity) {
        super(siteEntity.getCreatedDate(), siteEntity.getCreatedUserId(), siteEntity.getUpdatedDate(), siteEntity.getUpdatedUserId());
        this.siteId = siteEntity.getSiteId();
        this.regionId = siteEntity.getRegionId();
        this.siteName = siteEntity.getSiteName();
        this.remark = siteEntity.getRemark();
        this.statusCd = siteEntity.getStatusCd();
    }
}
