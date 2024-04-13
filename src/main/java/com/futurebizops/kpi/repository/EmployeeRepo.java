package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.constants.DropDownQueryConstants;
import com.futurebizops.kpi.constants.SQLQueryConstants;
import com.futurebizops.kpi.entity.EmployeeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<EmployeeEntity, Integer> {


    public Optional<EmployeeEntity> findByEmpEIdEqualsIgnoreCase(String empEId);
    public EmployeeEntity findByEmpMobileNoAndStatusCd(String empMobileNo, String statusCd);


    @Query(value = SQLQueryConstants.EMPLOYEE_QUERY, nativeQuery = true)
    List<Object[]> getEmployeeDetail(@Param("empId") Integer empId,@Param("empEId") String empEId, @Param("roleId") Integer roleId, @Param("deptId") Integer deptId, @Param("desigId") Integer desigId,@Param("firstName") String firstName,@Param("middleName") String middleName,@Param("lastName") String lastName, @Param("empMobNo") String empMobNo, @Param("emailId") String emailId,  @Param("statusCd") String statusCd,@Param("empTypeId") Integer empTypeId,@Param("companyId") Integer companyId, @Param("sortName") String sortName, @Param("pageSize") Integer pageSize, @Param("pageOffset") Integer pageOffset);

    @Query(value = SQLQueryConstants.EMPLOYEE_COUNT_QUERY, nativeQuery = true)
    Integer getEmployeeCount(@Param("empId") Integer empId,@Param("empEId") String empEId,@Param("roleId") Integer roleId, @Param("deptId") Integer deptId, @Param("desigId") Integer desigId,@Param("firstName") String firstName,@Param("middleName") String middleName,@Param("lastName") String lastName, @Param("empMobNo") String empMobNo, @Param("emailId") String emailId,@Param("statusCd") String statusCd,@Param("empTypeId") Integer empTypeId,@Param("companyId") Integer companyId);

    @Query(value = SQLQueryConstants.EMPLOYEE_BY_ID_QUERY, nativeQuery = true)
    List<Object[]> getEmployeeById(@Param("empId") Integer empId);

    @Query(value = SQLQueryConstants.EMPLOYEE_SEARCH_BY_ID_QUERY, nativeQuery = true)
    List<Object[]> getEmployeeSearchById(@Param("empId") Integer empId);

   // public List<EmployeeEntity> findByRoleIdAndDeptIdAndDesigId(Integer roleId, Integer deptId, Integer desigId);

    public List<EmployeeEntity> findByRoleIdOrDeptIdOrDesigId(Integer roleId,Integer deptId,Integer desigId);

    //to assign kpp to employee

    @Query(value = DropDownQueryConstants.DD_REGION_FROM_EMPLOYEE_QUERY, nativeQuery = true)
    List<Object[]> getDDRegionFromCompany();

    @Query(value = DropDownQueryConstants.DD_SITE_FROM_EMPLOYEE_QUERY, nativeQuery = true)
    List<Object[]> getDDSiteFromCompany(@Param("regionId") Integer regionId );

    @Query(value = DropDownQueryConstants.DD_COMPANY_FROM_EMPLOYEE_QUERY, nativeQuery = true)
    List<Object[]> getDDCompanyFromCompany(@Param("regionId") Integer regionId ,@Param("siteId") Integer siteId );

    @Query(value = DropDownQueryConstants.DD_ROLES_FROM_EMPLOYEE_QUERY, nativeQuery = true)
    List<Object[]> getDDRolesFromCompany(@Param("regionId") Integer regionId ,@Param("siteId") Integer siteId, @Param("companyId") Integer companyId );

    @Query(value = DropDownQueryConstants.DD_DEPT_FROM_EMPLOYEE_QUERY, nativeQuery = true)
    List<Object[]> getDDDeptFromCompany(@Param("regionId") Integer regionId ,@Param("siteId") Integer siteId, @Param("companyId") Integer companyId, @Param("roleId") Integer roleId );

    @Query(value = DropDownQueryConstants.DD_DESIG_FROM_EMPLOYEE_QUERY, nativeQuery = true)
    List<Object[]> getDDDesigFromCompany(@Param("regionId") Integer regionId ,@Param("siteId") Integer siteId, @Param("companyId") Integer companyId, @Param("roleId") Integer roleId, @Param("deptId") Integer deptId );

    //Advance seach
    @Query(value = SQLQueryConstants.EMPLOYEE_ADVANCE_SEARCH_COUNT_QUERY, nativeQuery = true)
    Integer getEmployeeAdvanceSearchCount(@Param("roleId") Integer roleId, @Param("deptId") Integer deptId, @Param("desigId") Integer desigId,@Param("regionId") Integer regionId,@Param("siteId") Integer siteId,@Param("companyId") Integer companyId, @Param("empTypeId") Integer empTypeId);

    @Query(value = SQLQueryConstants.EMPLOYEE_ADVANCE_SEARCH_QUERY, nativeQuery = true)
    List<Object[]> getEmployeeAdvanceSearchDetails(@Param("roleId") Integer roleId, @Param("deptId") Integer deptId, @Param("desigId") Integer desigId,@Param("regionId") Integer regionId,@Param("siteId") Integer siteId,@Param("companyId") Integer companyId, @Param("empTypeId") Integer empTypeId,@Param("sortName") String sortName, @Param("pageSize") Integer pageSize, @Param("pageOffset") Integer pageOffset);

    @Query(value = DropDownQueryConstants.DD_ROLES_FROM_EXCEMPT_EMPLOYEE, nativeQuery = true)
    List<Object[]> getRolesExceptEmployee(@Param("roleId") Integer roleId ,@Param("roleName") String roleName);

    @Query(value = DropDownQueryConstants.DD_DEPARTMENT_FROM_EMPLOYEE, nativeQuery = true)
    List<Object[]> getDepartmentFromEmployee(@Param("roleId") Integer roleId ,@Param("deptId") Integer deptId);

    @Query(value = DropDownQueryConstants.DD_DESIGNATION_FROM_EMPLOYEE, nativeQuery = true)
    List<Object[]> getDesignationFromEmployee(@Param("roleId") Integer roleId ,@Param("deptId") Integer deptId,@Param("desigId") Integer desigId);

}
