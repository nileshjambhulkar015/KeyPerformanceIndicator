package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.entity.EmployeeKppMasterAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeKppMasterAuditRepo extends JpaRepository<EmployeeKppMasterAudit, Integer> {
}
