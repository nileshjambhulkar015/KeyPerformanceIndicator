package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.constants.SQLQueryConstants;
import com.futurebizops.kpi.entity.OverallEmployeeKppFeedbackMasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface OverallEmployeeKppFeedbackMasterRepo extends JpaRepository<OverallEmployeeKppFeedbackMasterEntity, Integer> {

    Boolean findByEmpIdAndFinYear(Integer empId, String finYear);

    //Check employee report fill for month or not
    @Query(value = "select rekm.emp_id, rekm.ekpp_month   from report_employee_kpp_master rekm where  rekm.emp_id = coalesce(:empId, rekm.emp_id)", nativeQuery = true)
    public List<Object[]> getEmpIdAndDates(@Param("empId") Integer empId);

    @Query(value = "select * from freeze_report_employee_kpp_master where  emp_id = coalesce(:empId, emp_id) and fin_year=coalesce(:finYear, fin_year)", nativeQuery = true)
    public List<Object[]> checkKppReportAdded(@Param("empId") Integer empId,@Param("finYear") String finYear);

    @Modifying
    @Query(value = "update freeze_report_employee_kpp_master set emp_id =:empId, ekpp_month =:ekppMonth, total_emp_achived_weight =:totalAchivedWeightage,total_emp_overall_achieve =:totalOverAllAchive,total_emp_overall_task_comp = :totalOverallTaskCompleted,avg_total_overall_rating=:totalOverallRatings,avg_total_overall_achivement_per=:totalOverallPercentage,emp_ekpp_applied_date=:eKppAppliedDate,emp_ekpp_status=:empKppStatus, emp_remark=:empRemark,emp_ekpp_evidence=:evidence,hod_ekpp_status='In-Progress', gm_ekpp_status='In-Progress' where emp_eid =:empEId and role_id =:roleId 	and dept_id =:deptId and desig_id =:desigId", nativeQuery = true)
    public int updateEmployeeKppMaster(@Param("empId") Integer empId, @Param("ekppMonth") Instant ekppMonth, @Param("totalAchivedWeightage") String totalAchivedWeightage, @Param("totalOverAllAchive") String totalOverAllAchive, @Param("totalOverallTaskCompleted") String totalOverallTaskCompleted,@Param("totalOverallRatings") String totalOverallRatings,@Param("totalOverallPercentage") String totalOverallPercentage, @Param("eKppAppliedDate") Instant eKppAppliedDate,@Param("empKppStatus") String empKppStatus, @Param("empRemark") String empRemark, @Param("evidence") String evidence, @Param("empEId") String empEId, @Param("roleId") Integer roleId, @Param("deptId") Integer deptId, @Param("desigId") Integer desigId);

    @Query(value = SQLQueryConstants.FREEZE_YEARLY_EMPLOYEE_KPP_STATUS_INFO_QUERY, nativeQuery = true)
    List<Object[]> getEmployeeKppDataYearlyFromFreezeTable(@Param("empId") Integer empId,@Param("finYear") String finYear);

    @Query(value = "select distinct fin_year from freeze_report_employee_kpp_master", nativeQuery = true)
    public List<Object[]> ddAllFinancialYear();

    @Query(value = "select distinct fin_year from freeze_report_employee_kpp_master where emp_ekpp_status='Completed' and gm_ekpp_status='Completed' and status_cd='A'", nativeQuery = true)
    public List<Object[]> ddCompletedAllFinancialYear();

    @Query(value = SQLQueryConstants.EMPLOYEE_DETAILS_FOR_KPP, nativeQuery = true)
    List<Object[]> getEmployeeDetailForKPP(@Param("empId") Integer empId,@Param("roleId") Integer roleId,@Param("finYear") String finYear,@Param("reportingEmpId") Integer reportingEmpId,@Param("gmEmpId") Integer gmEmpId,@Param("empKppStatus") String empKppStatus,@Param("hodKppStatus") String hodKppStatus,@Param("gmKppStatus") String gmKppStatus, @Param("sortName") String sortName, @Param("pageSize") Integer pageSize, @Param("pageOffset") Integer pageOffset);

    @Query(value = SQLQueryConstants.EMPLOYEE_DETAILS_FOR_KPP_COUNT, nativeQuery = true)
    Integer getEmployeeDetailForKPPCount(@Param("empId") Integer empId,@Param("roleId") Integer roleId,@Param("finYear") String finYear,@Param("reportingEmpId") Integer reportingEmpId,@Param("gmEmpId") Integer gmEmpId,@Param("empKppStatus") String empKppStatus,@Param("hodKppStatus") String hodKppStatus,@Param("gmKppStatus") String gmKppStatus);

    @Modifying
    @Query(value = "update freeze_report_employee_kpp_master set key_strength =:empKeyStrength,are_of_improvement=:empAreaOfImprovement,training_dev_needs=:empTrainginDevelopmentNeeds,gm_remark=:gmRemark,gm_ekpp_status=:gmKppStatus where emp_id =:empId and fin_year = :finYear", nativeQuery = true)
    public int updateGMKeyStrengthOfEmployee(@Param("empId") Integer empId,@Param("finYear") String finYear,@Param("empKeyStrength") String empKeyStrength,@Param("empAreaOfImprovement") String empAreaOfImprovement,@Param("empTrainginDevelopmentNeeds") String empTrainginDevelopmentNeeds,@Param("gmRemark") String gmRemark,@Param("gmKppStatus") String gmKppStatus);

    @Modifying
    @Query(value = "update freeze_report_employee_kpp_master set key_strength =:empKeyStrength,are_of_improvement=:empAreaOfImprovement,training_dev_needs=:empTrainginDevelopmentNeeds, hod_remark=:hodRemark,hod_ekpp_status=:hodKppStatus where emp_id =:empId and fin_year = :finYear", nativeQuery = true)
    public int updateHODKeyStrengthForEmployee(@Param("empId") Integer empId,@Param("finYear") String finYear,@Param("empKeyStrength") String empKeyStrength,@Param("empAreaOfImprovement") String empAreaOfImprovement,@Param("empTrainginDevelopmentNeeds") String empTrainginDevelopmentNeeds,@Param("hodRemark") String hodRemark,@Param("hodKppStatus") String hodKppStatus);


    @Modifying
    @Query(value = "update freeze_report_employee_kpp_master set emp_ekpp_status =:empKppStatus,hod_ekpp_status=:hodKppStatus,gm_ekpp_status=:gmKppStatus,lst_updt_user_id=:employeeId where emp_id =:empId and fin_year = :finYear", nativeQuery = true)
    public int finishByGMKppFeedback(@Param("empId") Integer empId,@Param("finYear") String finYear,@Param("empKppStatus") String empKppStatus,@Param("hodKppStatus") String hodKppStatus,@Param("gmKppStatus") String gmKppStatus, @Param("employeeId") String employeeId);

}
