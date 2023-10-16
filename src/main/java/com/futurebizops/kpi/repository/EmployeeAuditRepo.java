package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.entity.EmployeeAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeAuditRepo extends JpaRepository<EmployeeAudit, Integer> {
}
