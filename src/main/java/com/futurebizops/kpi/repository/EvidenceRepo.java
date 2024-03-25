package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.entity.EvidenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvidenceRepo extends JpaRepository<EvidenceEntity, Integer> {
}
