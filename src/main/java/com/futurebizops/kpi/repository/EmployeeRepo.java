package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.entity.EmployeeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<EmployeeEntity, Integer> {

    @Query(value = "select * from employee where status_cd='A'", nativeQuery = true)
    public List<EmployeeEntity> findAllEmployeeDetails();
    public Optional<EmployeeEntity> findByEmpMobileNoOrEmailIdEqualsIgnoreCase(String empMobileNo, String emailId);

    public Page<EmployeeEntity> findByEmpIdAndStatusCd(Integer empId, String statusCd, Pageable pageable);

    public Page<EmployeeEntity> findByEmpFirstNameAndStatusCd(String empFirstName, String statusCd, Pageable pageable);

    public Page<EmployeeEntity> findByDeptIdAndStatusCd(Integer deptId, String statusCd, Pageable pageable);

    public Page<EmployeeEntity> findByDesigIdAndStatusCd(Integer desigId, String statusCd, Pageable pageable);

    public Page<EmployeeEntity> findByRoleIdAndStatusCd(Integer roleId, String statusCd, Pageable pageable);

    public Page<EmployeeEntity> findByEmpMobileNoAndStatusCd(String empMobileNo, String statusCd, Pageable pageable);

    public Page<EmployeeEntity> findByEmpGenderAndStatusCd(String empGender, String statusCd, Pageable pageable);

    public Page<EmployeeEntity> findByStatusCd(String statusCd, Pageable pageable);


    public EmployeeEntity findByEmpMobileNoAndStatusCd(String empMobileNo, String statusCd);

}
