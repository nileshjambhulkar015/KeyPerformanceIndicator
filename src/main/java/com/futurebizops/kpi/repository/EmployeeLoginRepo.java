package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.entity.EmployeeLoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeLoginRepo extends JpaRepository<EmployeeLoginEntity, Integer> {

    public Optional<EmployeeLoginEntity> findByRoleIdAndEmpMobileNoAndEmpPasswordAndStatusCd(Integer roleId, String empMobileNo, String empPassword, String statusCd);

    @Query(value = "select emp.emp_id , emp.emp_eid , emp.role_id, role.role_name, emp.dept_id , dept.dept_name, emp.desig_id, desig.desig_name , emp.emp_fname , emp.emp_mname , emp.emp_lname,emp.emp_email_id from employee emp, roles role, department dept, designation desig, employee_login emplog where emp.role_id = role.role_id and emp.dept_id = dept.dept_id and emp.desig_id = desig.desig_id and emp.emp_eid =emplog.emp_eid and (emplog.emp_eid = :userName or emplog.emp_mbno = :userName or emplog.emp_email_id = :userName) and emplog.emp_password = :userPassword", nativeQuery = true)
    public List<Object[]> employeeLogin(@Param("userName") String userName, @Param("userPassword")String userPassword);

    @Modifying
    @Query(value = "update employee_login set EMP_PASSWORD=:empPassword where emp_mbno=:empUserName or emp_email_id=:empUserName or emp_eid=:empUserName", nativeQuery = true)
    public int updatePassword(@Param("empUserName") String empUserName,@Param("empPassword")String empPassword);

}
