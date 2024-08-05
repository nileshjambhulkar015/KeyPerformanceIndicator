package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.entity.ComplaintTypeAudit;
import com.futurebizops.kpi.entity.RequestTypeAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestTypeAuditRepo extends JpaRepository<RequestTypeAudit, Integer> {
}
