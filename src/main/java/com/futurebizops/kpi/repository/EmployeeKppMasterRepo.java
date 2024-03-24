package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.constants.SQLQueryConstants;
import com.futurebizops.kpi.entity.EmployeeKppMasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeKppMasterRepo extends JpaRepository<EmployeeKppMasterEntity, Integer> {

    @Modifying
    @Query(value = "update employee_kpp_master set emp_id =:empId, ekpp_month =:ekppMonth, total_emp_achived_weight =:totalAchivedWeightage,total_emp_overall_achieve =:totalOverAllAchive,total_emp_overall_task_comp = :totalOverallTaskCompleted,emp_ekpp_applied_date=:eKppAppliedDate,emp_ekpp_status=:empKppStatus, emp_remark = :empRemark,emp_ekpp_evidence = :evidence,hod_ekpp_status='In-Progress', gm_ekpp_status='In-Progress' where emp_eid =:empEId and role_id =:roleId 	and dept_id =:deptId and desig_id =:desigId", nativeQuery = true)
    public int updateEmployeeKppMaster(@Param("empId") Integer empId, @Param("ekppMonth") Instant ekppMonth, @Param("totalAchivedWeightage") String totalAchivedWeightage, @Param("totalOverAllAchive") String totalOverAllAchive, @Param("totalOverallTaskCompleted") String totalOverallTaskCompleted, @Param("eKppAppliedDate") Instant eKppAppliedDate,@Param("empKppStatus") String empKppStatus, @Param("empRemark") String empRemark, @Param("evidence") String evidence, @Param("empEId") String empEId, @Param("roleId") Integer roleId, @Param("deptId") Integer deptId, @Param("desigId") Integer desigId);

    @Modifying
    @Query(value = "update employee_kpp_master set emp_ekpp_status=:empKppStatus,total_hod_achived_weight =:totalAchivedWeightage,total_hod_overall_achieve =:totalOverAllAchive,total_hod_overall_task_comp = :totalOverallTaskCompleted,hod_approved_date=:eKppAppliedDate,hod_ekpp_status=:empKppHodStatus, hod_remark = :remark ,gm_ekpp_status='In-Progress' where emp_id =:empId", nativeQuery = true)
    public int updateEmpKppApproveOrRejectByHod(@Param("empKppStatus") String empKppStatus,@Param("totalAchivedWeightage") String totalAchivedWeightage, @Param("totalOverAllAchive") String totalOverAllAchive, @Param("totalOverallTaskCompleted") String totalOverallTaskCompleted, @Param("eKppAppliedDate") Instant eKppAppliedDate,@Param("empKppHodStatus") String empKppHodStatus, @Param("remark") String remark, @Param("empId") Integer empId);


    @Modifying
    @Query(value = "update employee_kpp_master set emp_ekpp_status=:empKppStatus,total_gm_achived_weight =:totalAchivedWeightage,total_gm_overall_achieve =:totalOverAllAchive,total_gm_overall_task_comp = :totalOverallTaskCompleted,gm_approved_date=:eKppAppliedDate,gm_ekpp_status=:empKppHodStatus, gm_remark = :remark where emp_id =:empId", nativeQuery = true)
    public int updateGMKppApproveOrRejectByHod(@Param("empKppStatus") String empKppStatus,@Param("totalAchivedWeightage") String totalAchivedWeightage, @Param("totalOverAllAchive") String totalOverAllAchive, @Param("totalOverallTaskCompleted") String totalOverallTaskCompleted, @Param("eKppAppliedDate") Instant eKppAppliedDate,@Param("empKppHodStatus") String empKppHodStatus, @Param("remark") String remark, @Param("empId") Integer empId);

    Optional<EmployeeKppMasterEntity> findByEmpIdAndStatusCd(Integer empId, String statusCd);

    @Modifying
    @Query(value = "update employee_kpp_master set ekpp_month=null,total_emp_achived_weight='0',total_emp_overall_achieve='0',total_emp_overall_task_comp='0',emp_ekpp_applied_date=null, emp_ekpp_status='Pending',emp_remark=null, emp_ekpp_evidence=null,total_hod_achived_weight='0', total_hod_overall_achieve ='0', total_hod_overall_task_comp ='0',hod_ekpp_status='Pending',hod_approved_date=null,hod_remark=null,total_gm_achived_weight ='0',total_gm_overall_achieve='0',total_gm_overall_task_comp='0',gm_ekpp_status='Pending', gm_approved_date=null,gm_remark=null,remark=null where emp_id =:empId and status_cd=:statusCd", nativeQuery = true)
    public int resetEmployeeKppByGM(@Param("empId") Integer empId, @Param("statusCd") String statusCd);

    //Employee KPP status by employee, hod and gm level
    @Query(value = SQLQueryConstants.IN_PROGRESS_EMPLOYEE_KPP_STATUS_INFO_QUERY, nativeQuery = true)
    List<Object[]> getInProgressEmployeeKPPStatus(@Param("empId") Integer empId);

    @Query(value = SQLQueryConstants.COMPLETED_EMPLOYEE_KPP_STATUS_INFO_QUERY, nativeQuery = true)
    List<Object[]> getCompletedEmployeeKPPStatus(@Param("empId") Integer empId, @Param("ekkStatusMonth") String ekkStatusMonth);




  //  @Query(value = "select rekm.ekpp_month, rekm.emp_id , emp.emp_fname , emp.emp_mname, emp.emp_lname, emp.emp_eid,rekm.role_id, rol.role_name, rekm.dept_id, dept.dept_name, rekm.desig_id , desig.desig_name,  rekm.total_emp_achived_weight, rekm.total_hod_achived_weight, rekm.total_gm_achived_weight from report_employee_kpp_master rekm, employee emp, department dept, designation desig, roles rol where emp.emp_id = rekm.emp_id and rekm.dept_id = dept.dept_id and rekm.desig_id = desig.desig_id and rekm.role_id = rol.role_id and date(rekm.ekpp_month) >= coalesce(date(:startDate), date(rekm.ekpp_month)) and date(rekm.ekpp_month) <= coalesce(date(:endDate), date(rekm.ekpp_month)) and  rekm.role_id = coalesce(:roleId,rekm.role_id) and  rekm.dept_id = coalesce(:deptId,rekm.dept_id) and  rekm.desig_id = coalesce(:desigId,rekm.desig_id) and emp.reporting_emp_id = coalesce(:reportingEmpId,emp.reporting_emp_id) and emp.gm_emp_id = coalesce(:gmEmpId,emp.gm_emp_id)", nativeQuery = true)
  @Query(value = "select rekm.ekpp_month, rekm.emp_id , emp.emp_fname , emp.emp_mname, emp.emp_lname, emp.emp_eid, rekm.role_id, rol.role_name, rekm.dept_id, dept.dept_name, rekm.desig_id , desig.desig_name, rekm.total_emp_overall_achieve, rekm.total_hod_overall_achieve, rekm.total_gm_overall_achieve from report_employee_kpp_master rekm, employee emp, department dept, designation desig, roles rol where emp.emp_id = rekm.emp_id and emp.dept_id = dept.dept_id and emp.desig_id = desig.desig_id and emp.role_id = rol.role_id and date(rekm.ekpp_month) >= coalesce(date(:startDate), date(rekm.ekpp_month)) and date(rekm.ekpp_month) <= coalesce(date(:endDate), date(rekm.ekpp_month)) and emp.role_id = coalesce(:roleId, emp.role_id) and emp.dept_id = coalesce(:deptId, emp.dept_id) and emp.desig_id = coalesce(:desigId, emp.desig_id) and emp.reporting_emp_id = coalesce(:reportingEmpId, emp.reporting_emp_id) and emp.gm_emp_id = coalesce(:gmEmpId, emp.gm_emp_id)", nativeQuery = true)
    public List<Object[]> cumulativeEmpForHoDAndGM(@Param("startDate") String startDate,@Param("endDate") String endDate,@Param("roleId") Integer roleId,@Param("deptId") Integer deptId,@Param("desigId") Integer desigId,@Param("reportingEmpId") Integer reportingEmpId,@Param("gmEmpId") Integer gmEmpId);

}

