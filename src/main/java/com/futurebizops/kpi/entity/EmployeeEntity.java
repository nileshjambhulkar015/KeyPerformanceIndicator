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

@Table(name = "employee")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeEntity extends AuditEnabledEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	@Builder(builderMethodName = "employeeEntityBuilder")
	public EmployeeEntity(Integer empId,String empEId, Integer depId,  Integer desigId, Integer roleId, Integer regionId, Integer siteId,String empFirstName, String empMiddleName, String empLastName, String empDob, String empMobileNo, String empEmerMobileNo, String empPhoto, String emailId, String tempAddress, String permAddress, String empGender, String empBloodgroup, String remark,String statusCd, String createdUserId, Instant createdDate, Instant updatedDate, String updatedUserId) {
		super(createdDate, createdUserId, updatedDate, updatedUserId);
		this.empId = empId;
		this.empEId=empEId;
		this.deptId = depId;
		this.desigId = desigId;
		this.roleId = roleId;
		this.regionId=regionId;
		this.siteId=siteId;
		this.empFirstName = empFirstName;
		this.empMiddleName = empMiddleName;
		this.empLastName = empLastName;
		this.empDob = empDob;
		this.empMobileNo =empMobileNo;
		this.empEmerMobileNo = empEmerMobileNo;
		this.empPhoto =empPhoto;
		this.emailId= emailId;
		this.tempAddress = tempAddress;
		this.permAddress= permAddress;
		this.empGender =empGender;
		this.empBloodgroup= empBloodgroup;
		this.remark = remark;
		this.statusCd =statusCd;
	}
}