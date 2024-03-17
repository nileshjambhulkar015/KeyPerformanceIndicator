package com.futurebizops.kpi.response.cumulative;

import lombok.Data;

import java.util.List;

@Data
public class HoDCumulativeResponse {

    private Integer empId;
    private String empName;
    private String empEId;
    private Integer roleId;
    private String roleName;
    private Integer deptId;
    private String deptName;
    private Integer desigId;
    private String desigName;
    List<TotalCumulativeHoD> totalCumulativeHoDS;

    private String totalHodKppRatings;
    private Integer totalMonths;
    private String avgTotalHodKppRatings;
}
