package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.entity.EmployeeKeyPerfParamDetailsAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeKeyPerfParamDetailsAuditRepo extends JpaRepository<EmployeeKeyPerfParamDetailsAudit, Integer> {

    }
