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


@Table(name = "request_type")
@Entity
@Data
@AllArgsConstructor
@DynamicUpdate
@NoArgsConstructor
public class RequestTypeEntity extends AuditEnabledEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "req_type_id")
    private Integer reqTypeId;

    @Column(name = "dept_id")
    private Integer deptId;

    @Column(name = "req_type_NAME")
    private String reqTypeName;

    @Column(name = "remark")
    private String remark;

    @Column(name = "status_cd")
    private String statusCd;
}
