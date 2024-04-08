package com.futurebizops.kpi.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvidenceResponse {

    private Integer empId;
    private String evFileName;

    public EvidenceResponse(Object[] objects){
        this.empId=Integer.parseInt(String.valueOf(objects[0]));
        this.evFileName=String.valueOf(objects[1]);
    }
}
