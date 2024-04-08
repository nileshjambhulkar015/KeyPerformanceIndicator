package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.entity.ReportEvidenceAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportEvidenceAuditRepo extends JpaRepository<ReportEvidenceAudit, Integer> {
}
