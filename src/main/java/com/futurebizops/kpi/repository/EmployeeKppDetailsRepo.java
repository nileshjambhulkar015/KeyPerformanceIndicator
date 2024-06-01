package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.constants.SQLQueryConstants;
import com.futurebizops.kpi.entity.EmployeeKppDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeKppDetailsRepo extends JpaRepository<EmployeeKppDetailsEntity, Integer> {
    @Modifying
    @Query(value = "update employee_kpp_details set emp_id =:empId, ekpp_month =:ekppMonth, ekpp_emp_achived_weight =:ekppAchivedWeight,ekpp_emp_overall_achieve =:ekppOverallAchieve,ekpp_emp_overall_task_comp = :ekppOverallTaskComp where kpp_id = :kppId and emp_eid =:empEId and role_id =:roleId 	and dept_id =:deptId and desig_id =:desigId", nativeQuery = true)
    public int updateEmployeeKppDetails(@Param("empId") Integer empId,@Param("ekppMonth") Instant ekppMonth,@Param("ekppAchivedWeight") String ekppAchivedWeight,@Param("ekppOverallAchieve") String ekppOverallAchieve, @Param("ekppOverallTaskComp") String ekppOverallTaskComp, @Param("kppId") Integer kppId,@Param("empEId") String empEId,@Param("roleId") Integer roleId, @Param("deptId") Integer deptId,@Param("desigId") Integer desigId);

    @Modifying
    @Query(value = "update employee_kpp_details set ekpp_hod_achived_weight =:ekppAchivedWeight,ekpp_hod_overall_achieve =:ekppOverallAchieve,ekpp_hod_overall_task_comp = :ekppOverallTaskComp where kpp_id = :kppId and emp_id =:empId", nativeQuery = true)
    public int updateEmpApproveOrRejectHod(@Param("ekppAchivedWeight") String ekppAchivedWeight,@Param("ekppOverallAchieve") String ekppOverallAchieve, @Param("ekppOverallTaskComp") String ekppOverallTaskComp, @Param("kppId") Integer kppId,@Param("empId") Integer empId);

    @Modifying
    @Query(value = "update employee_kpp_details set ekpp_gm_achived_weight =:ekppAchivedWeight,ekpp_gm_overall_achieve =:ekppOverallAchieve,ekpp_gm_overall_task_comp = :ekppOverallTaskComp where kpp_id = :kppId and emp_id =:empId", nativeQuery = true)
    public int updateGMApproveOrRejectHod(@Param("ekppAchivedWeight") String ekppAchivedWeight,@Param("ekppOverallAchieve") String ekppOverallAchieve, @Param("ekppOverallTaskComp") String ekppOverallTaskComp, @Param("kppId") Integer kppId,@Param("empId") Integer empId);

    List<EmployeeKppDetailsEntity> findByEmpIdAndStatusCd(Integer empId, String statusCd);

    @Modifying
    @Query(value = "update employee_kpp_details set ekpp_month=null,ekpp_emp_achived_weight='0',ekpp_emp_overall_achieve='0',ekpp_emp_overall_task_comp='0',ekpp_hod_achived_weight='0',ekpp_hod_overall_achieve ='0',ekpp_hod_overall_task_comp='0',ekpp_gm_achived_weight='0',ekpp_gm_overall_achieve ='0',ekpp_gm_overall_task_comp ='0'  where emp_id =:empId and status_cd=:statusCd", nativeQuery = true)
    public int resetEmployeeKpp(@Param("empId") Integer empId, @Param("statusCd") String statusCd);

    @Query(value = SQLQueryConstants.VIEW_EMPLOYEE_KPP_COUNT, nativeQuery = true)
    Integer viewEmployeeKppCount(@Param("empId") Integer empId,@Param("roleId") Integer roleId,@Param("deptId") Integer deptId,@Param("desigId") Integer desigId);

   @Query(value = SQLQueryConstants.VIEW_EMPLOYEE_KPP, nativeQuery = true)
    //@Query(value = SQLQueryConstants.VIEW_EMPLOYEE_KPP_DETAILS, nativeQuery = true)

    List<Object[]> viewEmployeeKpp(@Param("empId") Integer empId,@Param("roleId") Integer roleId,@Param("deptId") Integer deptId,@Param("desigId") Integer desigId);

    public void deleteByEmpIdAndKppId(Integer empId, Integer kppId);

    @Query(value = SQLQueryConstants.ASSIGN_EMPLOYEE_KPP_COUNT_QUERY, nativeQuery = true)
    Integer assignEmployeeKppCount(@Param("empId") Integer empId);

    @Query(value = SQLQueryConstants.ASSIGN_EMPLOYEE_KPP, nativeQuery = true)
    List<Object[]> assignEmployeeKpp(@Param("empId") Integer empId,@Param("sortName") String sortName, @Param("pageSize") Integer pageSize, @Param("pageOffset") Integer pageOffset);

}


