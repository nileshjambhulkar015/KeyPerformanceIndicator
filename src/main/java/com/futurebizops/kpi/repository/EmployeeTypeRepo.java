package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.constants.SQLQueryConstants;
import com.futurebizops.kpi.entity.EmployeeTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeTypeRepo extends JpaRepository<EmployeeTypeEntity, Integer> {

    Optional<EmployeeTypeEntity> findByEmpTypeNameEqualsIgnoreCase(String empTypeName);

    @Query(value = SQLQueryConstants.EMPLOYEE_TYPE_QUERY, nativeQuery = true)
    List<Object[]> getEmployeeTypeDetail(@Param("empTypeId") Integer empTypeId, @Param("empTypeName") String empTypeName, @Param("statusCd") String statusCd);

}
