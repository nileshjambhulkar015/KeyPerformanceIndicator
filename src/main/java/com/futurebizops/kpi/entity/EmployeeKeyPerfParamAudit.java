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
import java.sql.Timestamp;
import java.time.Instant;

@Table(name = "employee_key_perf_parameter_audit")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeKeyPerfParamAudit extends AuditEnabledEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ekpp_aud_id")
    private Integer ekppAuditId;

    @Column(name = "ekpp_id")
    private Integer ekppId;

    @Column(name = "ekpp_month")
    private Instant ekppMonth;
    @Column(name = "kpp_id")
    private Integer kppId;

    @Column(name = "dept_id")
    private Integer deptId;

    @Column(name = "desig_id")
    private Integer desigId;

    @Column(name = "ekpp_achived_weight")
    private String ekppAchivedWeight;

    @Column(name = "ekpp_overall_achieve")
    private String ekppOverallAchieve;

    @Column(name = "ekpp_overall_task_comp")
    private String ekppOverallTaskComp;

    @Column(name = "ekpp_applied_date")
    private Instant ekppAppliedDate;

    @Column(name = "ekpp_evidence")
    private String ekppEvidence;

    @Column(name = "hod_emp_id")
    private String hodEmpId;

    @Column(name = "hod_kpp_status")
    private String hodKppStatus;

    @Column(name = "hod_rating")
    private String hodRating;

    @Column(name = "hod_remark")
    private String hodRemark;

    @Column(name = "hod_approved_date")
    private Instant hodApprovedDate;

    @Column(name = "gm_emp_id")
    private String gmEmpId;

    @Column(name = "gm_kpp_status")
    private String gmKppStatus;

    @Column(name = "gm_rating")
    private String gmRating;

    @Column(name = "gm_remark")
    private String gmRemark;

    @Column(name = "gm_approved_date")
    private Instant gmApprovedDate;

    @Column(name = "ekpp_status")
    private String ekppStatus;
    @Column(name = "remark")
    private String remark;

    @Column(name = "status_cd")
    private String statusCd;

    public EmployeeKeyPerfParamAudit(EmployeeKeyPerfParamEntity employeeKeyPerfParamEntity) {
        super(employeeKeyPerfParamEntity.getCreatedDate(), employeeKeyPerfParamEntity.getCreatedUserId(), employeeKeyPerfParamEntity.getUpdatedDate(), employeeKeyPerfParamEntity.getUpdatedUserId());
        this.ekppId = employeeKeyPerfParamEntity.getEkppId();
        this.kppId = employeeKeyPerfParamEntity.getKppId();
        this.deptId = employeeKeyPerfParamEntity.getDeptId();
        this.desigId = employeeKeyPerfParamEntity.getDesigId();
        this.ekppMonth = employeeKeyPerfParamEntity.getEkppMonth();
        this.ekppAchivedWeight = employeeKeyPerfParamEntity.getEkppAchivedWeight();
        this.ekppOverallAchieve = employeeKeyPerfParamEntity.getEkppOverallAchieve();
        this.ekppOverallTaskComp = employeeKeyPerfParamEntity.getEkppOverallTaskComp();
        this.ekppAppliedDate = employeeKeyPerfParamEntity.getEkppAppliedDate();
        this.ekppEvidence = employeeKeyPerfParamEntity.getEkppEvidence();
        this.ekppStatus = employeeKeyPerfParamEntity.getEkppStatus();
        this.remark = employeeKeyPerfParamEntity.getRemark();
        this.statusCd = employeeKeyPerfParamEntity.getStatusCd();
    }
}
