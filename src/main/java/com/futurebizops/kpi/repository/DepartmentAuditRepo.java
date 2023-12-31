package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.entity.DepartmentAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentAuditRepo extends JpaRepository<DepartmentAudit, Integer> {
}
