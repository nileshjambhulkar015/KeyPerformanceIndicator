package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.constants.SQLQueryConstants;
import com.futurebizops.kpi.entity.RoleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<RoleEntity, Integer> {

    public Optional<RoleEntity> findByRoleNameEqualsIgnoreCase(String roleName);

    @Query(value = SQLQueryConstants.ROLE_QUERY, nativeQuery = true)
    List<Object[]> getRoleDetails(@Param("roleId")Integer roleId, @Param("roleName") String roleName, @Param("sortName") String sortName, @Param("pageSize") Integer pageSize, @Param("pageOffset") Integer pageOffset);

    @Query(value = SQLQueryConstants.ROLE_COUNT_QUERY, nativeQuery = true)
    Integer getRoleDetailsCount(@Param("roleId")Integer roleId, @Param("roleName") String roleName);


    @Query(value = "select * from roles where status_cd ='A'", nativeQuery = true)
    public List<RoleEntity> findAllRolesDetails();

    //only for role id and name which is inside department table
    @Query(value = SQLQueryConstants.DD_ROLES_QUERY, nativeQuery = true)
    List<Object[]> ddEmployeeRoleExceptGM();
}
