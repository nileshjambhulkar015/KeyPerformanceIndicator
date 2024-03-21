package com.futurebizops.kpi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeAdvSearchModel {


    Integer roleId;
    Integer deptId;
    Integer desigId;
    Integer regionId;
    Integer siteId;
    Integer companyId;
    Integer empTypeId;
    Pageable pageable;
}
