package com.futurebizops.kpi.service.serviceimpl;

import com.futurebizops.kpi.dto.EmployeeKppDetailsDto;
import com.futurebizops.kpi.dto.EmployeeKppMasterDto;
import com.futurebizops.kpi.dto.EmployeeKppStatusDto;
import com.futurebizops.kpi.repository.EmployeeKppMasterRepo;
import com.futurebizops.kpi.response.EmpKppStatusResponse;
import com.futurebizops.kpi.service.EmployeeKppStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmployeeKppStatusServiceImpl implements EmployeeKppStatusService {

    @Autowired
    private EmployeeKppMasterRepo keyPerfParameterRepo;

    @Override
    public EmpKppStatusResponse getEmployeeKppStatus(Integer empId) {
        List<EmpKppStatusResponse> empKppStatusResponses = new ArrayList<>();
        List<Object[]> employeeKppData = keyPerfParameterRepo.getEmployeeKPPStatus(empId);
        List<EmployeeKppStatusDto> employeeKppStatusDtos = employeeKppData.stream().map(EmployeeKppStatusDto::new).collect(Collectors.toList());
        EmpKppStatusResponse statusResponse=null;
        Map<EmployeeKppMasterDto, List<EmployeeKppDetailsDto>> employeeKppMasterDtoListMap =
                employeeKppStatusDtos.stream().collect(Collectors.groupingBy(EmployeeKppStatusDto::getEmployeeKppMasterDto, Collectors.mapping(EmployeeKppStatusDto::getEmployeeKppDetailsDto, Collectors.toList())));

        for (Map.Entry<EmployeeKppMasterDto, List<EmployeeKppDetailsDto>> masterDtoListEntry : employeeKppMasterDtoListMap.entrySet()) {
            statusResponse = new EmpKppStatusResponse();
            statusResponse.setEKppMId(masterDtoListEntry.getKey().getEKppMId());
            statusResponse.setEmpEId(masterDtoListEntry.getKey().getEmpEId());

            statusResponse.setEKppMId(masterDtoListEntry.getKey().getEKppMId());
            //  statusResponse.setEkppMonth(masterDtoListEntry.getKey().getEkppMonth());
            statusResponse.setEmpId(masterDtoListEntry.getKey().getEmpId());
            statusResponse.setEmpName(masterDtoListEntry.getKey().getEmpName());
            statusResponse.setEmpEId(masterDtoListEntry.getKey().getEmpEId());
            statusResponse.setRoleId(masterDtoListEntry.getKey().getRoleId());
            statusResponse.setRoleName(masterDtoListEntry.getKey().getRoleName());
            statusResponse.setDeptId(masterDtoListEntry.getKey().getDeptId());
            statusResponse.setDeptName(masterDtoListEntry.getKey().getDeptName());
            statusResponse.setDesigId(masterDtoListEntry.getKey().getDesigId());
            statusResponse.setDesigName(masterDtoListEntry.getKey().getDesigName());
            statusResponse.setTotalEmpAchivedWeight(masterDtoListEntry.getKey().getTotalEmpAchivedWeight());
            statusResponse.setTotalEmpOverallAchieve(masterDtoListEntry.getKey().getTotalEmpOverallAchieve());
            statusResponse.setTotalEmpOverallTaskComp(masterDtoListEntry.getKey().getTotalEmpOverallTaskComp());
            //statusResponse.setEmpKppAppliedDate(masterDtoListEntry.getKey().getEmpKppAppliedDate());
            statusResponse.setEmpKppStatus(masterDtoListEntry.getKey().getEmpKppStatus());
            statusResponse.setEmpRemark(masterDtoListEntry.getKey().getEmpRemark());
            statusResponse.setHodEmpId(masterDtoListEntry.getKey().getHodEmpId());
            statusResponse.setTotalHodAchivedWeight(masterDtoListEntry.getKey().getTotalHodAchivedWeight());
            statusResponse.setTotalHodOverallAchieve(masterDtoListEntry.getKey().getTotalHodOverallAchieve());
            statusResponse.setTotalHodOverallTaskComp(masterDtoListEntry.getKey().getTotalHodOverallTaskComp());
            //  statusResponse.setHodKppAppliedDate(masterDtoListEntry.getKey().getHodKppAppliedDate());
            statusResponse.setHodKppStatus(masterDtoListEntry.getKey().getHodKppStatus());
            statusResponse.setHodRemark(masterDtoListEntry.getKey().getHodRemark());
            statusResponse.setGmEmpId(masterDtoListEntry.getKey().getGmEmpId());
            statusResponse.setTotalGmAchivedWeight(masterDtoListEntry.getKey().getTotalGmAchivedWeight());
            statusResponse.setTotalGmOverallAchieve(masterDtoListEntry.getKey().getTotalGmOverallAchieve());
            statusResponse.setTotalGmOverallTaskComp(masterDtoListEntry.getKey().getTotalGmOverallTaskComp());
            // statusResponse.setGmKppAppliedDate(masterDtoListEntry.getKey().getGmKppAppliedDate());
            statusResponse.setGmKppStatus(masterDtoListEntry.getKey().getGmKppStatus());
            statusResponse.setGmRemark(masterDtoListEntry.getKey().getGmRemark());
            statusResponse.setRemark(masterDtoListEntry.getKey().getRemark());

            statusResponse.setKppStatusDetails(masterDtoListEntry.getValue());
            empKppStatusResponses.add(statusResponse);
        }
        return statusResponse;
    }
}
