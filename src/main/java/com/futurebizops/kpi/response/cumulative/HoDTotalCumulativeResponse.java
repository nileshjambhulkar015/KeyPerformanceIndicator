package com.futurebizops.kpi.response.cumulative;

import lombok.Data;

@Data
public class HoDTotalCumulativeResponse {

    private Integer empId;
    private String empName;
    private String empEId;
    private Integer roleId;
    private String roleName;
    private Integer deptId;
    private String deptName;
    private Integer desigId;
    private String desigName;
    private Double totalHodKppRatings;
    private Double avgTotalHodKppRatings;
}
