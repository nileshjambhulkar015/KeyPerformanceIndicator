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

@Table(name = "designation")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DesignationEntity extends AuditEnabledEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "desig_id")
    private Integer desigId;

    @Column(name = "dept_id")
    private Integer deptId;

    @Column(name = "desig_name")
    private String desigName;

    @Column(name = "remark")
    private String remark;

    @Column(name = "status_cd")
    private String statusCd;

    @Builder(builderMethodName = "designationEntityBuilder")
    public DesignationEntity(Integer desigId, Integer deptId, String desigName, String remark, String statusCd, Instant createdDate, String createdUserId, Instant updatedDate, String updatedUserId) {
        super(createdDate, createdUserId, updatedDate, updatedUserId);
        this.desigId = desigId;
        this.deptId = deptId;
        this.desigName = desigName;
        this.remark = remark;
        this.statusCd = statusCd;
    }

}
