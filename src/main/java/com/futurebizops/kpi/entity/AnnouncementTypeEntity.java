package com.futurebizops.kpi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Table(name = "announcement_type")
@Entity
@Data
@AllArgsConstructor
@DynamicUpdate
@NoArgsConstructor
public class AnnouncementTypeEntity extends AuditEnabledEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "announ_type_ID")
    private Integer announTypeId;

    @Column(name = "announ_type_NAME")
    private String announTypeName;

    @Column(name = "remark")
    private String remark;

    @Column(name = "status_cd")
    private String statusCd;
}
