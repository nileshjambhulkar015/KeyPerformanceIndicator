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

@Table(name = "request_type_audit")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestTypeAudit extends AuditEnabledEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "req_type_AUD_ID")
    private Integer reqTypeAuditId;

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

    public RequestTypeAudit(RequestTypeEntity requestTypeEntity) {
        super(requestTypeEntity.getCreatedDate(), requestTypeEntity.getCreatedUserId(), requestTypeEntity.getUpdatedDate(), requestTypeEntity.getUpdatedUserId());

        this.deptId = requestTypeEntity.getDeptId();
        this.reqTypeId = requestTypeEntity.getReqTypeId();
        this.reqTypeName = requestTypeEntity.getReqTypeName();
        this.remark = requestTypeEntity.getRemark();
        this.statusCd = requestTypeEntity.getStatusCd();
    }
}
