package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.entity.DepartmentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepo extends JpaRepository<DepartmentEntity, Integer> {

    public Page<DepartmentEntity> findByDeptIdAndStatusCd(Integer deptId, String statusCd, Pageable pageable);

    public Page<DepartmentEntity> findByDeptNameStartingWithIgnoreCaseAndStatusCd(String deptName, String statusCd, Pageable pageable);

    public Page<DepartmentEntity> findByStatusCd(String status, Pageable pageable);

    @Query(value = "select * from department where status_cd ='A' order by dept_name", nativeQuery = true)
    public List<DepartmentEntity> findAllDepartments();

    @Query(value = "select * from department dept, designation desig where dept.status_cd ='A' and desig.dept_id = dept.dept_id", nativeQuery = true)
    public List<DepartmentEntity> findAllDepartmentDetailsForEmployee();

    public Optional<DepartmentEntity> findByDeptNameEqualsIgnoreCase(String deptName);

}
