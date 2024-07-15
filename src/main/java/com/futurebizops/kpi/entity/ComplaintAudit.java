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
import java.time.Instant;

@Table(name = "employee_complaint_audit ")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComplaintAudit extends AuditEnabledEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_comp_audit_id")
    private Integer empCompAuditId;

    @Column(name = "comp_id")
    private String compId;

    @Column(name = "emp_comp_id")
    private Integer empCompId;

    @Column(name = "emp_id")
    private Integer empId;

    @Column(name = "emp_eid")
    private String empEId;

    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "dept_id")
    private Integer deptId;

    @Column(name = "desig_id")
    private Integer desigId;

    @Column(name = "comp_date")
    private Instant compDate;

    @Column(name = "comp_resolve_date")
    private Instant compResolveDate;

    @Column(name = "comp_type_id")
    private Integer compTypeId;

    @Column(name = "emp_comp_desc")
    private String compDesc;

    @Column(name = "comp_status")
    private String compStatus;


    @Column(name = "comp_resolve_emp_id")
    private Integer compResolveEmpId;

    @Column(name = "comp_resolve_emp_name")
    private String compResolveEmpName;

    @Column(name = "comp_resolve_emp_eid")
    private String compResolveEmpEId;

    @Column(name = "remark")
    private String remark;

    @Column(name = "status_cd")
    private String statusCd;

    public ComplaintAudit(ComplaintEntity complaintEntity) {
        super(complaintEntity.getCreatedDate(), complaintEntity.getCreatedUserId(), complaintEntity.getUpdatedDate(), complaintEntity.getUpdatedUserId());
        this.empCompId = complaintEntity.getEmpCompId();
        this.compId = complaintEntity.getCompId();
        this.empId = complaintEntity.getEmpId();
        this.empEId = complaintEntity.getEmpEId();
        this.roleId = complaintEntity.getRoleId();
        this.deptId = complaintEntity.getDeptId();
        this.desigId = complaintEntity.getDesigId();
        this.compDate = Instant.now();
        this.compDesc = complaintEntity.getCompDesc();
        this.compStatus = complaintEntity.getCompStatus();
        this.compTypeId = complaintEntity.getCompTypeId();
        this.compResolveEmpId = complaintEntity.getCompResolveEmpId();
        this.compResolveEmpName = complaintEntity.getCompResolveEmpName();
        this.compResolveEmpId = complaintEntity.getCompResolveEmpId();
        this.remark = complaintEntity.getRemark();
        this.statusCd = complaintEntity.getStatusCd();
    }
}
