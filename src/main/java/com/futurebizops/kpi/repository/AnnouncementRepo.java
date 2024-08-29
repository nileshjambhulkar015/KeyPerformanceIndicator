package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.constants.SQLQueryConstants;
import com.futurebizops.kpi.entity.AnnouncementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepo extends JpaRepository<AnnouncementEntity, Integer> {


    @Query(value = SQLQueryConstants.ANNOUNCEMENT_QUERY, nativeQuery = true)
    List<Object[]> getAnnouncementDetail( @Param("announFromDate") String announFromDate,@Param("announToDate") String announToDate,@Param("statusCd") String statusCd, @Param("pageSize") Integer pageSize, @Param("pageOffset") Integer pageOffset);

    @Query(value = SQLQueryConstants.ANNOUNCEMENT_COUNT_QUERY, nativeQuery = true)
    Integer getAnnouncementCount(@Param("announFromDate") String announFromDate,@Param("announToDate") String announToDate,@Param("statusCd") String statusCd);

    @Query(value = SQLQueryConstants.ANNOUNCEMENT_BY_ID_QUERY, nativeQuery = true)
    List<Object[]> getAnnouncementByAnnounId( @Param("announId") Integer announId, @Param("statusCd") String statusCd);

    @Modifying
    @Query(value = "update announcement_master set announ_status=:announStatus, status_cd=:statusCd where announ_id =:announId", nativeQuery = true)
    public int cancelAnnouncement(@Param("announId") Integer announId, @Param("announStatus") String announStatus,@Param("statusCd") String statusCd);

    @Query(value = SQLQueryConstants.ADVANCE_SEARCH_ANNOUNCEMENT_QUERY, nativeQuery = true)
    List<Object[]> getAdvanceSearchAnnouncementDetail( @Param("announFromDate") String announFromDate,@Param("announToDate") String announToDate,@Param("asAnnounStatus") String asAnnounStatus, @Param("pageSize") Integer pageSize, @Param("pageOffset") Integer pageOffset);

    @Query(value = SQLQueryConstants.ADVANCE_SEARCH_ANNOUNCEMENT_COUNT_QUERY, nativeQuery = true)
    Integer getAdvanceSearcheAnnouncementCount(@Param("announFromDate") String announFromDate,@Param("announToDate") String announToDate, @Param("asAnnounStatus") String asAnnounStatus);

}
