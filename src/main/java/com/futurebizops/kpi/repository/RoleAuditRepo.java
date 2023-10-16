package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.entity.RoleAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleAuditRepo extends JpaRepository<RoleAudit, Integer> {
}
