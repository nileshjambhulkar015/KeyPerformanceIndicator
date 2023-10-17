package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.entity.KeyPerfParamEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeyPerfParameterRepo extends JpaRepository<KeyPerfParamEntity, Integer> {

    public Page<KeyPerfParamEntity> findByStatusCd(String status, Pageable pageable);

    public List<KeyPerfParamEntity> findByDeptIdAndDesigIdAndStatusCd(Integer deptId, Integer degidId, String status);
}
