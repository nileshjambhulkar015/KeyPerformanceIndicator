package com.futurebizops.kpi.service.serviceimpl;

import com.futurebizops.kpi.dto.EmployeeKppDetailsDto;
import com.futurebizops.kpi.dto.EmployeeKppMasterDto;
import com.futurebizops.kpi.dto.EmployeeKppStatusDto;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.repository.EmployeeKppMasterRepo;
import com.futurebizops.kpi.repository.OverallEmployeeKppFeedbackMasterRepo;
import com.futurebizops.kpi.repository.ReportEmployeeKppMasterRepo;
import com.futurebizops.kpi.response.EmpKppStatusResponse;
import com.futurebizops.kpi.service.EmployeeKppStatusService;
import com.futurebizops.kpi.utils.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmployeeKppStatusServiceImpl implements EmployeeKppStatusService {

    @Autowired
    private EmployeeKppMasterRepo keyPerfParameterRepo;

    @Autowired
    private ReportEmployeeKppMasterRepo reportEmployeeKppMasterRepo;

    @Autowired
    private OverallEmployeeKppFeedbackMasterRepo freezeReportEmployeeKppMasterRepo;

    //shpw only 2 decimal value
    private static final DecimalFormat decfor = new DecimalFormat("0.00");

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public EmpKppStatusResponse getInPrgressEmployeeKppStatus(Integer empId) {
        log.debug("Inside EmployeeKppStatusServiceImpl >> getInPrgressEmployeeKppStatus() empId: {}", empId);

        List<EmpKppStatusResponse> empKppStatusResponses = new ArrayList<>();
        EmpKppStatusResponse statusResponse = null;
        try {
            List<Object[]> employeeKppData = keyPerfParameterRepo.getInProgressEmployeeKPPStatus(empId);
            if (employeeKppData.size() > 0) {
                List<EmployeeKppStatusDto> employeeKppStatusDtos = employeeKppData.stream().map(EmployeeKppStatusDto::new).collect(Collectors.toList());

                Map<EmployeeKppMasterDto, List<EmployeeKppDetailsDto>> employeeKppMasterDtoListMap =
                        employeeKppStatusDtos.stream().collect(Collectors.groupingBy(EmployeeKppStatusDto::getEmployeeKppMasterDto, Collectors.mapping(EmployeeKppStatusDto::getEmployeeKppDetailsDto, Collectors.toList())));

                for (Map.Entry<EmployeeKppMasterDto, List<EmployeeKppDetailsDto>> masterDtoListEntry : employeeKppMasterDtoListMap.entrySet()) {
                    statusResponse = new EmpKppStatusResponse();
                    statusResponse.setEKppMId(masterDtoListEntry.getKey().getEKppMId());
                    statusResponse.setEmpEId(masterDtoListEntry.getKey().getEmpEId());

                    statusResponse.setEKppMId(masterDtoListEntry.getKey().getEKppMId());
                    statusResponse.setEkppMonth(masterDtoListEntry.getKey().getEkppMonth());
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
                    statusResponse.setTotalOverallRatings(masterDtoListEntry.getKey().getTotalOverallRatings());
                    statusResponse.setTotalOverallPercentage(masterDtoListEntry.getKey().getTotalOverallPercentage());
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
                    statusResponse.setCompanyId(masterDtoListEntry.getKey().getCompanyId());
                    statusResponse.setCompanyName(masterDtoListEntry.getKey().getCompanyName());
                    statusResponse.setCompanyAddress(masterDtoListEntry.getKey().getCompanyAddress());
                    statusResponse.setCompanyMbNo(masterDtoListEntry.getKey().getCompanyMbNo());
                    statusResponse.setCompanyFinYear(masterDtoListEntry.getKey().getCompanyFinYear());

                    statusResponse.setKppStatusDetails(masterDtoListEntry.getValue());
                    empKppStatusResponses.add(statusResponse);
                }
            } else {
                log.error("EmployeeKppStatusServiceImpl >> getInPrgressEmployeeKppStatus() Record not found");
            }
            return statusResponse;
        } catch (Exception ex) {
            log.error("Inside EmployeeKppStatusServiceImpl >> getInPrgressEmployeeKppStatus() : {}", ex);
            throw new KPIException("EmployeeKppStatusServiceImpl >> getInPrgressEmployeeKppStatus()", false, ex.getMessage());
        }
    }


    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public EmpKppStatusResponse getCompletedEmployeeKppStatus(Integer empId, String ekppMonth) {
        log.debug("Inside EmployeeKppStatusServiceImpl >> getCompletedEmployeeKppStatus() empId: {}, ekppMonth : {}", empId, ekppMonth);
        try {
            String ekkStatusMonth = StringUtils.isNotEmpty(ekppMonth) ? DateTimeUtils.addOneDayToInstant(ekppMonth).toString() : Instant.now().toString();
            List<EmpKppStatusResponse> empKppStatusResponses = new ArrayList<>();
            EmpKppStatusResponse statusResponse = null;
            List<Object[]> employeeKppData = keyPerfParameterRepo.getCompletedEmployeeKPPStatus(empId, ekkStatusMonth);
            if (employeeKppData.size() > 0) {
                List<EmployeeKppStatusDto> employeeKppStatusDtos = employeeKppData.stream().map(EmployeeKppStatusDto::new).collect(Collectors.toList());

                Map<EmployeeKppMasterDto, List<EmployeeKppDetailsDto>> employeeKppMasterDtoListMap =
                        employeeKppStatusDtos.stream().collect(Collectors.groupingBy(EmployeeKppStatusDto::getEmployeeKppMasterDto, Collectors.mapping(EmployeeKppStatusDto::getEmployeeKppDetailsDto, Collectors.toList())));

                for (Map.Entry<EmployeeKppMasterDto, List<EmployeeKppDetailsDto>> masterDtoListEntry : employeeKppMasterDtoListMap.entrySet()) {
                    statusResponse = new EmpKppStatusResponse();
                    statusResponse.setEKppMId(masterDtoListEntry.getKey().getEKppMId());
                    statusResponse.setEmpEId(masterDtoListEntry.getKey().getEmpEId());

                    statusResponse.setEKppMId(masterDtoListEntry.getKey().getEKppMId());
                    statusResponse.setEkppMonth(masterDtoListEntry.getKey().getEkppMonth());
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

                    statusResponse.setTotalOverallRatings(masterDtoListEntry.getKey().getTotalOverallRatings());
                    statusResponse.setTotalOverallPercentage(masterDtoListEntry.getKey().getTotalOverallPercentage());

                    // statusResponse.setGmKppAppliedDate(masterDtoListEntry.getKey().getGmKppAppliedDate());
                    statusResponse.setGmKppStatus(masterDtoListEntry.getKey().getGmKppStatus());
                    statusResponse.setGmRemark(masterDtoListEntry.getKey().getGmRemark());
                    statusResponse.setRemark(masterDtoListEntry.getKey().getRemark());
                    statusResponse.setCompanyId(masterDtoListEntry.getKey().getCompanyId());
                    statusResponse.setCompanyName(masterDtoListEntry.getKey().getCompanyName());
                    statusResponse.setCompanyAddress(masterDtoListEntry.getKey().getCompanyAddress());
                    statusResponse.setCompanyMbNo(masterDtoListEntry.getKey().getCompanyMbNo());
                    statusResponse.setCompanyFinYear(masterDtoListEntry.getKey().getCompanyFinYear());

                    statusResponse.setKppStatusDetails(masterDtoListEntry.getValue());
                    empKppStatusResponses.add(statusResponse);
                }
            } else {

                return null;
            }
            return statusResponse;
        } catch (Exception ex) {
            log.error("Inside EmployeeKppStatusServiceImpl >> getCompletedEmployeeKppStatus() : {}", ex);
            throw new KPIException("EmployeeKppStatusServiceImpl >> getCompletedEmployeeKppStatus()", false, ex.getMessage());
        }
    }
}
