package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.entity.EmployeeKppMasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface EmployeeKppMasterRepo extends JpaRepository<EmployeeKppMasterEntity, Integer> {

    @Modifying
    @Query(value = "update employee_kpp_master set emp_id =:empId, ekpp_month =:ekppMonth, total_emp_achived_weight =:totalAchivedWeightage,total_emp_overall_achieve =:totalOverAllAchive,total_emp_overall_task_comp = :totalOverallTaskCompleted,emp_ekpp_applied_date=:eKppAppliedDate,emp_ekpp_status=:empKppStatus, emp_remark = :remark,emp_ekpp_evidence = :evidence where emp_eid =:empEId and role_id =:roleId 	and dept_id =:deptId and desig_id =:desigId", nativeQuery = true)
    public int updateEmployeeKppMaster(@Param("empId") Integer empId, @Param("ekppMonth") Instant ekppMonth, @Param("totalAchivedWeightage") String totalAchivedWeightage, @Param("totalOverAllAchive") String totalOverAllAchive, @Param("totalOverallTaskCompleted") String totalOverallTaskCompleted, @Param("eKppAppliedDate") Instant eKppAppliedDate,@Param("empKppStatus") String empKppStatus, @Param("remark") String remark, @Param("evidence") String evidence, @Param("empEId") String empEId, @Param("roleId") Integer roleId, @Param("deptId") Integer deptId, @Param("desigId") Integer desigId);

    @Modifying
    @Query(value = "update employee_kpp_master set emp_ekpp_status=:empKppStatus,total_hod_achived_weight =:totalAchivedWeightage,total_hod_overall_achieve =:totalOverAllAchive,total_hod_overall_task_comp = :totalOverallTaskCompleted,hod_approved_date=:eKppAppliedDate,hod_ekpp_status=:empKppHodStatus, hod_remark = :remark where emp_id =:empId", nativeQuery = true)
    public int updateEmpKppApproveOrRejectByHod(@Param("empKppStatus") String empKppStatus,@Param("totalAchivedWeightage") String totalAchivedWeightage, @Param("totalOverAllAchive") String totalOverAllAchive, @Param("totalOverallTaskCompleted") String totalOverallTaskCompleted, @Param("eKppAppliedDate") Instant eKppAppliedDate,@Param("empKppHodStatus") String empKppHodStatus, @Param("remark") String remark, @Param("empId") Integer empId);


    @Modifying
    @Query(value = "update employee_kpp_master set emp_ekpp_status=:empKppStatus,total_gm_achived_weight =:totalAchivedWeightage,total_gm_overall_achieve =:totalOverAllAchive,total_gm_overall_task_comp = :totalOverallTaskCompleted,gm_approved_date=:eKppAppliedDate,gm_ekpp_status=:empKppHodStatus, gm_remark = :remark where emp_id =:empId", nativeQuery = true)
    public int updateGMKppApproveOrRejectByHod(@Param("empKppStatus") String empKppStatus,@Param("totalAchivedWeightage") String totalAchivedWeightage, @Param("totalOverAllAchive") String totalOverAllAchive, @Param("totalOverallTaskCompleted") String totalOverallTaskCompleted, @Param("eKppAppliedDate") Instant eKppAppliedDate,@Param("empKppHodStatus") String empKppHodStatus, @Param("remark") String remark, @Param("empId") Integer empId);

    Optional<EmployeeKppMasterEntity> findByEmpIdAndStatusCd(Integer empId, String statusCd);
}
