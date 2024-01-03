package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.entity.ReportEmployeeKppMasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportEmployeeKppMasterRepo extends JpaRepository<ReportEmployeeKppMasterEntity, Integer> {
}
