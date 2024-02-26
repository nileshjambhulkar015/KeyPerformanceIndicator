package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.entity.UoMAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UoMAuditRepo extends JpaRepository<UoMAudit, Integer> {
}
