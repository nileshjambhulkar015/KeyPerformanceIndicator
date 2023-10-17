package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.entity.DesignationEntity;
import com.futurebizops.kpi.entity.SiteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SiteRepo extends JpaRepository<SiteEntity, Integer> {

    public Optional<SiteEntity> findByRegionIdAndSiteNameEqualsIgnoreCase(Integer regionId, String siteName);
}
