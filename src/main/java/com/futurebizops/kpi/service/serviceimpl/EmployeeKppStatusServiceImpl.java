package com.futurebizops.kpi.service.serviceimpl;

import com.futurebizops.kpi.dto.EmployeeKppDetailsDto;
import com.futurebizops.kpi.dto.EmployeeKppMasterDto;
import com.futurebizops.kpi.dto.EmployeeKppStatusDto;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.repository.EmployeeKppMasterRepo;
import com.futurebizops.kpi.repository.ReportEmployeeKppMasterRepo;
import com.futurebizops.kpi.response.EmpKppStatusResponse;
import com.futurebizops.kpi.service.EmployeeKppStatusService;
import com.futurebizops.kpi.utils.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmployeeKppStatusServiceImpl implements EmployeeKppStatusService {

    @Autowired
    private EmployeeKppMasterRepo keyPerfParameterRepo;

    @Autowired
    private ReportEmployeeKppMasterRepo reportEmployeeKppMasterRepo;


    @Override
    public EmpKppStatusResponse getInPrgressEmployeeKppStatus(Integer empId) {
        List<EmpKppStatusResponse> empKppStatusResponses = new ArrayList<>();
        EmpKppStatusResponse statusResponse = null;
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
            log.error("EmployeeKppStatusServiceImpl >> getEmployeeKppStatus()  ");
            throw new KPIException("DepartmentServiceImpl", false, "No record found");
        }
        return statusResponse;
    }


    @Override
    public EmpKppStatusResponse getEmployeeKppDataYearly(Integer empId) {
        List<EmpKppStatusResponse> empKppStatusResponses = new ArrayList<>();
        EmpKppStatusResponse statusResponse = null;
        Double totalEmpAchivedWeight=0.0;
        Double totalEmpOverallAchieve=0.0;
        Double totalEmpOverallTaskComp=0.0;

        Double totalHodAchivedWeight=0.0;
        Double totalHodOverallAchieve=0.0;
        Double totalHodOverallTaskComp=0.0;

        Double totalGmAchivedWeight=0.0;
        Double totalGmOverallAchieve=0.0;
        Double totalGmOverallTaskComp=0.0;

        List<Object[]> employeeKppData = reportEmployeeKppMasterRepo.getEmployeeKppDataYearly(empId);
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

                totalEmpAchivedWeight+= Double.parseDouble(masterDtoListEntry.getKey().getTotalEmpAchivedWeight());
                totalEmpOverallAchieve+= Double.parseDouble(masterDtoListEntry.getKey().getTotalEmpOverallAchieve());
                totalEmpOverallTaskComp+= Double.parseDouble(masterDtoListEntry.getKey().getTotalEmpOverallTaskComp());

                statusResponse.setTotalOverallRatings(masterDtoListEntry.getKey().getTotalOverallRatings());
                statusResponse.setTotalOverallPercentage(masterDtoListEntry.getKey().getTotalOverallPercentage());
                //statusResponse.setEmpKppAppliedDate(masterDtoListEntry.getKey().getEmpKppAppliedDate());
                statusResponse.setEmpKppStatus(masterDtoListEntry.getKey().getEmpKppStatus());
                statusResponse.setEmpRemark(masterDtoListEntry.getKey().getEmpRemark());
                statusResponse.setHodEmpId(masterDtoListEntry.getKey().getHodEmpId());

                 totalHodAchivedWeight+=Double.parseDouble(masterDtoListEntry.getKey().getTotalHodAchivedWeight());
                 totalHodOverallAchieve+=Double.parseDouble(masterDtoListEntry.getKey().getTotalHodOverallAchieve());;
                 totalHodOverallTaskComp+=Double.parseDouble(masterDtoListEntry.getKey().getTotalHodOverallTaskComp());;

                //  statusResponse.setHodKppAppliedDate(masterDtoListEntry.getKey().getHodKppAppliedDate());
                statusResponse.setHodKppStatus(masterDtoListEntry.getKey().getHodKppStatus());
                statusResponse.setHodRemark(masterDtoListEntry.getKey().getHodRemark());
                statusResponse.setGmEmpId(masterDtoListEntry.getKey().getGmEmpId());

                totalGmAchivedWeight+=Double.parseDouble(masterDtoListEntry.getKey().getTotalGmAchivedWeight());
                 totalGmOverallAchieve+=Double.parseDouble(masterDtoListEntry.getKey().getTotalGmOverallAchieve());
                 totalGmOverallTaskComp+=Double.parseDouble(masterDtoListEntry.getKey().getTotalGmOverallTaskComp());

                // statusResponse.setGmKppAppliedDate(masterDtoListEntry.getKey().getGmKppAppliedDate());
                statusResponse.setGmKppStatus(masterDtoListEntry.getKey().getGmKppStatus());
                statusResponse.setGmRemark(masterDtoListEntry.getKey().getGmRemark());
                statusResponse.setRemark(masterDtoListEntry.getKey().getRemark());
                statusResponse.setCompanyId(masterDtoListEntry.getKey().getCompanyId());
                statusResponse.setCompanyName(masterDtoListEntry.getKey().getCompanyName());
                statusResponse.setCompanyAddress(masterDtoListEntry.getKey().getCompanyAddress());
                statusResponse.setCompanyMbNo(masterDtoListEntry.getKey().getCompanyMbNo());
                statusResponse.setCompanyFinYear(masterDtoListEntry.getKey().getCompanyFinYear());

                // statusResponse.setKppStatusDetails(masterDtoListEntry.getValue());

                List<EmployeeKppDetailsDto> employeeKppDetailsDtos = new ArrayList<>();

                employeeKppDetailsDtos = masterDtoListEntry.getValue().stream().collect(Collectors.groupingBy(EmployeeKppDetailsDto::getKppId,
                        Collectors.collectingAndThen(Collectors.toList(),
                                data -> {
                                    double empAchivedWeight = data.stream().mapToDouble(test -> Double.parseDouble(test.getEmpAchivedWeight())).sum();
                                    double empOverallAchieve = data.stream().mapToDouble(test -> Double.parseDouble(test.getEmpOverallAchieve())).sum();
                                    double empOverallTaskComp = data.stream().mapToDouble(test -> Double.parseDouble(test.getEmpOverallTaskComp())).sum();

                                    double hodAchivedWeight = data.stream().mapToDouble(test -> Double.parseDouble(test.getHodAchivedWeight())).sum();
                                    double hodOverallAchieve = data.stream().mapToDouble(test -> Double.parseDouble(test.getHodOverallAchieve())).sum();
                                    double hodOverallTaskComp = data.stream().mapToDouble(test -> Double.parseDouble(test.getHodOverallTaskComp())).sum();

                                    double gmAchivedWeight = data.stream().mapToDouble(test -> Double.parseDouble(test.getGmAchivedWeight())).sum();
                                    double gmOverallAchieve = data.stream().mapToDouble(test -> Double.parseDouble(test.getGmOverallAchieve())).sum();
                                    double gmOverallTaskComp = data.stream().mapToDouble(test -> Double.parseDouble(test.getGmOverallTaskComp())).sum();

                                    return new EmployeeKppDetailsDto(data.iterator().next().getEkppId(), data.iterator().next().getKppId(), String.valueOf(empAchivedWeight), String.valueOf(empOverallAchieve), String.valueOf(empOverallTaskComp), data.iterator().next().getHodEmployeeId(), String.valueOf(hodAchivedWeight), String.valueOf(hodOverallAchieve), String.valueOf(hodOverallTaskComp),
                                            data.iterator().next().getGmEmployeeId(), String.valueOf(gmAchivedWeight), String.valueOf(gmOverallAchieve), String.valueOf(gmOverallTaskComp), data.iterator().next().getOverallRatings(), data.iterator().next().getOverallPercentage(), data.iterator().next().getKppObjective(), data.iterator().next().getKppPerformanceIndi(), data.iterator().next().getKppOverallTarget(), data.iterator().next().getKppTargetPeriod(), data.iterator().next().getUomId(), data.iterator().next().getUomName(),
                                            data.iterator().next().getKppOverallWeightage(), data.iterator().next().getKppRating1(), data.iterator().next().getKppRating2(), data.iterator().next().getKppRating3(), data.iterator().next().getKppRating4(), data.iterator().next().getKppRating5(), data.iterator().next().getEkppStatus());
                                })
                )).values().stream().collect(Collectors.toList());

                statusResponse.setKppStatusDetails(employeeKppDetailsDtos);
            }
        } else {
            log.error("EmployeeKppStatusServiceImpl >> getEmployeeKppStatus()  ");
            throw new KPIException("DepartmentServiceImpl", false, "No record found");
        }

        statusResponse.setTotalEmpAchivedWeight(totalEmpAchivedWeight.toString());
        statusResponse.setTotalEmpOverallAchieve(totalEmpOverallAchieve.toString());
        statusResponse.setTotalEmpOverallTaskComp(totalEmpOverallTaskComp.toString());

        statusResponse.setTotalHodAchivedWeight(totalHodAchivedWeight.toString());
        statusResponse.setTotalHodOverallAchieve(totalHodOverallAchieve.toString());
        statusResponse.setTotalHodOverallTaskComp(totalHodOverallTaskComp.toString());

        statusResponse.setTotalGmAchivedWeight(totalGmAchivedWeight.toString());
        statusResponse.setTotalGmOverallAchieve(totalGmOverallAchieve.toString());
        statusResponse.setTotalGmOverallTaskComp(totalGmOverallTaskComp.toString());
        return statusResponse;
    }

    @Override
    public EmpKppStatusResponse getCompletedEmployeeKppStatus(Integer empId, String ekppMonth) {

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
            log.error("EmployeeKppStatusServiceImpl >> getEmployeeKppStatus()  ");
            throw new KPIException("DepartmentServiceImpl", false, "No record found");
        }
        return statusResponse;
    }
}
