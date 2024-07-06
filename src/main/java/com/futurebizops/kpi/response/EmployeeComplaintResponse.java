package com.futurebizops.kpi.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Column;
import java.time.Instant;

@Data
public class EmployeeComplaintResponse {

    @Schema(example = "1", description = "This field is used for complaint date")
    private Integer empCompId;


    @Schema(example = "1", description = "This field is used for complaint date")
    private Integer empId;
    private String empEId;
    private String empName;
    private String empMobileNo;

    private Integer roleId;
    private String roleName;
    private Integer deptId;
    private String deptName;
    private Integer desigId;
    private String desigName;


    @Schema(example = "1", description = "This field is used for complaint date")
    private String compId;

    @Schema(example = "2024-02-25", description = "This field is used for complaint date")
    private String compDate;

    @Schema(example = "2024-03-25", description = "This field is used for complaint date")
    private String compResolveDate;

    @Schema(example = "1", description = "This field is used for complaint type")
    private Integer compTypeId;

    @Schema(example = "1", description = "This field is used for complaint date")
    private String compTypeName;

    @Schema(example = "HR and Admin", description = "This field is used for complaint description")
    private String compDesc;

    @Schema(example = "Pending / Completed", description = "This field is used for complaint description")
    private String compStatus;

    @Schema(example = "This is remark", description = "This field is used for department remark")
    private String remark;

    @Schema(example = "A", description = "This field is used for role status")
    private String statusCd;

    public EmployeeComplaintResponse(Object[] objects){
        this.empCompId=Integer.parseInt(String.valueOf(objects[0]));
        this.empId=Integer.parseInt(String.valueOf(objects[1]));
        this.empEId=String.valueOf(objects[2]);
        this.empName=String.valueOf(objects[3]) +" "+String.valueOf(objects[4])+" "+String.valueOf(objects[5]);
        this.empMobileNo=String.valueOf(objects[6]);
        this.roleId=Integer.parseInt(String.valueOf(objects[7]));
        this.roleName=String.valueOf(objects[8]);
        this.deptId=Integer.parseInt(String.valueOf(objects[9]));
        this.deptName=String.valueOf(objects[10]);
        this.desigId=Integer.parseInt(String.valueOf(objects[11]));
        this.desigName=String.valueOf(objects[12]);
        this.compId=String.valueOf(objects[13]);
        this.compDate=String.valueOf(objects[14]);
        this.compResolveDate=String.valueOf(objects[15]);
        this.compTypeId=Integer.parseInt(String.valueOf(objects[16]));
        this.compTypeName=String.valueOf(objects[17]);
        this.compDesc=String.valueOf(objects[18]);
        this.compStatus=String.valueOf(objects[19]);
        this.remark=String.valueOf(objects[20]);
        this.statusCd=String.valueOf(objects[21]);
    }

}
