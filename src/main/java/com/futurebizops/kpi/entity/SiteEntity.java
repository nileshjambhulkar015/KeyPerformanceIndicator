package com.futurebizops.kpi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Table(name = "site")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SiteEntity extends AuditEnabledEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Builder(builderMethodName = "siteEntityBuilder")
    public SiteEntity(Integer siteId, Integer regionId, String siteName, String remark, String statusCd, Instant createdDate, String createdUserId, Instant updatedDate, String updatedUserId) {
        super(createdDate, createdUserId, updatedDate, updatedUserId);
        this.siteId = siteId;
        this.regionId = regionId;
        this.siteName = siteName;
        this.remark = remark;
        this.statusCd = statusCd;
    }

}
