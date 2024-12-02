package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.constants.SQLQueryConstants;
import com.futurebizops.kpi.entity.ReportEmployeeKppMasterEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface ReportEmployeeKppMasterRepo extends JpaRepository<ReportEmployeeKppMasterEntity, Integer> {

    @Query(value = "select count(*) from report_employee_kpp_master rekm, employee emp, department dept, designation desig, roles rol where emp.emp_id = rekm.emp_id and emp.dept_id = dept.dept_id and emp.desig_id = desig.desig_id and emp.role_id = rol.role_id and date(rekm.ekpp_month) >= coalesce(date(:startDate), date(rekm.ekpp_month)) and date(rekm.ekpp_month) <= coalesce(date(:endDate), date(rekm.ekpp_month)) and emp.role_id = coalesce(:roleId, emp.role_id) and emp.dept_id = coalesce(:deptId, emp.dept_id) and emp.desig_id = coalesce(:desigId, emp.desig_id) and emp.reporting_emp_id = coalesce(:reportingEmpId, emp.reporting_emp_id) and emp.gm_emp_id = coalesce(:gmEmpId, emp.gm_emp_id)", nativeQuery = true)
    public Integer cumulativeEmpForHoDAndGMCount(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("roleId") Integer roleId, @Param("deptId") Integer deptId, @Param("desigId") Integer desigId, @Param("reportingEmpId") Integer reportingEmpId, @Param("gmEmpId") Integer gmEmpId);

    @Query(value = "select rekm.ekpp_month, rekm.emp_id , emp.emp_fname , emp.emp_mname, emp.emp_lname, emp.emp_eid, rekm.role_id, rol.role_name, rekm.dept_id, dept.dept_name, rekm.desig_id , desig.desig_name, rekm.total_emp_overall_achieve, rekm.total_hod_overall_achieve, rekm.total_gm_overall_achieve from report_employee_kpp_master rekm, employee emp, department dept, designation desig, roles rol where emp.emp_id = rekm.emp_id and emp.dept_id = dept.dept_id and emp.desig_id = desig.desig_id and emp.role_id = rol.role_id and date(rekm.ekpp_month) >= coalesce(date(:startDate), date(rekm.ekpp_month)) and date(rekm.ekpp_month) <= coalesce(date(:endDate), date(rekm.ekpp_month)) and emp.role_id = coalesce(:roleId, emp.role_id) and emp.dept_id = coalesce(:deptId, emp.dept_id) and emp.desig_id = coalesce(:desigId, emp.desig_id) and emp.reporting_emp_id = coalesce(:reportingEmpId, emp.reporting_emp_id) and emp.gm_emp_id = coalesce(:gmEmpId, emp.gm_emp_id) order by  :sortName asc limit :pageSize offset :pageOffset", nativeQuery = true)
    public List<Object[]> cumulativeEmpForHoDAndGM(@Param("startDate") String startDate,@Param("endDate") String endDate,@Param("roleId") Integer roleId,@Param("deptId") Integer deptId,@Param("desigId") Integer desigId,@Param("reportingEmpId") Integer reportingEmpId,@Param("gmEmpId") Integer gmEmpId,@Param("sortName") String sortName, @Param("pageSize") Integer pageSize, @Param("pageOffset") Integer pageOffset);

    //Check employee report fill for month or not
    @Query(value = "select rekm.emp_id, rekm.ekpp_month   from report_employee_kpp_master rekm where  rekm.emp_id = coalesce(:empId, rekm.emp_id)", nativeQuery = true)
    public List<Object[]> getEmpIdAndDates(@Param("empId") Integer empId);

    @Query(value = SQLQueryConstants.EMPLOYEE_CUMULATIVE_KPP_STATUS_REPORT_QUERY, nativeQuery = true)
    List<Object[]> getEmployeeKppStatusReportDetail(@Param("startDate") String startDate,@Param("endDate") String endDate, @Param("empId") Integer empId,  @Param("roleId") Integer roleId,  @Param("statusCd") String statusCd,  @Param("sortName") String sortName, @Param("pageSize") Integer pageSize, @Param("pageOffset") Integer pageOffset);

    @Query(value = SQLQueryConstants.EMPLOYEE_CUMULATIVE_KPP_STATUS_REPORT_COUNT_QUERY, nativeQuery = true)
    Integer getEmployeeKppStatusReportCount(@Param("startDate") String startDate,@Param("endDate") String endDate, @Param("empId") Integer empId,  @Param("roleId") Integer roleId,  @Param("statusCd") String statusCd);

    //add strength, needs and development details
    @Modifying
    @Query(value = "update report_employee_kpp_master set fin_year=:finYear,key_strength=:empKeyStrength, are_of_improvement=:empAreaOfImprovement, training_dev_needs=:empTrainginDevelopmentNeeds,lst_updt_user_id=:employeeId where emp_id =:empId", nativeQuery = true)
    public int updateOverallEmployeeKppReportRemark(@Param("finYear") String finYear, @Param("empKeyStrength") String empKeyStrength, @Param("empAreaOfImprovement") String empAreaOfImprovement,@Param("empTrainginDevelopmentNeeds") String empTrainginDevelopmentNeeds,@Param("employeeId") String employeeId, @Param("empId") Integer empId);

    List<ReportEmployeeKppMasterEntity> findByEmpIdAndStatusCd(Integer empId, String statusCd);
}
