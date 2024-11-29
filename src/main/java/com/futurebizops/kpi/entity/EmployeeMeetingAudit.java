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

@Table(name = "meeting_master_audit")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeMeetingAudit extends AuditEnabledEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meet_aud_id")
    private Integer meetAuditId;

    @Column(name = "meet_id")
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

    public EmployeeMeetingAudit(EmployeeMeetingEntity employeeLoginEntity) {
        super(employeeLoginEntity.getCreatedDate(), employeeLoginEntity.getCreatedUserId(), employeeLoginEntity.getUpdatedDate(), employeeLoginEntity.getUpdatedUserId());
        this.meetId=employeeLoginEntity.getMeetId();
        this.meetStartDate = employeeLoginEntity.getMeetStartDate();
        this.meetEndDate=employeeLoginEntity.getMeetEndDate();
        this.meetCreatedByEmpId=employeeLoginEntity.getMeetCreatedByEmpId();
        this.meetCreatedByEmpEId = employeeLoginEntity.getMeetCreatedByEmpEId();
        this.meetCreatedByEmpName = employeeLoginEntity.getMeetCreatedByEmpName();
        this.meetCreatedByRoleId = employeeLoginEntity.getMeetCreatedByRoleId();

        this.meetCreatedByRoleName=employeeLoginEntity.getMeetCreatedByRoleName();
        this.meetCreatedByDeptId = employeeLoginEntity.getMeetCreatedByDeptId();
        this.meetCreatedByDeptName=employeeLoginEntity.getMeetCreatedByDeptName();
        this.meetCreatedByDesigId=employeeLoginEntity.getMeetCreatedByDesigId();

        this.meetCreatedByDesigName = employeeLoginEntity.getMeetCreatedByDesigName();
        this.meetVenue = employeeLoginEntity.getMeetVenue();
        this.meetTitle = employeeLoginEntity.getMeetTitle();

        this.meetDescription = employeeLoginEntity.getMeetDescription();
        this.meetStatus = employeeLoginEntity.getMeetStatus();


        this.remark = employeeLoginEntity.getRemark();
        this.statusCd = employeeLoginEntity.getStatusCd();
    }
}