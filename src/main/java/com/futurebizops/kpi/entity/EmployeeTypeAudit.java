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

@Table(name = "employee_type_audit")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeTypeAudit extends AuditEnabledEntity{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_type_aud_id")
    private Integer empTypeAuditId;

    @Column(name = "emp_type_id")
    private Integer empTypeId;

    @Column(name = "emp_type_name")
    private String empTypeName;

    @Column(name = "remark")
    private String remark;

    @Column(name = "status_cd")
    private String statusCd;

    public EmployeeTypeAudit(EmployeeTypeEntity departmentEntity) {
        super(departmentEntity.getCreatedDate(), departmentEntity.getCreatedUserId(), departmentEntity.getUpdatedDate(), departmentEntity.getUpdatedUserId());
        this.empTypeId = departmentEntity.getEmpTypeId();
        this.empTypeName = departmentEntity.getEmpTypeName();
        this.remark = departmentEntity.getRemark();
        this.statusCd = departmentEntity.getStatusCd();
    }
}
