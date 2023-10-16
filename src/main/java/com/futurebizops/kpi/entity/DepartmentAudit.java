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

@Table(name = "department_audit")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentAudit extends AuditEnabledEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dept_aud_id")
    private Integer deptAuditId;

    @Column(name = "dept_id")
    private Integer deptId;

    @Column(name = "dept_name")
    private String deptName;

    @Column(name = "remark")
    private String remark;

    @Column(name = "status_cd")
    private String statusCd;

    public DepartmentAudit(DepartmentEntity departmentEntity) {
        super(departmentEntity.getCreatedDate(), departmentEntity.getCreatedUserId(), departmentEntity.getUpdatedDate(), departmentEntity.getUpdatedUserId());
        this.deptId = departmentEntity.getDeptId();
        this.deptName = departmentEntity.getDeptName();
        this.remark = departmentEntity.getRemark();
        this.statusCd = departmentEntity.getStatusCd();
    }
}
