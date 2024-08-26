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

@Table(name = "announcement_type_audit")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementTypeAudit extends AuditEnabledEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "announ_type_AUD_ID")
    private Integer announTypeAuditId;

    @Column(name = "announ_type_ID")
    private Integer announTypeId;

    @Column(name = "announ_type_NAME")
    private String announTypeName;

    @Column(name = "remark")
    private String remark;

    @Column(name = "status_cd")
    private String statusCd;

    public AnnouncementTypeAudit(AnnouncementTypeEntity announcementTypeEntity) {
        super(announcementTypeEntity.getCreatedDate(), announcementTypeEntity.getCreatedUserId(), announcementTypeEntity.getUpdatedDate(), announcementTypeEntity.getUpdatedUserId());
        this.announTypeId = announcementTypeEntity.getAnnounTypeId();
        this.announTypeName = announcementTypeEntity.getAnnounTypeName();
        this.remark = announcementTypeEntity.getRemark();
        this.statusCd = announcementTypeEntity.getStatusCd();
    }
}
