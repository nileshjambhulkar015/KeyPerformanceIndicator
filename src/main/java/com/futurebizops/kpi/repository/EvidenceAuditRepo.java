package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.entity.EvidenceAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvidenceAuditRepo extends JpaRepository<EvidenceAudit, Integer> {
}