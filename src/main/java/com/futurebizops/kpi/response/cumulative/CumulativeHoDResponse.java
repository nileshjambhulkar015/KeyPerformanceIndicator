package com.futurebizops.kpi.response.cumulative;

import lombok.Data;

@Data
public class CumulativeHoDResponse {

  HODCumulativeData hodCumulativeData = new HODCumulativeData();
  TotalCumulativeHoD totalCumulativeHoD = new TotalCumulativeHoD();

    public CumulativeHoDResponse(Object[] objects){

        hodCumulativeData.setEmpId(Integer.parseInt(String.valueOf(objects[1])));
        hodCumulativeData.setEmpName(String.valueOf(objects[2]) + String.valueOf(objects[3]) + String.valueOf(objects[4]));
        hodCumulativeData.setEmpEId(String.valueOf(objects[5]));
        hodCumulativeData.setRoleId(Integer.parseInt(String.valueOf(objects[6])));
        hodCumulativeData.setRoleName(String.valueOf(objects[7]));
        hodCumulativeData.setDeptId(Integer.parseInt(String.valueOf(objects[8])));
        hodCumulativeData.setDeptName(String.valueOf(objects[9]));
        hodCumulativeData.setDesigId(Integer.parseInt(String.valueOf(objects[10])));
        hodCumulativeData.setDesigName(String.valueOf(objects[11]));

        totalCumulativeHoD.setEkppMonth(String.valueOf(objects[0]));
        totalCumulativeHoD.setTotalEmpAchivedWeight(String.valueOf(objects[12]));
        totalCumulativeHoD.setTotalHoDAchivedWeight(String.valueOf(objects[13]));
        totalCumulativeHoD.setTotalGMAchivedWeight(String.valueOf(objects[14]));

        totalCumulativeHoD.setTotalWeight(Double.valueOf(String.valueOf(objects[12])) + Double.valueOf(String.valueOf(objects[13])) + Double.valueOf(String.valueOf(objects[14])));
    }
}
