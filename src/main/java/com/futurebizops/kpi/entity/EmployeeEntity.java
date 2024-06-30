package com.futurebizops.kpi.entity;

import io.swagger.v3.oas.annotations.media.Schema;
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

	@Column(name = "emp_type_id")
	private Integer empTypeId;

	@Column(name = "reporting_emp_id")
	private Integer reportingEmpId;

	@Column(name = "gm_emp_id")
	private Integer gmEmpId;

	@Column(name = "region_id")
	private Integer regionId;

	@Column(name = "site_id")
	private Integer siteId;

	@Column(name = "comp_id")
	private Integer companyId;

	@Column(name = "emp_fname")
	private String empFirstName;

	@Column(name = "emp_mname")
	private String empMiddleName;

	@Column(name = "emp_lname")
	private String empLastName;

	@Column(name = "emp_dob")
	private Instant empDob;

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
}