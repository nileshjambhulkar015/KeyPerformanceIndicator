package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.entity.EmployeeLoginAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeLoginAuditRepo extends JpaRepository<EmployeeLoginAudit, Integer> {
}
