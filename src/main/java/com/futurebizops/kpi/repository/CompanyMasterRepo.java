package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.constants.DropDownQueryConstants;
import com.futurebizops.kpi.constants.SQLQueryConstants;
import com.futurebizops.kpi.entity.CompanyMasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyMasterRepo extends JpaRepository<CompanyMasterEntity, Integer> {

    public Optional<CompanyMasterEntity> findByCompanyNameEqualsIgnoreCaseAndRegionIdAndSiteId(String companyName, Integer regionId, Integer siteId);

    @Query(value = SQLQueryConstants.COMPANY_MASTER_QUERY, nativeQuery = true)
    List<Object[]> getCompanyDetail(@Param("regionId") Integer regionId, @Param("siteId") Integer siteId, @Param("companyName") String companyName, @Param("statusCd") String statusCd, @Param("sortName") String sortName, @Param("pageSize") Integer pageSize, @Param("pageOffset") Integer pageOffset);

    @Query(value = SQLQueryConstants.COMPANY_MASTER_COUNT_QUERY, nativeQuery = true)
    Integer getCompanyCount(@Param("regionId") Integer regionId, @Param("siteId") Integer siteId, @Param("companyName") String companyName, @Param("statusCd") String statusCd);

    //only for desig id and desig name from desig table
    @Query(value = SQLQueryConstants.COMPANY_BY_COMP_ID_QUERY, nativeQuery = true)
    List<Object[]> getAllCompanyByCompId(@Param("companyId") Integer companyId);

    @Query(value = SQLQueryConstants.DD_COMPANY_BY_COMP_ID_QUERY, nativeQuery = true)
    List<Object[]> getAllCompanyByRegionIdAndSiteId(@Param("regionId") Integer regionId, @Param("siteId") Integer siteId);

    @Query(value = DropDownQueryConstants.DD_REGION_FROM_COMPANY_QUERY, nativeQuery = true)
    List<Object[]> getDDRegionFromCompany();

    @Query(value = DropDownQueryConstants.DD_SITE_FROM_COMPANY_QUERY, nativeQuery = true)
    List<Object[]> getDDSiteFromCompany(@Param("regionId") Integer regionId );

    @Query(value = DropDownQueryConstants.DD_COMPANY_FROM_COMPANY_QUERY, nativeQuery = true)
    List<Object[]> getDDCompanyFromCompany(@Param("regionId") Integer regionId ,@Param("siteId") Integer siteId );
}
