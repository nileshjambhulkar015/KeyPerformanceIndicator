package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.entity.FreezeReportEmployeeKppMasterEntity;
import com.futurebizops.kpi.entity.ReportEmployeeKppMasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface FreezeReportEmployeeKppMasterRepo extends JpaRepository<FreezeReportEmployeeKppMasterEntity, Integer> {

    Optional<FreezeReportEmployeeKppMasterEntity> findByEmpIdAndFinYear(Integer empId, String finYear);

    //Check employee report fill for month or not
    @Query(value = "select rekm.emp_id, rekm.ekpp_month   from report_employee_kpp_master rekm where  rekm.emp_id = coalesce(:empId, rekm.emp_id)", nativeQuery = true)
    public List<Object[]> getEmpIdAndDates(@Param("empId") Integer empId);

    @Modifying
    @Query(value = "update freeze_report_employee_kpp_master set emp_id =:empId, ekpp_month =:ekppMonth, total_emp_achived_weight =:totalAchivedWeightage,total_emp_overall_achieve =:totalOverAllAchive,total_emp_overall_task_comp = :totalOverallTaskCompleted,avg_total_overall_rating=:totalOverallRatings,avg_total_overall_achivement_per=:totalOverallPercentage,emp_ekpp_applied_date=:eKppAppliedDate,emp_ekpp_status=:empKppStatus, emp_remark=:empRemark,emp_ekpp_evidence=:evidence,hod_ekpp_status='In-Progress', gm_ekpp_status='In-Progress' where emp_eid =:empEId and role_id =:roleId 	and dept_id =:deptId and desig_id =:desigId", nativeQuery = true)
    public int updateEmployeeKppMaster(@Param("empId") Integer empId, @Param("ekppMonth") Instant ekppMonth, @Param("totalAchivedWeightage") String totalAchivedWeightage, @Param("totalOverAllAchive") String totalOverAllAchive, @Param("totalOverallTaskCompleted") String totalOverallTaskCompleted,@Param("totalOverallRatings") String totalOverallRatings,@Param("totalOverallPercentage") String totalOverallPercentage, @Param("eKppAppliedDate") Instant eKppAppliedDate,@Param("empKppStatus") String empKppStatus, @Param("empRemark") String empRemark, @Param("evidence") String evidence, @Param("empEId") String empEId, @Param("roleId") Integer roleId, @Param("deptId") Integer deptId, @Param("desigId") Integer desigId);


}
