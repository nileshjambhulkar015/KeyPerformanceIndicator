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

@Table(name = "employee_login_audit")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeLoginAudit extends AuditEnabledEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_audit_login_id")
    private Integer empAuditLoginId;


    @Column(name = "emp_email_id")
    private String emailId;

    @Column(name = "emp_eid")
    private String empEId;

    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "dept_id")
    private Integer deptId;

    @Column(name = "desig_id")
    private Integer desigId;

    @Column(name = "emp_mbno")
    private String empMobileNo;


    @Column(name = "EMP_PASSWORD")
    private String empPassword;

    @Column(name = "remark")
    private String remark;

    @Column(name = "status_cd")
    private String statusCd;

    public EmployeeLoginAudit(EmployeeLoginEntity employeeLoginEntity) {
        super(employeeLoginEntity.getCreatedDate(), employeeLoginEntity.getCreatedUserId(), employeeLoginEntity.getUpdatedDate(), employeeLoginEntity.getUpdatedUserId());
        this.empEId=employeeLoginEntity.getEmpEId();
        this.roleId = employeeLoginEntity.getRoleId();
       this.deptId=employeeLoginEntity.getDeptId();
       this.desigId=employeeLoginEntity.getDesigId();
        this.empMobileNo = employeeLoginEntity.getEmpMobileNo();
        this.emailId = employeeLoginEntity.getEmailId();
        this.empPassword = employeeLoginEntity.getEmpPassword();
        this.remark = employeeLoginEntity.getRemark();
        this.statusCd = employeeLoginEntity.getStatusCd();
    }
}
