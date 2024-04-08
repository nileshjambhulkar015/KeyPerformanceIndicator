package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.entity.EvidenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface EvidenceRepo extends JpaRepository<EvidenceEntity, Integer> {

    public void deleteByEmpId(Integer empId);

    @Query(value = "select emp_id,ev_file_name,ev_content_type,ev_month,status_cd from evidence where emp_id=:empId  and status_cd=:statusCd", nativeQuery = true)
    public       List<Object[]> getEvidenceDetails(Integer empId, String statusCd);

    @Query(value = "select emp_id,ev_file_name from evidence where emp_id=:empId  and status_cd=:statusCd", nativeQuery = true)
    public List<Object[]> getEvidenceDetailsByEmpId(Integer empId, String statusCd);

}