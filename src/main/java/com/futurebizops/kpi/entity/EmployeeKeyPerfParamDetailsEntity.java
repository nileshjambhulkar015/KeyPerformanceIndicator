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

@Table(name = "employee_key_perf_parameter_details")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeKeyPerfParamDetailsEntity extends AuditEnabledEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ekpp_did")
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

    @Column(name = "ekpp_emp_achived_weight")
    private String empAchivedWeight;

    @Column(name = "ekpp_emp_overall_achieve")
    private String empOverallAchieve;

    @Column(name = "ekpp_emp_overall_task_comp")
    private String empOverallTaskComp;

    @Column(name = "hod_emp_id")
    private Integer hodEmpId;

    @Column(name = "ekpp_hod_achived_weight")
    private String hodAchivedWeight;

    @Column(name = "ekpp_hod_overall_achieve")
    private String hodOverallAchieve;

    @Column(name = "ekpp_hod_overall_task_comp")
    private String hodOverallTaskComp;

    @Column(name = "gm_emp_id")
    private Integer gmEmpId;

    @Column(name = "ekpp_gm_achived_weight")
    private String gmAchivedWeight;

    @Column(name = "ekpp_gm_overall_achieve")
    private String gmOverallAchieve;

    @Column(name = "ekpp_gm_overall_task_comp")
    private String gmOverallTaskComp;

    @Column(name = "status_cd")
    private String statusCd;

    @Builder(builderMethodName = "keyEmployeePerfParamEntityBuilder")
    public EmployeeKeyPerfParamDetailsEntity(Integer ekppId, Integer kppId, Integer empId, String empEId, Integer roleId, Integer deptId, Integer desigId, Instant ekppMonth, String ekppAchivedWeight, String ekppOverallAchieve, String ekppOverallTaskComp, Instant createdDate, String createdUserId, Instant updatedDate, String updatedUserId) {
        super(createdDate, createdUserId, updatedDate, updatedUserId);
        this.ekppId = ekppId;
        this.kppId = kppId;
        this.empId = empId;
        this.empEId=empEId;
        this.roleId=roleId;
        this.deptId = deptId;
        this.desigId = desigId;
        this.ekppMonth = ekppMonth;
        this.empAchivedWeight = ekppAchivedWeight;
        this.empOverallAchieve = ekppOverallAchieve;
        this.empOverallTaskComp = ekppOverallTaskComp;
    }
}