package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.entity.ComplaintEntity;
import com.futurebizops.kpi.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComplaintRepo extends JpaRepository<ComplaintEntity, Integer> {

    public Optional<ComplaintEntity> findByCompTypeIdAndCompStatusEqualsIgnoreCase(Integer compTypeId, String compStatus);
}
