package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.entity.ReportEvidenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface ReportEvidenceRepo extends JpaRepository<ReportEvidenceEntity, Integer> {

  Optional<ReportEvidenceEntity> findByEmpIdAndEvMonth(Integer empId, Instant evMonth);
}
