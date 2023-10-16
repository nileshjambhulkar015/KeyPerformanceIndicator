package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.constants.SQLQueryConstants;
import com.futurebizops.kpi.entity.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegionRepo extends JpaRepository<RegionEntity, Integer> {

    public Optional<RegionEntity> findByRegionNameEqualsIgnoreCase(String regionName);

    @Query(value = SQLQueryConstants.REGION_QUERY, nativeQuery = true)
    List<Object[]> getRegionDetails(@Param("regionId")Integer regionId, @Param("regionName") String regionName, @Param("statusCd") String statusCd, @Param("sortName") String sortName, @Param("pageSize") Integer pageSize, @Param("pageOffset") Integer pageOffset);

    @Query(value = SQLQueryConstants.REGION_COUNT_QUERY, nativeQuery = true)
    Integer getRegionCount(@Param("regionId")Integer regionId, @Param("regionName") String regionName, @Param("statusCd") String statusCd);

}
