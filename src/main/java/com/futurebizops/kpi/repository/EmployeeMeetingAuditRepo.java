package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.entity.EmployeeMeetingAudit;
import com.futurebizops.kpi.entity.EmployeeMeetingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeMeetingAuditRepo extends JpaRepository<EmployeeMeetingAudit, Integer> {
}
