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

@Table(name = "employee_audit")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeAudit extends AuditEnabledEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_aud_id")
    private Integer empAuditId;

    @Column(name = "emp_id")
    private Integer empId;

    @Column(name = "emp_eid")
    private String empEId;

    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "dept_id")
    private Integer depId;

    @Column(name = "desig_id")
    private Integer desigId;

    @Column(name = "reporting_emp_id")
    private Integer reportingEmpId;

    @Column(name = "region_id")
    private Integer regionId;

    @Column(name = "site_id")
    private Integer siteId;


    @Column(name = "emp_fname")
    private String empFirstName;

    @Column(name = "emp_mname")
    private String empMiddleName;

    @Column(name = "emp_lname")
    private String empLastName;

    @Column(name = "emp_dob")
    private String empDob;

    @Column(name = "emp_mbno")
    private String empMobileNo;

    @Column(name = "emp_emer_mbno")
    private String empEmerMobileNo;

    @Column(name = "emp_photo")
    private String empPhoto;

    @Column(name = "emp_email_id")
    private String emailId;

    @Column(name = "emp_temp_address")
    private String tempAddress;

    @Column(name = "emp_perm_address")
    private String permAddress;

    @Column(name = "emp_gender")
    private String empGender;

    @Column(name = "emp_blood_group")
    private String empBloodgroup;

    @Column(name = "remark")
    private String remark;

    @Column(name = "status_cd")
    private String statusCd;

    public EmployeeAudit(EmployeeEntity employeeEntity) {
        super(employeeEntity.getCreatedDate(), employeeEntity.getCreatedUserId(), employeeEntity.getUpdatedDate(), employeeEntity.getUpdatedUserId());
        this.empId = employeeEntity.getEmpId();
        this.empEId = employeeEntity.getEmpEId();
        this.roleId = employeeEntity.getRoleId();
        this.depId = employeeEntity.getDeptId();
        this.desigId = employeeEntity.getDesigId();
        this.reportingEmpId = employeeEntity.getReportingEmpId();
        this.regionId = employeeEntity.getRegionId();
        this.siteId = employeeEntity.getSiteId();
        this.empFirstName = employeeEntity.getEmpFirstName();
        this.empMiddleName = employeeEntity.getEmpMiddleName();
        this.empLastName = employeeEntity.getEmpLastName();
        this.empDob = employeeEntity.getEmpDob();
        this.empMobileNo = employeeEntity.getEmpMobileNo();
        this.empEmerMobileNo = employeeEntity.getEmpEmerMobileNo();
        this.empPhoto = employeeEntity.getEmpPhoto();
        this.emailId = employeeEntity.getEmailId();
        this.tempAddress = employeeEntity.getTempAddress();
        this.permAddress = employeeEntity.getPermAddress();
        this.empGender = employeeEntity.getEmpGender();
        this.empBloodgroup = employeeEntity.getEmpBloodgroup();
        this.remark = employeeEntity.getRemark();
        this.statusCd = employeeEntity.getStatusCd();
    }
}
