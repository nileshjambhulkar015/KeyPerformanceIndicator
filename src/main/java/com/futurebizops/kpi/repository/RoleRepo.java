package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.constants.SQLQueryConstants;
import com.futurebizops.kpi.entity.RoleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<RoleEntity, Integer> {

    public Optional<RoleEntity> findByRoleNameEqualsIgnoreCase(String roleName);

    public Page<RoleEntity> findByRoleIdAndStatusCd(Integer roleId, String statusCd, Pageable pageable);

    public Page<RoleEntity> findByRoleNameContainingIgnoreCaseAndStatusCd(String roleName, String statusCd, Pageable pageable);

    public Page<RoleEntity> findByStatusCd(String status, Pageable pageable);

    @Query(value = "select * from roles where status_cd ='A'", nativeQuery = true)
    public List<RoleEntity> findAllRolesDetails();

    //only for role id and name which is inside department table
    @Query(value = SQLQueryConstants.ROLE_IN_DEPARTMENT_QUERY, nativeQuery = true)
    List<Object[]> getAllRoleFromDept();

    @Query(value = SQLQueryConstants.ROLE_IN_DESIGNATION_QUERY, nativeQuery = true)
    List<Object[]> getAllRoleFromDesignation();
}
