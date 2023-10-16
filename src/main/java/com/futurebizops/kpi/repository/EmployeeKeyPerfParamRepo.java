package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.entity.EmployeeKeyPerfParamEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface EmployeeKeyPerfParamRepo extends JpaRepository<EmployeeKeyPerfParamEntity, Integer> {

    @Modifying
    @Query(value = "update employee_key_perf_parameter set hod_emp_id=:hodEmpId,hod_kpp_status=:hodKppStatus, hod_rating=:hodRating,hod_remark=:hodRemark,hod_approved_date=:hodApprovedDate,lst_updt_user_id=:lastUpdatedUserId where ekpp_id=:ekppId and emp_id=:empId and dept_id=:deptId and desig_id=:desigId", nativeQuery = true)
    public void updateHODEmplyeeKppStatus(@Param("hodEmpId") String hodEmpId, @Param("hodKppStatus")String hodKppStatus, @Param("hodRating")String hodRating, @Param("hodRemark")String hodRemark,  @Param("hodApprovedDate")Instant hodApprovedDate, @Param("lastUpdatedUserId")String lastUpdatedUserId,@Param("ekppId") Integer ekppId,  @Param("empId")Integer empId,@Param("deptId")Integer deptId,@Param("desigId") Integer desigId);

    @Modifying
    @Query(value = "update employee_key_perf_parameter set gm_emp_id=:gmEmpId,gm_kpp_status=:gmKppStatus, gm_rating=:gmRating,gm_remark=:gmRemark,gm_approved_date=:gmApprovedDate,lst_updt_user_id=:lastUpdatedUserId where ekpp_id=:ekppId and emp_id=:empId and dept_id=:deptId and desig_id=:desigId", nativeQuery = true)
    public void updateGMEmplyeeKppStatus(@Param("gmEmpId") String gmEmpId, @Param("gmKppStatus")String gmKppStatus, @Param("gmRating")String gmRating, @Param("gmRemark")String gmRemark,  @Param("gmApprovedDate")Instant gmApprovedDate, @Param("lastUpdatedUserId")String lastUpdatedUserId,@Param("ekppId") Integer ekppId, @Param("empId")Integer empId,@Param("deptId")Integer deptId,@Param("desigId") Integer desigId);

}