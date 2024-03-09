package com.futurebizops.kpi.service.serviceimpl;

import com.futurebizops.kpi.constants.KPIConstants;
import com.futurebizops.kpi.entity.EmployeeEntity;
import com.futurebizops.kpi.entity.EmployeeKppDetailsAudit;
import com.futurebizops.kpi.entity.EmployeeKppDetailsEntity;
import com.futurebizops.kpi.entity.EmployeeKppMasterAudit;
import com.futurebizops.kpi.entity.EmployeeKppMasterEntity;
import com.futurebizops.kpi.entity.KeyPerfParamEntity;
import com.futurebizops.kpi.entity.ReportEmployeeKppDetailsEntity;
import com.futurebizops.kpi.entity.ReportEmployeeKppMasterEntity;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.repository.EmployeeKppDetailsAuditRepo;
import com.futurebizops.kpi.repository.EmployeeKppDetailsRepo;
import com.futurebizops.kpi.repository.EmployeeKppMasterAuditRepo;
import com.futurebizops.kpi.repository.EmployeeKppMasterRepo;
import com.futurebizops.kpi.repository.EmployeeRepo;
import com.futurebizops.kpi.repository.KeyPerfParameterRepo;
import com.futurebizops.kpi.repository.ReportEmployeeKppDetailsRepo;
import com.futurebizops.kpi.repository.ReportEmployeeKppMasterRepo;
import com.futurebizops.kpi.request.EmpKPPMasterUpdateRequest;
import com.futurebizops.kpi.request.EmpKPPUpdateRequest;
import com.futurebizops.kpi.request.EmployeeKeyPerfParamCreateRequest;
import com.futurebizops.kpi.request.GMUpdateDetailsEmpRatingsReq;
import com.futurebizops.kpi.request.GMUpdateMasterEmployeeRatingReq;
import com.futurebizops.kpi.request.HODUpdateDetailsEmpRatingsReq;
import com.futurebizops.kpi.request.HODUpdateMasterEmployeeRatingReq;
import com.futurebizops.kpi.response.HodEmploeeKppResponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.KPPResponse;
import com.futurebizops.kpi.response.dropdown.CompanyDDResponse;
import com.futurebizops.kpi.response.dropdown.RegionDDResponse;
import com.futurebizops.kpi.response.dropdown.SiteDDResponse;
import com.futurebizops.kpi.service.EmployeeKeyPerfParamService;
import com.futurebizops.kpi.utils.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmployeeKeyPerfParamServiceImpl implements EmployeeKeyPerfParamService {


    @Autowired
    private EmployeeKppDetailsAuditRepo keyPerfParamAuditRepo;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private KeyPerfParameterRepo keyPerfParameterRepo;

    @Autowired
    EmployeeKppMasterAuditRepo employeeKeyPerfParamMasterAuditRepo;

    @Autowired
    EmployeeKppMasterRepo employeeKppMasterRepo;

    @Autowired
    EmployeeKppDetailsRepo employeeKppDetailsRepo;

    @Autowired
    ReportEmployeeKppMasterRepo reportKppMasterRepo;

    @Autowired
    ReportEmployeeKppDetailsRepo reportEmployeeKppDetailsRepo;

    @Autowired
    private EmployeeKppMasterRepo employeeKeyPerfParamMasterRepo;

    @Override
    public KPIResponse saveEmployeeKeyPerfParamDetails(EmployeeKeyPerfParamCreateRequest keyPerfParamCreateRequest) {

        //When hod is inserted then gm set to reporing employee id only
        Integer gmEmpId = null;
        //2 is for HOD role
        if (2 == keyPerfParamCreateRequest.getRoleId()) {
            gmEmpId = keyPerfParamCreateRequest.getReportingEmpId();
        } else {
            gmEmpId = getGmEmpId(keyPerfParamCreateRequest.getReportingEmpId());
        }

        EmployeeKppDetailsEntity employeeKppDetailsEntities = convertEmployeeKPPCreateRequestToEntity(keyPerfParamCreateRequest);
        try {
            employeeKppDetailsRepo.save(employeeKppDetailsEntities);
            EmployeeKppDetailsAudit partAudit = new EmployeeKppDetailsAudit(employeeKppDetailsEntities);
            keyPerfParamAuditRepo.save(partAudit);

            Optional<EmployeeKppMasterEntity> employeeKppMasterEntity = employeeKeyPerfParamMasterRepo.findByEmpIdAndStatusCd(keyPerfParamCreateRequest.getEmpId(), "A");
            if (employeeKppMasterEntity.isPresent()) {
                // do nothing
            } else {
                EmployeeKppMasterEntity kppMasterEntity = new EmployeeKppMasterEntity();
                kppMasterEntity.setEmpId(keyPerfParamCreateRequest.getEmpId());
                kppMasterEntity.setEmpEId(keyPerfParamCreateRequest.getEmpEId());
                kppMasterEntity.setRoleId(keyPerfParamCreateRequest.getRoleId());
                kppMasterEntity.setDeptId(keyPerfParamCreateRequest.getDeptId());
                kppMasterEntity.setDesigId(keyPerfParamCreateRequest.getDesigId());
                kppMasterEntity.setEmpTotalAchivedWeight("0");
                kppMasterEntity.setEmpTotalOverallAchieve("0");
                kppMasterEntity.setEmpTotalOverallTaskComp("0");
                kppMasterEntity.setEmpKppStatus("Pending");
                kppMasterEntity.setEmpKppAppliedDate(null);
                kppMasterEntity.setEmpRemark("Ratings added");
                kppMasterEntity.setEmpEvidence(null);
                kppMasterEntity.setHodEmpId(keyPerfParamCreateRequest.getReportingEmpId());
                kppMasterEntity.setHodTotalAchivedWeight("0");
                kppMasterEntity.setHodTotalOverallAchieve("0");
                kppMasterEntity.setHodTotalOverallTaskComp("0");
                kppMasterEntity.setHodKppAppliedDate(null);
                kppMasterEntity.setHodKppStatus("Pending");
                kppMasterEntity.setHodRemark("HoD Ratings added");
                kppMasterEntity.setGmEmpId(gmEmpId);
                kppMasterEntity.setGmKppStatus("Pending");
                kppMasterEntity.setGmTotalAchivedWeight("0");
                kppMasterEntity.setGmTotalOverallAchieve("0");
                kppMasterEntity.setGmTotalOverallTaskComp("0");
                kppMasterEntity.setGmKppAppliedDate(null);
                kppMasterEntity.setGmRemark("GM Ratings added");
                kppMasterEntity.setRemark(null);
                kppMasterEntity.setStatusCd("A");
                kppMasterEntity.setCreatedUserId(keyPerfParamCreateRequest.getEmployeeId());

                employeeKeyPerfParamMasterRepo.save(kppMasterEntity);
                EmployeeKppMasterAudit employeeKeyPerfParamMasterAudit = new EmployeeKppMasterAudit();
                employeeKeyPerfParamMasterAuditRepo.save(employeeKeyPerfParamMasterAudit);
            }
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_SUCCESS)
                    .build();
        } catch (Exception ex) {
            log.error("Inside DepartmentServiceImpl >> saveDepartment()");
            throw new KPIException("DepartmentServiceImpl", false, ex.getMessage());
        }
    }

    @Transactional
    @Override
    public KPIResponse updateEmployeeKeyPerfParamDetails(EmpKPPMasterUpdateRequest empKPPMasterUpdateRequest) {
        Instant ekppMonth = StringUtils.isNotEmpty(empKPPMasterUpdateRequest.getEkppMonth()) ? DateTimeUtils.convertStringToInstant(empKPPMasterUpdateRequest.getEkppMonth()) : Instant.now();
        try {
            for (EmpKPPUpdateRequest paramUpdateRequest : empKPPMasterUpdateRequest.getKppUpdateRequests()) {

                employeeKppDetailsRepo.updateEmployeeKppDetails(paramUpdateRequest.getEmpId(), ekppMonth, paramUpdateRequest.getEmpAchivedWeight(), paramUpdateRequest.getEmpOverallAchieve(), paramUpdateRequest.getEmpOverallTaskComp(), paramUpdateRequest.getKppId(), paramUpdateRequest.getEmpEId(), paramUpdateRequest.getRoleId(), paramUpdateRequest.getDeptId(), paramUpdateRequest.getDesigId());
            }
            employeeKppMasterRepo.updateEmployeeKppMaster(empKPPMasterUpdateRequest.getKppUpdateRequests().get(0).getEmpId(), ekppMonth, empKPPMasterUpdateRequest.getTotalAchivedWeightage(), empKPPMasterUpdateRequest.getTotalOverAllAchive(), empKPPMasterUpdateRequest.getTotalOverallTaskCompleted(), Instant.now(), empKPPMasterUpdateRequest.getEkppStatus(), empKPPMasterUpdateRequest.getEmpRemark(), empKPPMasterUpdateRequest.getEvidence(), empKPPMasterUpdateRequest.getKppUpdateRequests().get(0).getEmpEId(), empKPPMasterUpdateRequest.getKppUpdateRequests().get(0).getRoleId(), empKPPMasterUpdateRequest.getKppUpdateRequests().get(0).getDeptId(), empKPPMasterUpdateRequest.getKppUpdateRequests().get(0).getDesigId());

            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_SUCCESS)
                    .build();
        } catch (Exception ex) {
            log.error("Inside EmployeeKeyPerfParamServiceImpl >> updateEmployeeKeyPerfParamDetails()");
            throw new KPIException("EmployeeKeyPerfParamServiceImpl Class", false, ex.getMessage());
        }
    }

    //when employee fill kpp then coming for hod approval
    @Override
    @Transactional
    public KPIResponse updateHoDApprovalRequest(HODUpdateMasterEmployeeRatingReq empKPPMasterUpdateRequest) {
        String empKppStatus = "In-Progress";
        try {
            for (HODUpdateDetailsEmpRatingsReq paramUpdateRequest : empKPPMasterUpdateRequest.getKppUpdateRequests()) {

                employeeKppDetailsRepo.updateEmpApproveOrRejectHod(paramUpdateRequest.getHodAchivedWeight(), paramUpdateRequest.getHodOverallAchieve(), paramUpdateRequest.getHodOverallTaskComp(), paramUpdateRequest.getKppId(), paramUpdateRequest.getEmpId());
            }
            if ("Approved".equalsIgnoreCase(empKPPMasterUpdateRequest.getHodKppStatus())) {
                empKppStatus = "Approved";
            } else if ("Reject".equalsIgnoreCase(empKPPMasterUpdateRequest.getHodKppStatus())) {
                empKppStatus = "Reject";
            }
            employeeKppMasterRepo.updateEmpKppApproveOrRejectByHod(empKppStatus, empKPPMasterUpdateRequest.getHodTotalAchivedWeight(), empKPPMasterUpdateRequest.getHodTotalOverallAchieve(), empKPPMasterUpdateRequest.getHodTotalOverallTaskComp(), Instant.now(), empKPPMasterUpdateRequest.getHodKppStatus(), empKPPMasterUpdateRequest.getHodRemark(), empKPPMasterUpdateRequest.getKppUpdateRequests().get(0).getEmpId());

            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_SUCCESS)
                    .build();
        } catch (Exception ex) {
            log.error("Inside EmployeeKeyPerfParamServiceImpl >> updateEmployeeKeyPerfParamDetails()");
            throw new KPIException("EmployeeKeyPerfParamServiceImpl Class", false, ex.getMessage());
        }
    }

    @Override
    public KPIResponse updateGMApprovalRequest(GMUpdateMasterEmployeeRatingReq empKPPMasterUpdateRequest) {

        String empKppStatus = "In-Progress";
        try {
            for (GMUpdateDetailsEmpRatingsReq paramUpdateRequest : empKPPMasterUpdateRequest.getKppUpdateRequests()) {
                employeeKppDetailsRepo.updateGMApproveOrRejectHod(paramUpdateRequest.getGmAchivedWeight(), paramUpdateRequest.getGmOverallAchieve(), paramUpdateRequest.getGmOverallTaskComp(), paramUpdateRequest.getKppId(), paramUpdateRequest.getEmpId());
            }
            if ("Approved".equalsIgnoreCase(empKPPMasterUpdateRequest.getGmKppStatus())) {
                empKppStatus = "Approved";
            } else if ("Reject".equalsIgnoreCase(empKPPMasterUpdateRequest.getGmKppStatus())) {
                empKppStatus = "Reject";
            }
            employeeKppMasterRepo.updateGMKppApproveOrRejectByHod(empKppStatus, empKPPMasterUpdateRequest.getGmTotalAchivedWeight(), empKPPMasterUpdateRequest.getGmTotalOverallAchieve(), empKPPMasterUpdateRequest.getGmTotalOverallTaskComp(), Instant.now(), empKPPMasterUpdateRequest.getGmKppStatus(), empKPPMasterUpdateRequest.getGmRemark(), empKPPMasterUpdateRequest.getKppUpdateRequests().get(0).getEmpId());

            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_SUCCESS)
                    .build();
        } catch (Exception ex) {
            log.error("Inside EmployeeKeyPerfParamServiceImpl >> updateEmployeeKeyPerfParamDetails()");
            throw new KPIException("EmployeeKeyPerfParamServiceImpl Class", false, ex.getMessage());
        }
    }

    @Override
    public List<KPPResponse> getKeyPerfomanceParameter(Integer roleId, Integer deptId, Integer desigId, String statusCd) {
       /* try {
            List<KeyPerfParamEntity> keyPerfParamEntities = keyPerfParameterRepo.findByRoleIdAndDeptIdAndDesigIdAndStatusCd(roleId, deptId, desigId, statusCd);
            if(keyPerfParamEntities.size()>0) {
                return convertEntityListToResponse(keyPerfParamEntities);
            }
            else{
                log.error("Inside EmployeeKeyPerfParamServiceImpl >> getKeyPerfomanceParameter()");
                throw new KPIException("EmployeeKeyPerfParamServiceImpl", false, "No data available");
            }
        } catch (Exception ex) {
            log.error("Inside EmployeeKeyPerfParamServiceImpl >> updateGMApprovalRequest()");
            throw new KPIException("EmployeeKeyPerfParamServiceImpl", false, ex.getMessage());
        }*/
        return null;
    }

    @Override
    public List<HodEmploeeKppResponse> getEmployeeForHodRatings(Integer empId, String empEId, String statusCd) {
        List<Object[]> employeeKppData = keyPerfParameterRepo.getEmployeeKeyPerfParameterDetail(empId, empEId, statusCd);
        List<HodEmploeeKppResponse> hodEmployeeResponses = employeeKppData.stream().map(HodEmploeeKppResponse::new).collect(Collectors.toList());
        hodEmployeeResponses = hodEmployeeResponses.stream()
                .sorted(Comparator.comparing(HodEmploeeKppResponse::getKppObjective))
                .collect(Collectors.toList());
        return hodEmployeeResponses;
    }


    @Transactional
    @Override
    public KPIResponse generateEmployeeKppReport(Integer empId, String statusCd) {
        Optional<EmployeeKppMasterEntity> employeeKppMasterEntity = employeeKppMasterRepo.findByEmpIdAndStatusCd(empId, statusCd);
        ReportEmployeeKppMasterEntity kppMaster = new ReportEmployeeKppMasterEntity();

        if (employeeKppMasterEntity.isPresent()) {
            EmployeeKppMasterEntity kppMasterEntity = employeeKppMasterEntity.get();
            kppMaster.setEkppMonth(kppMasterEntity.getEkppMonth());
            kppMaster.setEmpId(kppMasterEntity.getEmpId());
            kppMaster.setEmpEId(kppMasterEntity.getEmpEId());
            kppMaster.setRoleId(kppMasterEntity.getRoleId());
            kppMaster.setDeptId(kppMasterEntity.getRoleId());
            kppMaster.setDesigId(kppMasterEntity.getDesigId());
            kppMaster.setTotalAchivedWeight(kppMasterEntity.getEmpTotalAchivedWeight());
            kppMaster.setTotalOverallAchieve(kppMasterEntity.getEmpTotalOverallAchieve());
            kppMaster.setTotalOverallTaskComp(kppMasterEntity.getEmpTotalOverallTaskComp());
            kppMaster.setEmpKppAppliedDate(kppMasterEntity.getEmpKppAppliedDate());
            kppMaster.setEmpKppStatus(kppMasterEntity.getEmpKppStatus());
            kppMaster.setEmpRemark(kppMasterEntity.getEmpRemark());
            kppMaster.setEmpEvidence(kppMasterEntity.getEmpEvidence());
            kppMaster.setHodEmpId(kppMasterEntity.getHodEmpId());
            kppMaster.setHodAchivedWeight(kppMasterEntity.getHodTotalAchivedWeight());
            kppMaster.setHodOverallAchieve(kppMasterEntity.getHodTotalOverallAchieve());
            kppMaster.setHodOverallTaskComp(kppMasterEntity.getHodTotalOverallTaskComp());
            kppMaster.setHodKppAppliedDate(kppMasterEntity.getHodKppAppliedDate());
            kppMaster.setHodKppStatus(kppMasterEntity.getHodKppStatus());
            kppMaster.setHodRemark(kppMasterEntity.getHodRemark());
            kppMaster.setGmEmpId(kppMasterEntity.getGmEmpId());
            kppMaster.setGmAchivedWeight(kppMasterEntity.getGmTotalAchivedWeight());
            kppMaster.setGmOverallAchieve(kppMasterEntity.getGmTotalOverallAchieve());
            kppMaster.setGmOverallTaskComp(kppMasterEntity.getGmTotalOverallTaskComp());
            kppMaster.setGmKppAppliedDate(kppMasterEntity.getGmKppAppliedDate());
            kppMaster.setGmKppStatus(kppMasterEntity.getGmKppStatus());
            kppMaster.setGmRemark(kppMasterEntity.getGmRemark());
            kppMaster.setRemark(kppMasterEntity.getRemark());
            kppMaster.setStatusCd(kppMasterEntity.getStatusCd());

            reportKppMasterRepo.save(kppMaster);
            employeeKppMasterRepo.resetEmployeeKppByGM(empId, statusCd);
        }

        List<EmployeeKppDetailsEntity> employeeKppDetailsEntities = employeeKppDetailsRepo.findByEmpIdAndStatusCd(empId, statusCd);
        List<ReportEmployeeKppDetailsEntity> kppDetailsEntities = new ArrayList<>();

        ReportEmployeeKppDetailsEntity detailsEntity = null;
        if (employeeKppDetailsEntities.size() > 0) {
            for (EmployeeKppDetailsEntity employeeKppDetailsEntity : employeeKppDetailsEntities) {
                detailsEntity = new ReportEmployeeKppDetailsEntity();

                detailsEntity.setEkppMonth(employeeKppDetailsEntity.getEkppMonth());
                detailsEntity.setEmpId(employeeKppDetailsEntity.getEmpId());
                detailsEntity.setEmpEId(employeeKppDetailsEntity.getEmpEId());
                detailsEntity.setRoleId(employeeKppDetailsEntity.getRoleId());
                detailsEntity.setDeptId(employeeKppDetailsEntity.getRoleId());
                detailsEntity.setDesigId(employeeKppDetailsEntity.getDesigId());
                detailsEntity.setKppId(employeeKppDetailsEntity.getKppId());
                detailsEntity.setEmpAchivedWeight(employeeKppDetailsEntity.getEmpAchivedWeight());
                detailsEntity.setEmpOverallAchieve(employeeKppDetailsEntity.getEmpOverallAchieve());
                detailsEntity.setEmpOverallTaskComp(employeeKppDetailsEntity.getEmpOverallTaskComp());
                detailsEntity.setHodEmpId(employeeKppDetailsEntity.getHodEmpId());
                detailsEntity.setHodAchivedWeight(employeeKppDetailsEntity.getHodAchivedWeight());
                detailsEntity.setHodOverallAchieve(employeeKppDetailsEntity.getHodOverallAchieve());
                detailsEntity.setHodOverallTaskComp(employeeKppDetailsEntity.getHodOverallTaskComp());
                detailsEntity.setGmEmpId(employeeKppDetailsEntity.getGmEmpId());
                detailsEntity.setGmAchivedWeight(employeeKppDetailsEntity.getGmAchivedWeight());
                detailsEntity.setGmOverallAchieve(employeeKppDetailsEntity.getGmOverallAchieve());
                detailsEntity.setGmOverallTaskComp(employeeKppDetailsEntity.getGmOverallTaskComp());
                detailsEntity.setStatusCd(employeeKppDetailsEntity.getStatusCd());

                kppDetailsEntities.add(detailsEntity);
            }
            reportEmployeeKppDetailsRepo.saveAll(kppDetailsEntities);
            employeeKppDetailsRepo.resetEmployeeKpp(empId, statusCd);
        }
        return null;
    }

    @Override
    public List<RegionDDResponse> getDDRegionFromCompany() {
        List<Object[]> regionData = employeeRepo.getDDRegionFromCompany();
        return regionData.stream().map(RegionDDResponse::new).collect(Collectors.toList());
    }

    @Override
    public List<SiteDDResponse> getDDSitesFromComany(Integer regionId) {
        List<Object[]> siteData = employeeRepo.getDDSiteFromCompany(regionId);
        return siteData.stream().map(SiteDDResponse::new).collect(Collectors.toList());
    }

    @Override
    public List<CompanyDDResponse> getDDCompanyFromComany(Integer regionId, Integer siteId) {
        List<Object[]> siteData = employeeRepo.getDDCompanyFromCompany(regionId, siteId);
        return siteData.stream().map(CompanyDDResponse::new).collect(Collectors.toList());
    }

    private List<KPPResponse> convertEntityListToResponse(List<KeyPerfParamEntity> keyPerfParamEntities) {
        return keyPerfParamEntities
                .stream()
                .map(keyPerfParamEntity ->
                        KPPResponse.builder()
                                .kppId(keyPerfParamEntity.getKppId())
                                .roleId(keyPerfParamEntity.getRoleId())
                                .deptId(keyPerfParamEntity.getDeptId())
                                .desigId(keyPerfParamEntity.getDesigId())
                                .kppObjective(keyPerfParamEntity.getKppObjective())
                                .kppPerformanceIndi(keyPerfParamEntity.getKppPerformanceIndi())
                                .kppOverallTarget(keyPerfParamEntity.getKppOverallTarget())
                                .kppTargetPeriod(keyPerfParamEntity.getKppTargetPeriod())
                                .uomId(keyPerfParamEntity.getUomId())
                                .kppOverallWeightage(keyPerfParamEntity.getKppOverallWeightage())
                                .kppRating1(keyPerfParamEntity.getKppRating1())
                                .kppRating2(keyPerfParamEntity.getKppRating2())
                                .kppRating3(keyPerfParamEntity.getKppRating3())
                                .kppRating4(keyPerfParamEntity.getKppRating4())
                                .kppRating5(keyPerfParamEntity.getKppRating5())
                                .build()
                )
                .collect(Collectors.toList());
    }


    private EmployeeKppDetailsEntity convertEmployeeKPPCreateRequestToEntity(EmployeeKeyPerfParamCreateRequest keyPerfParamCreateRequest) {
        //When hod is inserted then gm set to reporing employee id only
        Integer gmEmpId = null;
        //2 is for HOD role
        if (2 == keyPerfParamCreateRequest.getRoleId()) {
            gmEmpId = keyPerfParamCreateRequest.getReportingEmpId();
        } else {
            gmEmpId = getGmEmpId(keyPerfParamCreateRequest.getReportingEmpId());
        }

        EmployeeKppDetailsEntity employeeKppDetailsEntity = new EmployeeKppDetailsEntity();
        employeeKppDetailsEntity.setEkppMonth(keyPerfParamCreateRequest.getEkppMonth());
        employeeKppDetailsEntity.setKppId(keyPerfParamCreateRequest.getKppId());
        employeeKppDetailsEntity.setEmpId(keyPerfParamCreateRequest.getEmpId());

        employeeKppDetailsEntity.setEmpEId(keyPerfParamCreateRequest.getEmpEId());
        employeeKppDetailsEntity.setRoleId(keyPerfParamCreateRequest.getRoleId());
        employeeKppDetailsEntity.setDeptId(keyPerfParamCreateRequest.getDeptId());
        employeeKppDetailsEntity.setDesigId(keyPerfParamCreateRequest.getDesigId());
        employeeKppDetailsEntity.setCreatedUserId(keyPerfParamCreateRequest.getEmployeeId());

        employeeKppDetailsEntity.setEmpAchivedWeight("0");
        employeeKppDetailsEntity.setEmpOverallAchieve("0");
        employeeKppDetailsEntity.setEmpOverallTaskComp("0");
        employeeKppDetailsEntity.setHodEmpId(keyPerfParamCreateRequest.getReportingEmpId());
        employeeKppDetailsEntity.setHodAchivedWeight("0");
        employeeKppDetailsEntity.setHodOverallAchieve("0");
        employeeKppDetailsEntity.setHodOverallTaskComp("0");
        employeeKppDetailsEntity.setGmEmpId(gmEmpId);
        employeeKppDetailsEntity.setGmAchivedWeight("0");
        employeeKppDetailsEntity.setGmOverallAchieve("0");
        employeeKppDetailsEntity.setGmOverallTaskComp("0");
        employeeKppDetailsEntity.setStatusCd("A");
        employeeKppDetailsEntity.setCreatedUserId(keyPerfParamCreateRequest.getEmployeeId());


        return employeeKppDetailsEntity;
    }

    private Integer getGmEmpId(Integer reportinEmpId) {
        Optional<EmployeeEntity> employeeEntity = employeeRepo.findById(reportinEmpId);
        if (employeeEntity.isPresent()) {
            return employeeEntity.get().getReportingEmpId();
        }
        return null;
    }
}
