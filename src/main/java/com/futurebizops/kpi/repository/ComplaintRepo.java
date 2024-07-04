package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.constants.SQLQueryConstants;
import com.futurebizops.kpi.entity.ComplaintEntity;
import com.futurebizops.kpi.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComplaintRepo extends JpaRepository<ComplaintEntity, Integer> {



    public Optional<ComplaintEntity> findByCompIdAndCompStatusEqualsIgnoreCase(String compTypeId, String compStatus);

    @Query(value = SQLQueryConstants.EMPLOYEE_COMPLAINT_QUERY, nativeQuery = true)
    List<Object[]> getEmployeeComplaintDetail(@Param("empId") Integer empId, @Param("compId") String compId,@Param("compTypeName") String compTypeName,@Param("compStatus") String compStatus, @Param("statusCd") String statusCd, @Param("sortName") String sortName, @Param("pageSize") Integer pageSize, @Param("pageOffset") Integer pageOffset);

    @Query(value = SQLQueryConstants.EMPLOYEE_COMPLAINT_COUNT_UERY, nativeQuery = true)
    Integer getEmployeeComplaintCount(@Param("empId") Integer empId, @Param("compId") String compId, @Param("compTypeName") String compTypeName,@Param("compStatus") String compStatus, @Param("statusCd") String statusCd);

    @Query(value = SQLQueryConstants.EMPLOYEE_COMPLAINT_BY_ID_QUERY, nativeQuery = true)
    List<Object[]> getEmployeeComplaintByIdDetail(@Param("empCompId") Integer empCompId);
}
