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

@Table(name = "key_perf_parameter_audit")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeyPerfParamAudit extends AuditEnabledEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kpp_aud_id")
    private Integer kppAuditId;

    @Column(name = "kpp_id")
    private Integer kppId;

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

    public KeyPerfParamAudit(KeyPerfParamEntity keyPerfParamEntity) {
        super(keyPerfParamEntity.getCreatedDate(), keyPerfParamEntity.getCreatedUserId(), keyPerfParamEntity.getUpdatedDate(), keyPerfParamEntity.getUpdatedUserId());
        this.kppId = keyPerfParamEntity.getKppId();
        this.deptId = keyPerfParamEntity.getDeptId();
        this.desigId = keyPerfParamEntity.getDesigId();
        this.kppObjective = keyPerfParamEntity.getKppObjective();
        this.kppPerformanceIndi = keyPerfParamEntity.getKppPerformanceIndi();
        this.kppOverallTarget = keyPerfParamEntity.getKppOverallTarget();
        this.kppTargetPeriod = keyPerfParamEntity.getKppTargetPeriod();
        this.kppUoM = keyPerfParamEntity.getKppUoM();
        this.kppOverallWeightage = keyPerfParamEntity.getKppOverallWeightage();
        this.kppRating1 = keyPerfParamEntity.getKppRating1();
        this.kppRating2 = keyPerfParamEntity.getKppRating2();
        this.kppRating3 = keyPerfParamEntity.getKppRating2();
        this.kppRating4 = keyPerfParamEntity.getKppRating4();
        this.kppRating5 = keyPerfParamEntity.getKppRating5();
        this.kppDescription = keyPerfParamEntity.getKppDescription();
        this.remark = keyPerfParamEntity.getRemark();
        this.statusCd = keyPerfParamEntity.getStatusCd();
    }
}
