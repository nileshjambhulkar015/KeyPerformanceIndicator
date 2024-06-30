package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.entity.ComplaintTypeAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintTypeAuditRepo extends JpaRepository<ComplaintTypeAudit, Integer> {
}
