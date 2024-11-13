package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.constants.SQLQueryConstants;
import com.futurebizops.kpi.entity.AnnouncementTypeEntity;
import com.futurebizops.kpi.entity.DepartmentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnnouncementTypeRepo extends JpaRepository<AnnouncementTypeEntity, Integer> {
    public Optional<AnnouncementTypeEntity> findByAnnounTypeNameEqualsIgnoreCase(String announTypeName);

    @Modifying
    @Query(value = "update announcement_type set status_cd='I' where announ_type_id =:annonTypeId", nativeQuery = true)
    public int deleteAnnouncementTypeDetails(@Param("annonTypeId") Integer annonTypeId);


    @Query(value = SQLQueryConstants.ANNOUNCEMENT_TYPE_QUERY, nativeQuery = true)
    List<Object[]> getAnnouncementTypeDetail( @Param("annonTypeId") Integer annonTypeId, @Param("annonTypeName") String annonTypeName, @Param("statusCd") String statusCd, @Param("sortName") String sortName, @Param("pageSize") Integer pageSize, @Param("pageOffset") Integer pageOffset);

    @Query(value = SQLQueryConstants.ANNOUNCEMENT_TYPE_COUNT_UERY, nativeQuery = true)
    Integer getAnnouncementTypeCount(@Param("annonTypeId") Integer annonTypeId, @Param("annonTypeName") String annonTypeName, @Param("statusCd") String statusCd);

    @Query(value = SQLQueryConstants.DEPARTMENT_BY_ID_QUERY, nativeQuery = true)
    List<Object[]> getDepartmentByIdDetail(@Param("deptId") Integer deptId);



}
