package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.entity.SiteAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiteAuditRepo extends JpaRepository<SiteAudit, Integer> {
}
