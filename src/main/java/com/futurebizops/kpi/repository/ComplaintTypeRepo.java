package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.constants.SQLQueryConstants;
import com.futurebizops.kpi.entity.ComplaintTypeEntity;
import com.futurebizops.kpi.entity.DepartmentEntity;
import com.futurebizops.kpi.response.dropdown.ComplaintTypeDDResponse;
import com.futurebizops.kpi.response.dropdown.DepartmentDDResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComplaintTypeRepo extends JpaRepository<ComplaintTypeEntity, Integer> {

    public Optional<ComplaintTypeEntity> findByDeptIdAndCompTypeNameEqualsIgnoreCase(Integer deptId, String compTypeName);

    @Query(value = SQLQueryConstants.COMPLAINT_TYPE_QUERY, nativeQuery = true)
    List<Object[]> getComplaintTypeDetail(@Param("compTypeId") Integer compTypeId, @Param("compTypeName") String compTypeName, @Param("statusCd") String statusCd, @Param("sortName") String sortName, @Param("pageSize") Integer pageSize, @Param("pageOffset") Integer pageOffset);

    @Query(value = SQLQueryConstants.COMPLAINT_TYPE_COUNT_UERY, nativeQuery = true)
    Integer getComplaintTypeCount(@Param("compTypeId") Integer compTypeId, @Param("compTypeName") String compTypeName, @Param("statusCd") String statusCd);

    @Query(value = SQLQueryConstants.COMPLAINT_TYPE_BY_ID_QUERY, nativeQuery = true)
    List<Object[]> getComplaintTypeByIdDetail(@Param("compTypeId") Integer compTypeId);

    @Query(value = "select distinct comp.dept_id,dept.dept_name, comp.status_cd from complaint_type comp,department dept where dept.status_cd ='A' and comp.status_cd='A' and comp.dept_id=dept.dept_id", nativeQuery = true)
    public List<Object[]> findAllDepartmentFromComplaintType();

    @Query(value = "select comp.comp_type_id, comp.comp_type_NAME, comp.status_cd from complaint_type comp where comp.status_cd='A' and comp.dept_id=:deptId", nativeQuery = true)
    public List<Object[]> findAllComplaintTypeByDeptId(@Param("deptId") Integer deptId);

    public Optional<ComplaintTypeEntity> findByDeptId(Integer deptId);
}
