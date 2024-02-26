package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.constants.SQLQueryConstants;
import com.futurebizops.kpi.entity.RoleEntity;
import com.futurebizops.kpi.entity.UoMEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UoMRepo extends JpaRepository<UoMEntity, Integer> {

    public Optional<UoMEntity> findByUomNameEqualsIgnoreCase(String uomName);

    @Query(value = SQLQueryConstants.UOM_QUERY, nativeQuery = true)
    List<Object[]> getUoMDetails(@Param("uomId")Integer uomId, @Param("uomName") String uomName, @Param("statusCd") String statusCd, @Param("sortName") String sortName, @Param("pageSize") Integer pageSize, @Param("pageOffset") Integer pageOffset);

    @Query(value = SQLQueryConstants.UOM_COUNT_QUERY, nativeQuery = true)
    Integer getUoMCount(@Param("uomId")Integer uomId, @Param("uomName") String uomName, @Param("statusCd") String statusCd);

}
