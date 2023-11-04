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

@Table(name = "key_perf_parameter")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeyPerfParamEntity extends AuditEnabledEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kpp_id")
    private Integer kppId;

    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "dept_id")
    private Integer deptId;

    @Column(name = "desig_id")
    private Integer desigId;

    @Column(name = "kpp_objective")
    private String kppObjective;

    @Column(name = "kpp_performance_indi")
    private String kppPerformanceIndi;

    @Column(name = "kpp_overall_tar")
    private String kppOverallTarget;

    @Column(name = "kpp_target_period")
    private String kppTargetPeriod;

    @Column(name = "kpp_unit_of_measu")
    private String kppUoM;

    @Column(name = "kpp_overall_weightage")
    private String kppOverallWeightage;
    @Column(name = "kpp_rating1")
    private String kppRating1;

    @Column(name = "kpp_rating2")
    private String kppRating2;

    @Column(name = "kpp_rating3")
    private String kppRating3;

    @Column(name = "kpp_rating4")
    private String kppRating4;

    @Column(name = "kpp_rating5")
    private String kppRating5;

    @Column(name = "kpp_description")
    private String kppDescription;

    @Column(name = "remark")
    private String remark;

    @Column(name = "status_cd")
    private String statusCd;

    @Builder(builderMethodName = "keyPerfParamEntityBuilder")
    public KeyPerfParamEntity(Integer kppId, Integer roleId, Integer deptId, Integer desigId, String kppObjective, String kppPerformanceIndi, String kppOverallTarget, String kppTargetPeriod, String kppUoM, String kppOverallWeightage, String kppRating1, String kppRating2, String kppRating3, String kppRating4, String kppRating5, String kppDescription, String remark, String statusCd, Instant createdDate, String createdUserId, Instant updatedDate, String updatedUserId) {
        super(createdDate, createdUserId, updatedDate, updatedUserId);
        this.kppId = kppId;
        this.roleId = roleId;
        this.deptId = deptId;
        this.desigId = desigId;
        this.kppObjective = kppObjective;
        this.kppPerformanceIndi = kppPerformanceIndi;
        this.kppOverallTarget = kppOverallTarget;
        this.kppTargetPeriod = kppTargetPeriod;
        this.kppUoM = kppUoM;
        this.kppOverallWeightage = kppOverallWeightage;
        this.kppRating1 = kppRating1;
        this.kppRating2 = kppRating2;
        this.kppRating3 = kppRating3;
        this.kppRating4 = kppRating4;
        this.kppRating5 = kppRating5;
        this.kppDescription = kppDescription;
        this.remark = remark;
        this.statusCd = statusCd;
    }
}
