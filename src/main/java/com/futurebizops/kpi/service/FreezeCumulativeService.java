package com.futurebizops.kpi.service;

import com.futurebizops.kpi.request.EmployeeTypeCreateRequest;
import com.futurebizops.kpi.request.FreezeCumulativeCreateRequest;
import com.futurebizops.kpi.response.KPIResponse;
import org.springframework.data.domain.Pageable;

public interface FreezeCumulativeService {
    public KPIResponse saveFreezeCumulativeService(FreezeCumulativeCreateRequest freezeCumulativeCreateRequest);
}
