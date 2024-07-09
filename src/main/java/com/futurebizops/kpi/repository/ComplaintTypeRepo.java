package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.constants.SQLQueryConstants;
import com.futurebizops.kpi.entity.ComplaintTypeEntity;
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
}
