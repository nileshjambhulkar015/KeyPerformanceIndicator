package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.entity.FreezeReportEmployeeKppDetailsEntity;
import com.futurebizops.kpi.entity.ReportEmployeeKppDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface FreezeReportEmployeeKppDetailsRepo extends JpaRepository<FreezeReportEmployeeKppDetailsEntity, Integer> {

    @Modifying
    @Query(value = "update freeze_report_employee_kpp_details set emp_id =:empId, ekpp_month =:ekppMonth, ekpp_emp_achived_weight =:ekppAchivedWeight,ekpp_emp_overall_achieve =:ekppOverallAchieve,ekpp_emp_overall_task_comp = :ekppOverallTaskComp, avg_overall_rating=:overallRatings,avg_overall_achivement_per=:overallPercentage where kpp_id = :kppId and emp_eid =:empEId and role_id =:roleId 	and dept_id =:deptId and desig_id =:desigId", nativeQuery = true)
    public int updateEmployeeKppDetails(@Param("empId") Integer empId, @Param("ekppMonth") Instant ekppMonth, @Param("ekppAchivedWeight") String ekppAchivedWeight, @Param("ekppOverallAchieve") String ekppOverallAchieve, @Param("ekppOverallTaskComp") String ekppOverallTaskComp, @Param("overallRatings") String overallRatings, @Param("overallPercentage") String overallPercentage, @Param("kppId") Integer kppId, @Param("empEId") String empEId, @Param("roleId") Integer roleId, @Param("deptId") Integer deptId, @Param("desigId") Integer desigId);

    @Modifying
    @Query(value = "update freeze_report_employee_kpp_details set emp_kpp_feedback =:empKppFeedback where emp_id =:empId and role_id =:roleId and dept_id =:deptId and desig_id =:desigId and kpp_id = :kppId and fin_year = :finYear", nativeQuery = true)
    public int updateHODFeedbackKppDetails(@Param("empKppFeedback") String empKppFeedback,@Param("empId") Integer empId,  @Param("roleId") Integer roleId, @Param("deptId") Integer deptId, @Param("desigId") Integer desigId,@Param("kppId") Integer kppId,@Param("finYear") String finYear);

}
