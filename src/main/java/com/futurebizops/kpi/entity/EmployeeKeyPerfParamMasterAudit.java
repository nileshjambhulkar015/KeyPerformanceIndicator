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
import java.time.Instant;

@Table(name = "employee_key_perf_parameter_master_audit")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeKeyPerfParamMasterAudit extends AuditEnabledEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ekpp_aud_mid")
    private Integer eKppAuditMId;

    @Column(name = "ekpp_mid")
    private Integer eKppMId;

    @Column(name = "ekpp_month")
    private Instant ekppMonth;

    @Column(name = "emp_id")
    private Integer empId;

    @Column(name = "emp_eid")
    private String empEId;

    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "dept_id")
    private Integer deptId;

    @Column(name = "desig_id")
    private Integer desigId;

    @Column(name = "total_emp_achived_weight")
    private String totalAchivedWeight;

    @Column(name = "total_emp_overall_achieve")
    private String totalOverallAchieve;

    @Column(name = "total_emp_overall_task_comp")
    private String totalOverallTaskComp;

    @Column(name = "emp_ekpp_applied_date")
    private Instant empKppAppliedDate;

    @Column(name = "emp_ekpp_status")
    private String empKppStatus;

    @Column(name = "emp_remark")
    private String empRemark;

    @Column(name = "emp_ekpp_evidence")
    private String empEvidence;

    @Column(name = "hod_emp_id")
    private String hodEmpId;

    @Column(name = "total_hod_achived_weight")
    private String hodAchivedWeight;

    @Column(name = "total_hod_overall_achieve")
    private String hodOverallAchieve;

    @Column(name = "total_hod_overall_task_comp")
    private String hodOverallTaskComp;

    @Column(name = "hod_approved_date")
    private Instant hodKppAppliedDate;

    @Column(name = "hod_ekpp_status")
    private String hodKppStatus;

    @Column(name = "hod_remark")
    private String hodRemark;

    @Column(name = "gm_emp_id")
    private String gmEmpId;

    @Column(name = "total_gm_achived_weight")
    private String gmAchivedWeight;

    @Column(name = "total_gm_overall_achieve")
    private String gmOverallAchieve;

    @Column(name = "total_gm_overall_task_comp")
    private String gmOverallTaskComp;

    @Column(name = "gm_approved_date")
    private Instant gmKppAppliedDate;

    @Column(name = "gm_ekpp_status")
    private String gmKppStatus;

    @Column(name = "gm_remark")
    private String gmRemark;

    @Column(name = "remark")
    private String remark;

    @Column(name = "status_cd")
    private String statusCd;
}
