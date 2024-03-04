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
    @Query(value = SQLQueryConstants.EMPLOYEE_KPP_STATUS_INFO_QUERY, nativeQuery = true)
    List<Object[]> getEmployeeKPPStatus(@Param("empId") Integer empId);


    @Query(value = "select ekm.report_ekpp_id , ekm.ekpp_month, ekm.emp_id, emp.emp_fname, emp.emp_mname, emp.emp_lname, ekm.emp_eid, ekm.role_id, ro.role_name, ekm.dept_id, dept.dept_name, ekm.desig_id, desig.desig_name, ekm.total_emp_achived_weight, ekm.total_emp_overall_achieve, ekm.total_emp_overall_task_comp, ekm.emp_ekpp_applied_date, ekm.emp_ekpp_status, ekm.emp_remark, ekm.hod_emp_id, ekm.total_hod_achived_weight, ekm.total_hod_overall_achieve, ekm.total_hod_overall_task_comp, ekm.hod_ekpp_status, ekm.hod_approved_date, ekm.hod_remark, ekm.gm_emp_id, ekm.total_gm_achived_weight, ekm.total_gm_overall_achieve, ekm.total_gm_overall_task_comp, ekm.gm_ekpp_status, ekm.gm_approved_date, ekm.gm_remark, ekm.remark from report_employee_kpp_master ekm, employee emp, roles ro, department dept, designation desig where ekm.emp_id = emp.emp_id and ekm.role_id = ro.role_id and ekm.dept_id = dept.dept_id and ekm.desig_id = desig.desig_id and emp.emp_id= coalesce(:empId,emp.emp_id) and emp.emp_id= coalesce(:empId,emp.emp_id) and emp.reporting_emp_id = coalesce(:reportingEmpId,emp.reporting_emp_id) and emp.gm_emp_id = coalesce(:gmEmployeedId,emp.gm_emp_id) and date(ekm.ekpp_month) >= coalesce(date(:startDate),date(ekm.ekpp_month)) and date(ekm.ekpp_month) <= coalesce(date(:endDate),date(ekm.ekpp_month))  order by :sortName asc limit :pageSize offset :pageOffset", nativeQuery = true)
    public List<Object[]> cumulativeEmpForHoDAndGM(@Param("empId") Integer empId, @Param("reportingEmpId") Integer reportingEmpId,@Param("gmEmployeedId") Integer gmEmployeedId,@Param("startDate") String startDate,@Param("endDate") String endDate,@Param("sortName") String sortName, @Param("pageSize") Integer pageSize, @Param("pageOffset") Integer pageOffset);

}

