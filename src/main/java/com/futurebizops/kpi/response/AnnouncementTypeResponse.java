package com.futurebizops.kpi.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementTypeResponse {

    private Integer annonTypeId;
    private String announTypeName;
    private String remark;
    private String statusCd;

    public AnnouncementTypeResponse(Object[] objects){
        annonTypeId=Integer.parseInt(String.valueOf(objects[0]));
        announTypeName =String.valueOf(objects[1]);
        remark=String.valueOf(objects[2]);
        statusCd=String.valueOf(objects[3]);
    }
}
