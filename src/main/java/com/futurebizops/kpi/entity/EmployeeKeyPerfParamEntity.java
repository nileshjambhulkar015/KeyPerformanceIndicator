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

@Table(name = "employee_key_perf_parameter")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeKeyPerfParamEntity extends AuditEnabledEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ekpp_id")
    private Integer ekppId;

    @Column(name = "ekpp_month")
    private Instant ekppMonth;

    @Column(name = "kpp_id")
    private Integer kppId;

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

    @Builder(builderMethodName = "keyEmployeePerfParamEntityBuilder")
    public EmployeeKeyPerfParamEntity(Integer ekppId, Integer kppId, Integer empId, String empEId, Integer roleId,Integer deptId, Integer desigId, Instant ekppMonth, String ekppAchivedWeight, String ekppOverallAchieve, String ekppOverallTaskComp, Instant ekppAppliedDate, String ekppEvidence, String ekppStatus, String remark, String statusCd, Instant createdDate, String createdUserId, Instant updatedDate, String updatedUserId) {
        super(createdDate, createdUserId, updatedDate, updatedUserId);
        this.ekppId = ekppId;
        this.kppId = kppId;
        this.empId = empId;
        this.empEId=empEId;
        this.roleId=roleId;
        this.deptId = deptId;
        this.desigId = desigId;
        this.ekppMonth = ekppMonth;
        this.ekppAchivedWeight = ekppAchivedWeight;
        this.ekppOverallAchieve = ekppOverallAchieve;
        this.ekppOverallTaskComp = ekppOverallTaskComp;
        this.ekppAppliedDate = ekppAppliedDate;
        this.ekppEvidence = ekppEvidence;
        this.ekppStatus = ekppStatus;
        this.remark = remark;
        this.statusCd = statusCd;
    }
}