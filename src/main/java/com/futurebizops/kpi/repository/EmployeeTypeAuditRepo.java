package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.entity.EmployeeTypeAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeTypeAuditRepo extends JpaRepository<EmployeeTypeAudit, Integer> {
}
