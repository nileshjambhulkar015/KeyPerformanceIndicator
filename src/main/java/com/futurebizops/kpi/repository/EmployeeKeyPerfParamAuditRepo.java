package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.entity.EmployeeKeyPerfParamAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeKeyPerfParamAuditRepo extends JpaRepository<EmployeeKeyPerfParamAudit, Integer> {
}
