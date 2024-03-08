package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.entity.CompanyMasterAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyMasterAuditRepo extends JpaRepository<CompanyMasterAudit, Integer> {
}
