package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.entity.ReportEmployeeKppDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportEmployeeKppDetailsRepo extends JpaRepository<ReportEmployeeKppDetailsEntity, Integer> {
}
