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

@Table(name = "meeting_master")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeMeetingEntity  extends AuditEnabledEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name =  "meet_id")
    private Integer meetId;

    @Column(name = "meet_start_date")
    private Instant meetStartDate;

    @Column(name = "meet_end_date")
    private Instant meetEndDate;

    @Column(name = "meet_created_by_emp_id")
    private Integer meetCreatedByEmpId;

    @Column(name = "meet_created_by_emp_eid")
    private String meetCreatedByEmpEId;

    @Column(name = "meet_created_by_emp_name")
    private String meetCreatedByEmpName;

    @Column(name = "meet_created_by_emp_role_id")
    private Integer meetCreatedByRoleId;

    @Column(name = "meet_created_by_emp_role_name")
    private String meetCreatedByRoleName;

    @Column(name = "meet_created_by_emp_dept_id")
    private Integer meetCreatedByDeptId;

    @Column(name = "meet_created_by_emp_dept_name")
    private String meetCreatedByDeptName;

    @Column(name = "meet_created_by_emp_desig_id")
    private Integer meetCreatedByDesigId;

    @Column(name = "meet_created_by_emp_desig_name")
    private String meetCreatedByDesigName;

    @Column(name = "meet_venue")
    private String meetVenue;

    @Column(name = "meet_title")
    private String meetTitle;

    @Column(name = "meet_description")
    private String meetDescription;

    @Column(name = "meet_status")
    private String meetStatus;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "STATUS_CD")
    private String statusCd;
}