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

@Table(name = "company_master")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyMasterEntity extends  AuditEnabledEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comp_id")
    private Integer companyId;

    @Column(name = "region_id")
    private Integer regionId;

    @Column(name = "site_id")
    private Integer siteId;

    @Column(name = "comp_name")
    private String companyName;

    @Column(name = "comp_address")
    private String companyAddress;

    @Column(name = "comp_mbno")
    private String companyMbNo;

    @Column(name = "comp_finance_year")
    private String companyFinYear;

    @Column(name = "remark")
    private String remark;

    @Column(name = "status_cd")
    private String statusCd;
}
