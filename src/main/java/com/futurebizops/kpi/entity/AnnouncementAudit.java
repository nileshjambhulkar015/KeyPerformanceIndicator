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

@Table(name = "announcement_master_audit")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementAudit extends AuditEnabledEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "announ_aud_id")
    private Integer announAuditId;

    @Column(name = "announ_id")
    private Integer announId;

    @Column(name = "announ_start_date")
    private Instant announStartDate;

    @Column(name = "announ_end_date")
    private Instant announEndDate;

    @Column(name = "announ_created_by_emp_id")
    private Integer announCreatedByEmpId;

    @Column(name = "announ_created_by_emp_eid")
    private String announCreatedByEmpEId;

    @Column(name = "announ_created_by_emp_name")
    private String announCreatedByEmpName;

    @Column(name = "announ_created_by_emp_role_id")
    private Integer announCreatedByRoleId;

    @Column(name = "announ_created_by_emp_role_name")
    private String announCreatedByRoleName;

    @Column(name = "announ_created_by_emp_dept_id")
    private Integer announCreatedByDeptId;

    @Column(name = "announ_created_by_emp_dept_name")
    private String announCreatedByDeptName;

    @Column(name = "announ_created_by_emp_desig_id")
    private Integer announCreatedByDesigId;

    @Column(name = "announ_created_by_emp_desig_name")
    private String announCreatedByDesigName;

    @Column(name = "announ_venue")
    private String announVenue;

    @Column(name = "announ_title")
    private String announTitle;

    @Column(name = "announ_description")
    private String announDescription;

    @Column(name = "announ_status")
    private String announStatus;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "STATUS_CD")
    private String statusCd;

    public AnnouncementAudit(AnnouncementEntity announcementEntity) {
        super(announcementEntity.getCreatedDate(), announcementEntity.getCreatedUserId(), announcementEntity.getUpdatedDate(), announcementEntity.getUpdatedUserId());
        this.announId=announcementEntity.getAnnounId();
        this.announStartDate = announcementEntity.getAnnounStartDate();
        this.announEndDate=announcementEntity.getAnnounEndDate();
        this.announCreatedByEmpId=announcementEntity.getAnnounCreatedByEmpId();
        this.announCreatedByEmpEId = announcementEntity.getAnnounCreatedByEmpEId();
        this.announCreatedByEmpName = announcementEntity.getAnnounCreatedByEmpName();
        this.announCreatedByRoleId = announcementEntity.getAnnounCreatedByRoleId();

        this.announCreatedByRoleName=announcementEntity.getAnnounCreatedByRoleName();
        this.announCreatedByDeptId = announcementEntity.getAnnounCreatedByDeptId();
        this.announCreatedByDeptName=announcementEntity.getAnnounCreatedByDeptName();
        this.announCreatedByDesigId=announcementEntity.getAnnounCreatedByDesigId();

        this.announCreatedByDesigName = announcementEntity.getAnnounCreatedByDesigName();
        this.announVenue = announcementEntity.getAnnounVenue();
        this.announTitle = announcementEntity.getAnnounTitle();

        this.announDescription = announcementEntity.getAnnounDescription();
        this.announStatus = announcementEntity.getAnnounStatus();


        this.remark = announcementEntity.getRemark();
        this.statusCd = announcementEntity.getStatusCd();
    }
}