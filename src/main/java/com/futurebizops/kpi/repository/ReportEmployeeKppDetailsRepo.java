package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.entity.ReportEmployeeKppDetailsEntity;
import com.futurebizops.kpi.entity.ReportEmployeeKppMasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportEmployeeKppDetailsRepo extends JpaRepository<ReportEmployeeKppDetailsEntity, Integer> {

    List<ReportEmployeeKppDetailsEntity> findByEmpIdAndStatusCd(Integer empId, String statusCd);

}
