package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.entity.RegionAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionAuditRepo extends JpaRepository<RegionAudit, Integer> {
}
