package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.entity.DesignationAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignationAuditRepo extends JpaRepository<DesignationAudit, Integer> {
}
