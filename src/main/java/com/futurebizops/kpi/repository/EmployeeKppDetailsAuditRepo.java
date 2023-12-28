package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.entity.EmployeeKppDetailsAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeKppDetailsAuditRepo extends JpaRepository<EmployeeKppDetailsAudit, Integer> {

    }
