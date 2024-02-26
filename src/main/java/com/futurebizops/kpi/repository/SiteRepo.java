package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.constants.SQLQueryConstants;
import com.futurebizops.kpi.entity.DesignationEntity;
import com.futurebizops.kpi.entity.SiteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SiteRepo extends JpaRepository<SiteEntity, Integer> {

    public Optional<SiteEntity> findByRegionIdAndSiteNameEqualsIgnoreCase(Integer regionId, String siteName);

    @Query(value = SQLQueryConstants.SITE_QUERY, nativeQuery = true)
    List<Object[]> getSiteDetail(@Param("siteId") Integer siteId, @Param("regionId") Integer regionId, @Param("siteName") String siteName, @Param("statusCd") String statusCd, @Param("sortName") String sortName, @Param("pageSize") Integer pageSize, @Param("pageOffset") Integer pageOffset);

    @Query(value = SQLQueryConstants.SITE_COUNT_UERY, nativeQuery = true)
    Integer getSiteCount(@Param("siteId") Integer siteId, @Param("regionId") Integer regionId, @Param("siteName") String siteName, @Param("statusCd") String statusCd);

}
