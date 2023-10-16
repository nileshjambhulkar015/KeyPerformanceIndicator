package com.futurebizops.kpi.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

    private Integer empId;
    private String empFirstName;
    private String empMiddleName;
    private String empLastName;
    private String empMobileNo;
    private String emailId;
    private Integer roleId;
    private String remark;
}
