package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.constants.SQLQueryConstants;
import com.futurebizops.kpi.entity.DesignationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DesignationRepo extends JpaRepository<DesignationEntity, Integer> {

    public Page<DesignationEntity> findByDesigIdAndStatusCd(Integer desigId, String statusCd, Pageable pageable);

    public Page<DesignationEntity> findByDeptIdAndStatusCd(Integer deptId, String statusCd, Pageable pageable);

    public Page<DesignationEntity> findByDesigNameStartingWithIgnoreCaseAndStatusCd(String desigName, String statusCd, Pageable pageable);

    public Page<DesignationEntity> findByStatusCd(String status, Pageable pageable);

    @Query(value = "select * from designation desig where desig.status_cd='A' and desig.dept_id = :deptId", nativeQuery = true)
    public List<DesignationEntity> findAllDesignation(Integer deptId);

    public Optional<DesignationEntity> findByDeptIdAndDesigNameEqualsIgnoreCase(Integer deptId, String desigName);

    @Query(value = SQLQueryConstants.DESIGNATION_QUERY, nativeQuery = true)
    List<Object[]> getDesignationDetail(@Param("roleId") Integer roleId, @Param("deptId") Integer deptId, @Param("desigName") String desigName, @Param("statusCd") String statusCd, @Param("sortName") String sortName,  @Param("pageSize") Integer pageSize, @Param("pageOffset") Integer pageOffset);

    @Query(value = SQLQueryConstants.DESIGNATION_COUNT_QUERY, nativeQuery = true)
    Integer getDesignationCount(@Param("roleId") Integer roleId, @Param("deptId") Integer deptId, @Param("desigName") String desigName, @Param("statusCd") String statusCd);

    @Query(value = SQLQueryConstants.DESIGNATION_BY_DESIG_ID_QUERY, nativeQuery = true)
    List<Object[]> getDesignationByDesigId(@Param("desigId") Integer desigId);


//only for deptId and name which is inside designation table
    @Query(value = SQLQueryConstants.DEPT_IN_DESIGNATION_QUERY, nativeQuery = true)
    List<Object[]> getDeptInDesigById(@Param("deptId") Integer deptId);


    //only for desig id and desig name from desig table
    @Query(value = SQLQueryConstants.DESIGNATION_BY_DEPT_ID_QUERY, nativeQuery = true)
    List<Object[]> getAllDesigByDeptId(@Param("deptId") Integer deptId);

    //for designation
}

