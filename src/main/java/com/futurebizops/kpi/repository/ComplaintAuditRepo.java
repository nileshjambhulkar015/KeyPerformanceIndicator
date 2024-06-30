package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.entity.ComplaintAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintAuditRepo extends JpaRepository<ComplaintAudit, Integer> {
}
