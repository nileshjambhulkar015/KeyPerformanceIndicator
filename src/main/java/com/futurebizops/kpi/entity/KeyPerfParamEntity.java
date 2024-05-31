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

    @Column(name = "kpp_objective_no")
    private String kppObjectiveNo;

    @Column(name = "kpp_objective")
    private String kppObjective;

    @Column(name = "kpp_performance_indi")
    private String kppPerformanceIndi;

    @Column(name = "kpp_overall_tar")
    private String kppOverallTarget;

    @Column(name = "kpp_target_period")
    private String kppTargetPeriod;

    @Column(name = "uom_id")
    private Integer uomId;

   // @Column(name = "kpp_overall_weightage")
   // private String kppOverallWeightage;

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

    @Column(name = "remark")
    private String remark;

    @Column(name = "status_cd")
    private String statusCd;
}
