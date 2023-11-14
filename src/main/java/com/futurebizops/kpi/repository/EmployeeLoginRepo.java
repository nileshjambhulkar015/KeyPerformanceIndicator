package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.entity.EmployeeLoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface EmployeeLoginRepo extends JpaRepository<EmployeeLoginEntity, Integer> {

    public Optional<EmployeeLoginEntity> findByEmpMobileNoAndEmpPasswordAndStatusCd(String empMobileNo, String empPassword, String statusCd);

    @Modifying
    @Query(value = "update employee_login set EMP_PASSWORD=:empPassword where emp_mbno=:empUserName or emp_email_id=:empUserName or emp_eid=:empUserName", nativeQuery = true)
    public int updatePassword(@Param("empUserName") String empUserName,@Param("empPassword")String empPassword);

}
