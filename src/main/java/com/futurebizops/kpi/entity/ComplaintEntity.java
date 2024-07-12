package com.futurebizops.kpi.entity;

import io.swagger.v3.oas.annotations.media.Schema;
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
import java.time.Instant;

@Table(name = "employee_complaint")
@Entity
@Data
@AllArgsConstructor
@DynamicUpdate
@NoArgsConstructor
public class ComplaintEntity extends AuditEnabledEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_comp_id")
    private Integer empCompId;

    @Column(name = "comp_id")
    private String compId;

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

    @Column(name = "comp_type_role_id")
    private Integer compTypeRoleId;

    @Column(name = "comp_type_dept_id")
    private Integer compTypeDeptId;

    @Column(name = "comp_type_id")
    private Integer compTypeId;

    @Column(name = "emp_comp_desc")
    private String compDesc;

    @Column(name = "comp_status")
        private String compStatus;

    @Column(name = "remark")
    private String remark;

    @Column(name = "status_cd")
    private String statusCd;
}
