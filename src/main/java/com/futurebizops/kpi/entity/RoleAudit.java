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

@Table(name = "roles_audit")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleAudit extends AuditEnabledEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_aud_id")
    private Integer roleAuditId;

    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "remark")
    private String remark;

    @Column(name = "status_cd")
    private String statusCd;


    public RoleAudit(RoleEntity roleEntity) {
        super(roleEntity.getCreatedDate(), roleEntity.getCreatedUserId(), roleEntity.getUpdatedDate(), roleEntity.getUpdatedUserId());
        this.roleId = roleEntity.getRoleId();
        this.roleName = roleEntity.getRoleName();
        this.remark = roleEntity.getRemark();
        this.statusCd = roleEntity.getStatusCd();
    }
}
