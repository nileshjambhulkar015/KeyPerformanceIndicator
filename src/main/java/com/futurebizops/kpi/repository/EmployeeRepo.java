package com.futurebizops.kpi.repository;

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


    public Optional<EmployeeEntity> findByEmpMobileNoOrEmailIdEqualsIgnoreCase(String empMobileNo, String emailId);
    public EmployeeEntity findByEmpMobileNoAndStatusCd(String empMobileNo, String statusCd);


    @Query(value = SQLQueryConstants.EMPLOYEE_QUERY, nativeQuery = true)
    List<Object[]> getEmployeeDetail(@Param("empId") Integer empId,@Param("roleId") Integer roleId, @Param("deptId") Integer deptId, @Param("desigId") Integer desigId,@Param("firstName") String firstName,@Param("middleName") String middleName,@Param("lastName") String lastName, @Param("empMobNo") String empMobNo, @Param("emailId") String emailId,  @Param("statusCd") String statusCd, @Param("sortName") String sortName, @Param("pageSize") Integer pageSize, @Param("pageOffset") Integer pageOffset);

    @Query(value = SQLQueryConstants.EMPLOYEE_COUNT_QUERY, nativeQuery = true)
    Integer getEmployeeCount(@Param("empId") Integer empId,@Param("roleId") Integer roleId, @Param("deptId") Integer deptId, @Param("desigId") Integer desigId,@Param("firstName") String firstName,@Param("middleName") String middleName,@Param("lastName") String lastName, @Param("empMobNo") String empMobNo, @Param("emailId") String emailId,@Param("statusCd") String statusCd);

    @Query(value = SQLQueryConstants.EMPLOYEE_BY_ID_QUERY, nativeQuery = true)
    List<Object[]> getEmployeeById(@Param("empId") Integer empId);

    @Query(value = SQLQueryConstants.EMPLOYEE_SEARCH_BY_ID_QUERY, nativeQuery = true)
    List<Object[]> getEmployeeSearchById(@Param("empId") Integer empId);

   // public List<EmployeeEntity> findByRoleIdAndDeptIdAndDesigId(Integer roleId, Integer deptId, Integer desigId);

    public List<EmployeeEntity> findByRoleIdOrDeptIdOrDesigId(Integer roleId,Integer deptId,Integer desigId);
}
