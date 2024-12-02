package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.entity.AnnouncementTypeAudit;
import com.futurebizops.kpi.entity.DepartmentEntity;
import com.futurebizops.kpi.entity.EmployeeTypeEntity;
import com.futurebizops.kpi.entity.FinancialYearEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FinancialYearRepo extends JpaRepository<FinancialYearEntity, Integer> {

    Optional<FinancialYearEntity> findByFinYearEqualsIgnoreCase(String finYearName);

    @Modifying
    @Query(value = "update financial_year set status_cd='I' where fin_year_id =:finYearId", nativeQuery = true)
    public int deleteFinancialYear(@Param("finYearId") Integer finYearId);

    @Query(value = "select * from financial_year finyear where finyear.status_cd ='A'", nativeQuery = true)
    public List<FinancialYearEntity> ddAllFinancialYear();
}
