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

@Table(name = "employee_kpp_details_audit")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeKppDetailsAudit extends AuditEnabledEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ekpp_aud_did")
    private Integer ekppAuditId;

    @Column(name = "ekpp_did")
    private Integer ekppId;

    @Column(name = "ekpp_month")
    private Instant ekppMonth;

    @Column(name = "kpp_id")
    private Integer kppId;

    @Column(name = "kpp_overall_tar")
    private String kppOverallTarget;

    @Column(name = "kpp_overall_weightage")
    private String kppOverallWeightage;

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

    @Column(name = "avg_overall_rating")
    private String avgOverallRating;

    @Column(name = "avg_overall_achivement_per")
    private String avgOverallPer;

    @Column(name = "status_cd")
    private String statusCd;

    public EmployeeKppDetailsAudit(EmployeeKppDetailsEntity employeeKeyPerfParamEntity) {
        super(employeeKeyPerfParamEntity.getCreatedDate(), employeeKeyPerfParamEntity.getCreatedUserId(), employeeKeyPerfParamEntity.getUpdatedDate(), employeeKeyPerfParamEntity.getUpdatedUserId());
        this.ekppId = employeeKeyPerfParamEntity.getEkppId();
        this.kppId = employeeKeyPerfParamEntity.getKppId();
        this.kppOverallTarget = employeeKeyPerfParamEntity.getKppOverallTarget();
        this.kppOverallWeightage = employeeKeyPerfParamEntity.getKppOverallWeightage();
        this.empId=employeeKeyPerfParamEntity.getEmpId();
        this.empEId=employeeKeyPerfParamEntity.getEmpEId();
        this.roleId=employeeKeyPerfParamEntity.getRoleId();
        this.deptId = employeeKeyPerfParamEntity.getDeptId();
        this.desigId = employeeKeyPerfParamEntity.getDesigId();
        this.ekppMonth = employeeKeyPerfParamEntity.getEkppMonth();
        this.empAchivedWeight = employeeKeyPerfParamEntity.getEmpAchivedWeight();
        this.empOverallAchieve = employeeKeyPerfParamEntity.getEmpOverallAchieve();
        this.empOverallTaskComp = employeeKeyPerfParamEntity.getEmpOverallTaskComp();
        this.avgOverallRating=employeeKeyPerfParamEntity.getAvgOverallRating();
        this.avgOverallPer=employeeKeyPerfParamEntity.getAvgOverallPer();
        this.statusCd = employeeKeyPerfParamEntity.getStatusCd();
    }
}
