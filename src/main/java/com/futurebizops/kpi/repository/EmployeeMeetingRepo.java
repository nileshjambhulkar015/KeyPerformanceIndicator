package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.constants.SQLQueryConstants;
import com.futurebizops.kpi.entity.EmployeeMeetingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface EmployeeMeetingRepo extends JpaRepository<EmployeeMeetingEntity, Integer> {


    @Query(value = SQLQueryConstants.EMPLOYEE_MEETING_QUERY, nativeQuery = true)
    List<Object[]> getEmployeeMeetingDetail( @Param("pageSize") Integer pageSize, @Param("pageOffset") Integer pageOffset);

    @Query(value = SQLQueryConstants.EMPLOYEE_MEETING_COUNT_QUERY, nativeQuery = true)
    Integer getEmployeeMeetingCount();

    @Query(value = SQLQueryConstants.EMPLOYEE_MEETING_BY_ID_QUERY, nativeQuery = true)
    List<Object[]> getMeetingByMeetingId( @Param("meetingId") Integer meetingId, @Param("statusCd") String statusCd);

    @Modifying
    @Query(value = "update meeting_master set meet_status=:meetStatus where meet_id =:meetId", nativeQuery = true)
    public int cancelMeeting(@Param("meetId") Integer meetId, @Param("meetStatus") String meetStatus);

}
