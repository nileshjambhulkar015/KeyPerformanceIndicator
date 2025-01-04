package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.constants.SQLQueryConstants;
import com.futurebizops.kpi.entity.KeyPerfParamEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface KeyPerfParameterRepo extends JpaRepository<KeyPerfParamEntity, Integer> {

    public Page<KeyPerfParamEntity> findByStatusCd(String status, Pageable pageable);

    @Modifying
    @Query(value = "update key_perf_parameter set status_cd='I' where kpp_id =:kppId", nativeQuery = true)
    public int deleteKeyPerfomanceParamDetails(@Param("kppId") Integer kppId);

    @Query(value = SQLQueryConstants.KPP_QUERY, nativeQuery = true)
    List<Object[]> getKeyPerfParameterDetail(@Param("kppId") Integer kppId, @Param("kppObjectiveNo") String kppObjectiveNo,@Param("kppObjective") String kppObjective, @Param("statusCd") String statusCd, @Param("sortName") String sortName, @Param("pageSize") Integer pageSize, @Param("pageOffset") Integer pageOffset);

    @Query(value = SQLQueryConstants.KPP_COUNT_QUERY, nativeQuery = true)
    Integer getKeyPerfParameterCount(@Param("kppId") Integer kppId, @Param("kppObjectiveNo") String kppObjectiveNo, @Param("kppObjective") String kppObjective, @Param("statusCd") String statusCd);

    @Query(value = SQLQueryConstants.KPP_BY_ID_QUERY, nativeQuery = true)
    List<Object[]> getKeyPerfParameterDetailById(@Param("kppId") Integer kppId);

    @Query(value = SQLQueryConstants.EMPLOYEE_KPP_STATUS_QUERY, nativeQuery = true)
    List<Object[]> getEmployeeKppStatusDetail(@Param("reportingEmployee") Integer reportingEmployee, @Param("gmEmpId") Integer gmEmpId, @Param("empId") Integer empId, @Param("empEId") String empEId, @Param("roleId") Integer roleId, @Param("deptId") Integer deptId, @Param("desigId") Integer desigId, @Param("statusCd") String statusCd, @Param("empKppStatus") String empKppStatus, @Param("hodKppStatus") String hodKppStatus, @Param("gmKppStatus") String gmKppStatus, @Param("sortName") String sortName, @Param("pageSize") Integer pageSize, @Param("pageOffset") Integer pageOffset);

    @Query(value = SQLQueryConstants.EMPLOYEE_KPP_STATUS_COUNT_QUERY, nativeQuery = true)
    Integer getEmployeeKppStatusDetailCount(@Param("reportingEmployee") Integer reportingEmployee, @Param("gmEmpId") Integer gmEmpId, @Param("empId") Integer empId, @Param("empEId") String empEId, @Param("roleId") Integer roleId, @Param("deptId") Integer deptId, @Param("desigId") Integer desigId,  @Param("statusCd") String statusCd, @Param("empKppStatus") String empKppStatus, @Param("hodKppStatus") String hodKppStatus, @Param("gmKppStatus") String gmKppStatus);

    //When hod want to give rating to employee
    @Query(value = SQLQueryConstants.EMPLOYEE_KPP_QUERY, nativeQuery = true)
    List<Object[]> getEmployeeKeyPerfParameterDetail(@Param("empId") Integer empId, @Param("empEId") String empEId, @Param("statusCd") String statusCd);

    Optional<KeyPerfParamEntity> findByKppObjectiveNoEqualsIgnoreCaseAndStatusCd(String kppObjectiveNo,String statusCd);
}
