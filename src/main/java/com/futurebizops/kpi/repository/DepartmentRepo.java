package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.constants.SQLQueryConstants;
import com.futurebizops.kpi.entity.DepartmentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepo extends JpaRepository<DepartmentEntity, Integer> {

    public Page<DepartmentEntity> findByDeptIdAndStatusCd(Integer deptId, String statusCd, Pageable pageable);

    public Page<DepartmentEntity> findByDeptNameStartingWithIgnoreCaseAndStatusCd(String deptName, String statusCd, Pageable pageable);

    public Page<DepartmentEntity> findByStatusCd(String status, Pageable pageable);

    @Query(value = "" +
            "select dept.dept_id, dept.dept_name, dept.remark,dept.remark  from department dept, designation desig where dept.dept_id =desig.dept_id and dept.status_cd='A' and dept.dept_id = coalesce(:deptId,dept.dept_id) order by dept.dept_name", nativeQuery = true)
    public List<Object[]> findAllDepartments();

    @Query(value = "select * from department dept where dept.status_cd ='A'", nativeQuery = true)
    public List<DepartmentEntity> findAllDepartmentDetailsForEmployee();

    public Optional<DepartmentEntity> findByDeptNameEqualsIgnoreCaseAndRoleId(String deptName, Integer roleId);

    @Query(value = SQLQueryConstants.DEPARTMENT_QUERY, nativeQuery = true)
    List<Object[]> getDepartmentDetail(@Param("roleId") Integer roleId, @Param("deptId") Integer deptId, @Param("deptName") String deptName, @Param("statusCd") String statusCd, @Param("sortName") String sortName, @Param("pageSize") Integer pageSize, @Param("pageOffset") Integer pageOffset);

    @Query(value = SQLQueryConstants.DEPARTMENT_COUNT_UERY, nativeQuery = true)
    Integer getDepartmentCount(@Param("roleId") Integer roleId, @Param("deptId") Integer deptId, @Param("deptName") String deptName, @Param("statusCd") String statusCd);

    @Query(value = SQLQueryConstants.DEPARTMENT_BY_ID_QUERY, nativeQuery = true)
    List<Object[]> getDepartmentByIdDetail(@Param("deptId") Integer deptId);

    @Query(value = SQLQueryConstants.DEPT_IN_DESIGNATION_QUERY, nativeQuery = true)
    List<Object[]> getDeptInDesigById(@Param("deptId") Integer deptId);



    @Query(value = SQLQueryConstants.DEPT_BY_ROLE_ID_FROM_DESIG, nativeQuery = true)
    List<Object[]> getAllDepartmentFromDesigByRoleId(@Param("roleId") Integer roleId);

    @Query(value = SQLQueryConstants.DEPT_BY_ROLE_ID, nativeQuery = true)
    List<Object[]> getAllDepartByRoleId(@Param("roleId") Integer roleId);

    @Query(value = SQLQueryConstants.DEPT_SUGGEST, nativeQuery = true)
    List<Object[]> getAllDepartmentByDeptName(@Param("deptName") String deptName);

    public Optional<DepartmentEntity> findByRoleIdAndDeptNameEqualsIgnoreCase(Integer roleId,String deptName);


}
