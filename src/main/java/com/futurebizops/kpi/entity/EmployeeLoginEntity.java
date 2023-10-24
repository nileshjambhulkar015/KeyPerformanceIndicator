package com.futurebizops.kpi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Table(name = "employee_login")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeLoginEntity extends AuditEnabledEntity {

    @Id
    @Column(name = "emp_mbno")
    private String empMobileNo;

    @Column(name = "dept_id")
    private Integer deptId;

    @Column(name = "desig_id")
    private Integer desigId;

    @Column(name = "emp_email_id")
    private String emailId;

    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "EMP_PASSWORD")
    private String empPassword;

    @Column(name = "remark")
    private String remark;

    @Column(name = "status_cd")
    private String statusCd;

    @Builder(builderMethodName = "employeeLoginEntityBuilder")
    public EmployeeLoginEntity(Integer deptId, Integer desigId,String empMobileNo, String emailId, Integer roleId, String empPassword, String remark, String statusCd, Instant createdDate, String createdUserId, Instant updatedDate, String updatedUserId) {
        super(createdDate, createdUserId, updatedDate, updatedUserId);
        this.deptId=deptId;
        this.desigId=desigId;
        this.empMobileNo = empMobileNo;
        this.emailId = emailId;
        this.roleId = roleId;
        this.empPassword = empPassword;
        this.remark = remark;
        this.statusCd = statusCd;
    }
}
