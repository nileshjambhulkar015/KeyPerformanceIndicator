package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.constants.SQLQueryConstants;
import com.futurebizops.kpi.entity.RequestTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestTypeRepo extends JpaRepository<RequestTypeEntity, Integer> {

    public Optional<RequestTypeEntity> findByDeptIdAndReqTypeNameEqualsIgnoreCase(Integer deptId, String reqTypeName);

    @Query(value = SQLQueryConstants.REQUEST_TYPE_QUERY, nativeQuery = true)
    List<Object[]> getRequestTypeDetail(@Param("reqTypeId") Integer reqTypeId, @Param("reqTypeName") String reqTypeName, @Param("statusCd") String statusCd, @Param("sortName") String sortName, @Param("pageSize") Integer pageSize, @Param("pageOffset") Integer pageOffset);

    @Query(value = SQLQueryConstants.REQUEST_TYPE_COUNT_UERY, nativeQuery = true)
    Integer getRequestTypeCount(@Param("reqTypeId") Integer reqTypeId, @Param("reqTypeName") String reqTypeName, @Param("statusCd") String statusCd);

    @Query(value = SQLQueryConstants.REQUEST_TYPE_BY_ID_QUERY, nativeQuery = true)
    List<Object[]> getRequestTypeByIdDetail(@Param("reqTypeId") Integer reqTypeId);

    @Query(value = "select distinct req.dept_id,dept.dept_name, req.status_cd from request_type req,department dept where dept.status_cd ='A' and req.status_cd='A' and req.dept_id=dept.dept_id", nativeQuery = true)
    public List<Object[]> findAllDepartmentFromRequestType();

    @Query(value = "select req.req_type_id, req.req_type_NAME, req.status_cd from request_type req where req.status_cd='A' and req.dept_id=:deptId", nativeQuery = true)
    public List<Object[]> findAllRequestTypeByDeptId(@Param("deptId") Integer deptId);
}
