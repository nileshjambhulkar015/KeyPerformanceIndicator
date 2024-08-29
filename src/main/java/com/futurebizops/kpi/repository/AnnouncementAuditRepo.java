package com.futurebizops.kpi.repository;

import com.futurebizops.kpi.entity.AnnouncementAudit;
import com.futurebizops.kpi.entity.EmployeeMeetingAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementAuditRepo extends JpaRepository<AnnouncementAudit, Integer> {
}
