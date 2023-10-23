package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.constants.SQLQueryConstants;
import com.futurebizops.kpi.entity.KeyPerfParamEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeyPerfParameterRepo extends JpaRepository<KeyPerfParamEntity, Integer> {

    public Page<KeyPerfParamEntity> findByStatusCd(String status, Pageable pageable);

    public List<KeyPerfParamEntity> findByDeptIdAndDesigIdAndStatusCd(Integer deptId, Integer degidId, String status);

    @Query(value = SQLQueryConstants.KPP_QUERY, nativeQuery = true)
    List<Object[]> getKeyPerfParameterDetail(@Param("kppId") Integer kppId,@Param("deptId") Integer deptId, @Param("desigId") Integer desigId,@Param("kppObjective") String kppObjective, @Param("statusCd") String statusCd, @Param("sortName") String sortName, @Param("pageSize") Integer pageSize, @Param("pageOffset") Integer pageOffset);

    @Query(value = SQLQueryConstants.KPP_COUNT_QUERY, nativeQuery = true)
    Integer getKeyPerfParameterCount(@Param("kppId") Integer kppId,@Param("deptId") Integer deptId, @Param("desigId") Integer desigId,@Param("kppObjective") String kppObjective, @Param("statusCd") String statusCd);

    @Query(value = SQLQueryConstants.KPP_BY_ID_QUERY, nativeQuery = true)
    List<Object[]> getKeyPerfParameterDetailById(@Param("kppId") Integer kppId);

}
