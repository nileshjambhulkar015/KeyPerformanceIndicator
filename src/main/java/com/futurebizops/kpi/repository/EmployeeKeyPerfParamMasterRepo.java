package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.entity.EmployeeKeyPerfParamMasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface EmployeeKeyPerfParamMasterRepo extends JpaRepository<EmployeeKeyPerfParamMasterEntity, Integer> {

    @Modifying
    @Query(value = "update employee_key_perf_parameter_master set emp_id =:empId, ekpp_month =:ekppMonth, total_emp_achived_weight =:totalAchivedWeightage,total_emp_overall_achieve =:totalOverAllAchive,total_emp_overall_task_comp = :totalOverallTaskCompleted,emp_ekpp_status=:empKppStatus, emp_remark = :remark,emp_ekpp_evidence = :evidence where emp_eid =:empEId and role_id =:roleId 	and dept_id =:deptId and desig_id =:desigId", nativeQuery = true)
    public int updateEmployeeKppMaster(@Param("empId") Integer empId, @Param("ekppMonth") Instant ekppMonth, @Param("totalAchivedWeightage") String totalAchivedWeightage, @Param("totalOverAllAchive") String totalOverAllAchive, @Param("totalOverallTaskCompleted") String totalOverallTaskCompleted, @Param("empKppStatus") String empKppStatus, @Param("remark") String remark, @Param("evidence") String evidence, @Param("empEId") String empEId, @Param("roleId") Integer roleId, @Param("deptId") Integer deptId, @Param("desigId") Integer desigId);

//(totalAchivedWeightage,totalOverAllAchive,totalOverallTaskCompleted,empKppStatus,remark,evidence,kppUpdateRequests.get(0).getEmpEId(),kppUpdateRequests.get(0).getRoleId(),kppUpdateRequests.get(0).getDeptId(),kppUpdateRequests.get(0).getDesigId());

}
