package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.constants.SQLQueryConstants;
import com.futurebizops.kpi.entity.ComplaintEntity;
import com.futurebizops.kpi.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface ComplaintRepo extends JpaRepository<ComplaintEntity, Integer> {



    public Optional<ComplaintEntity> findByCompIdAndCompStatusEqualsIgnoreCase(String compTypeId, String compStatus);

    @Query(value = SQLQueryConstants.EMPLOYEE_COMPLAINT_QUERY, nativeQuery = true)
    List<Object[]> getEmployeeComplaintDetail(@Param("empId") Integer empId, @Param("compId") String compId,@Param("roleId") Integer roleId,@Param("deptId") Integer deptId,@Param("compTypeName") String compTypeName,@Param("compStatus") String compStatus, @Param("compTypeDeptId") Integer compTypeDeptId,@Param("resolveEmpId") Integer resolveEmpId, @Param("statusCd") String statusCd, @Param("sortName") String sortName, @Param("pageSize") Integer pageSize, @Param("pageOffset") Integer pageOffset);

    @Query(value = SQLQueryConstants.EMPLOYEE_COMPLAINT_COUNT_UERY, nativeQuery = true)
    Integer getEmployeeComplaintCount(@Param("empId") Integer empId, @Param("compId") String compId,@Param("roleId") Integer roleId,@Param("deptId") Integer deptId, @Param("compTypeName") String compTypeName,@Param("compStatus") String compStatus,@Param("compTypeDeptId") Integer compTypeDeptId,@Param("resolveEmpId") Integer resolveEmpId, @Param("statusCd") String statusCd);

    @Query(value = SQLQueryConstants.EMPLOYEE_COMPLAINT_BY_ID_QUERY, nativeQuery = true)
    List<Object[]> getEmployeeComplaintByIdDetail(@Param("empCompId") Integer empCompId);

    @Modifying
    @Query(value = "update employee_complaint set status_cd='I' where emp_comp_id =:empCompId", nativeQuery = true)
    public int deleteEmployeeComplaint(@Param("empCompId") Integer empCompId);

    @Modifying
    @Query(value = "update employee_complaint set emp_comp_desc=:compDesc where emp_comp_id =:empCompId", nativeQuery = true)
    public int updateEmployeeComplaintDescription(@Param("empCompId") Integer empCompId,@Param("compDesc") String compDesc);

    @Modifying
    @Query(value = "update employee_complaint set comp_status=:compStatus,comp_resolve_date=:complaintResolveDate, remark=:remark where emp_comp_id =:empCompId", nativeQuery = true)
    public int updateAdminHandleComplaintDescription(@Param("empCompId") Integer empCompId, @Param("compStatus") String compStatus, @Param("complaintResolveDate") Instant complaintResolveDate, @Param("remark") String remark);

    @Modifying
    @Query(value = "update employee_complaint set comp_status=:compStatus,comp_resolve_emp_id=:compResolveEmpId, comp_resolve_emp_name=:compResolveEmpName,comp_resolve_emp_eid=:compResolveEmpEId where emp_comp_id =:empCompId", nativeQuery = true)
    public int updateEmpAssignComplaintHimself(@Param("empCompId") Integer empCompId, @Param("compStatus") String compStatus, @Param("compResolveEmpId") Integer compResolveEmpId,@Param("compResolveEmpName") String compResolveEmpName,@Param("compResolveEmpEId") String compResolveEmpEId);

}
