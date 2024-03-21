package com.futurebizops.kpi.request.advsearch;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

@Data
public class EmployeeAdvSearchRequest {


     Integer roleId;
     Integer deptId;
     Integer desigId;
     Integer regionId;
     Integer siteId;
     Integer companyId;
     Integer empTypeId;
}
