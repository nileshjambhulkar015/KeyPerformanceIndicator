package com.futurebizops.kpi.response;

import lombok.Data;
import org.springframework.data.domain.PageImpl;

import javax.persistence.Column;
import java.util.List;

@Data
public class CummalitiveEmployeeResponse {
    private Double sumOfEmployeeRatings;
    private Double sumOfHodRatings;
    private Double sumOfGMRatings;

    private Double cummulativeRatings;
    private Integer totalMonths;
    private Double avgCummulativeRatings;

    private Integer empId;
    private String empEId;
    private String empName;
    private Integer deptId;
    private String deptName;
    private Integer desigId;
    private String desigName;
    private String empMobileNo;
    private String emailId;

    private String finYear;
    private String empKeyStrength;
    private String empAreaOfImprovement;
    private String empTrainginDevelopmentNeeds;

    PageImpl<EmployeeKppStatusResponse> employeeKppStatusResponses;
}
