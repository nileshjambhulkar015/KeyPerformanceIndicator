package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.constants.SQLQueryConstants;
import com.futurebizops.kpi.entity.DesignationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DesignationRepo extends JpaRepository<DesignationEntity, Integer> {

    @Modifying
    @Query(value = "update designation set status_cd='I' where desig_id =:desigId", nativeQuery = true)
    public int deleteDesignationDetails(@Param("desigId") Integer desigId);

    public Optional<DesignationEntity> findByDeptIdAndDesigNameEqualsIgnoreCase(Integer deptId, String desigName);

    @Query(value = SQLQueryConstants.DESIGNATION_QUERY, nativeQuery = true)
    List<Object[]> getDesignationDetail( @Param("deptId") Integer deptId, @Param("desigName") String desigName, @Param("statusCd") String statusCd, @Param("sortName") String sortName,  @Param("pageSize") Integer pageSize, @Param("pageOffset") Integer pageOffset);

    @Query(value = SQLQueryConstants.DESIGNATION_COUNT_QUERY, nativeQuery = true)
    Integer getDesignationCount(@Param("deptId") Integer deptId, @Param("desigName") String desigName, @Param("statusCd") String statusCd);

    @Query(value = SQLQueryConstants.DESIGNATION_BY_DESIG_ID_QUERY, nativeQuery = true)
    List<Object[]> getDesignationByDesigId(@Param("desigId") Integer desigId);


//only for deptId and name which is inside designation table
    @Query(value = SQLQueryConstants.DEPT_IN_DESIGNATION_QUERY, nativeQuery = true)
    List<Object[]> getDeptInDesigById(@Param("deptId") Integer deptId);

    //only for desig id and desig name from desig table
    @Query(value = SQLQueryConstants.DESIGNATION_BY_DEPT_ID_QUERY, nativeQuery = true)
    List<Object[]> getAllDesigByDeptId(@Param("deptId") Integer deptId);

    public Optional<DesignationEntity> findByDesigNameEqualsIgnoreCaseAndDeptId(String desigName,Integer deptId);
}

