package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.entity.KeyPerfParamAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyPerfParameterAuditRepo extends JpaRepository<KeyPerfParamAudit, Integer> {
}
