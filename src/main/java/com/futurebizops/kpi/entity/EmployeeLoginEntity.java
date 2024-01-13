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

    @Column(name = "EMP_PASSWORD")
    private String empPassword;

    @Column(name = "remark")
    private String remark;

    @Column(name = "status_cd")
    private String statusCd;
}
