package com.futurebizops.kpi.service.serviceImpl;

import com.futurebizops.kpi.constants.KPIConstants;
import com.futurebizops.kpi.entity.KeyPerfParamAudit;
import com.futurebizops.kpi.entity.KeyPerfParamEntity;
import com.futurebizops.kpi.enums.KeyPerfParamSearchEnum;
import com.futurebizops.kpi.enums.StatusCdEnum;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.repository.KeyPerfParameterAuditRepo;
import com.futurebizops.kpi.repository.KeyPerfParameterRepo;
import com.futurebizops.kpi.request.KeyPerfParamCreateRequest;
import com.futurebizops.kpi.request.KeyPerfParamUpdateRequest;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.KPPResponse;
import com.futurebizops.kpi.service.KeyPerfParameterService;
import com.futurebizops.kpi.utils.KPIUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class KeyPerfParameterServiceImpl implements KeyPerfParameterService {

    @Autowired
    private KeyPerfParameterRepo keyPerfParameterRepo;

    @Autowired
    private KeyPerfParameterAuditRepo keyPerfParameterAuditRepo;

    @Override
    public KPIResponse saveKeyPerfomanceParameter(KeyPerfParamCreateRequest keyPerfParamCreateRequest) {

        KeyPerfParamEntity keyPerfParamEntity = convertKeyPerfParamCreateRequestToEntity(keyPerfParamCreateRequest);
        try {
            keyPerfParameterRepo.save(keyPerfParamEntity);
            KeyPerfParamAudit keyPerfParamAudit = new KeyPerfParamAudit(keyPerfParamEntity);
            keyPerfParameterAuditRepo.save(keyPerfParamAudit);
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_SUCCESS)
                    .build();
        } catch (Exception ex) {
            log.error("Inside KeyPerfParameterServiceImpl >> saveKeyPerfomanceParameter()");
            throw new KPIException("KeyPerfParameterServiceImpl", false, ex.getMessage());
        }
    }

    @Override
    public KPIResponse updateKeyPerfomanceParameter(KeyPerfParamUpdateRequest ratingRatioUpdateRequest) {
        KeyPerfParamEntity keyPerfParamEntity = convertKeyPerfParamUpdateRequestToEntity(ratingRatioUpdateRequest);
        try {
            keyPerfParameterRepo.save(keyPerfParamEntity);
            KeyPerfParamAudit departmentAudit = new KeyPerfParamAudit(keyPerfParamEntity);
            keyPerfParameterAuditRepo.save(departmentAudit);
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_UPDATE)
                    .build();
        } catch (Exception ex) {
            log.error("Inside KeyPerfParameterServiceImpl >> updateKeyPerfomanceParameter()");
            throw new KPIException("KeyPerfParameterServiceImpl", false, ex.getMessage());
        }
    }

    @Override
    public KPIResponse findKeyPerfomanceParameterDetails(KeyPerfParamSearchEnum searchEnum, String searchString, StatusCdEnum statusCdEnum, Pageable requestPageable, String sortParam, String pageDirection) {
        Page<KeyPerfParamEntity> keyPerfParamEntities = null;
        Pageable pageable = KPIUtils.sort(requestPageable, sortParam, pageDirection);
        switch (searchEnum.getSearchType()) {
            case "BY_STATUS":
                keyPerfParamEntities = keyPerfParameterRepo.findByStatusCd(statusCdEnum.getSearchType(), pageable);
                break;
            case "ALL":
            default:
                keyPerfParamEntities = keyPerfParameterRepo.findAll(pageable);
        }
        return KPIResponse.builder()
                .isSuccess(true)
                .responseData(keyPerfParamEntities)
                .responseMessage(KPIConstants.RECORD_FETCH)
                .build();
    }

    @Override
    public KPIResponse getKeyPerfomanceParameter(Integer deptId, Integer desigId, String statusCd) {
        List<KeyPerfParamEntity> keyPerfParamEntities = keyPerfParameterRepo.findByDeptIdAndDesigIdAndStatusCd(deptId, desigId, statusCd);
        List<KPPResponse> kppResponses = convertEntityListToResponse(keyPerfParamEntities);
        return KPIResponse.builder()
                .isSuccess(true)
                .responseData(kppResponses)
                .responseMessage(KPIConstants.RECORD_FETCH)
                .build();
    }

    private List<KPPResponse> convertEntityListToResponse(List<KeyPerfParamEntity> keyPerfParamEntities) {
        List<KPPResponse> kppResponseList =
                keyPerfParamEntities.stream()
                        .map(keyPerfParamEntity -> {
                            KPPResponse kppResponse = KPPResponse.builder()
                                    .kppId(keyPerfParamEntity.getKppId())
                                    .kppObjective(keyPerfParamEntity.getKppObjective())
                                    .kppPerformanceIndi(keyPerfParamEntity.getKppPerformanceIndi())
                                    .kppOverallTarget(keyPerfParamEntity.getKppOverallTarget())
                                    .kppTargetPeriod(keyPerfParamEntity.getKppTargetPeriod())
                                    .kppUoM(keyPerfParamEntity.getKppUoM())
                                    .kppOverallWeightage(keyPerfParamEntity.getKppOverallWeightage())
                                    .kppRating1(keyPerfParamEntity.getKppRating1())
                                    .kppRating2(keyPerfParamEntity.getKppRating2())
                                    .kppRating3(keyPerfParamEntity.getKppRating3())
                                    .kppRating4(keyPerfParamEntity.getKppRating4())
                                    .kppRating5(keyPerfParamEntity.getKppRating5())
                                    .build();
return  kppResponse;
                        })
                        .collect(Collectors.toList());
        return kppResponseList;

    }

    private KeyPerfParamEntity convertKeyPerfParamCreateRequestToEntity(KeyPerfParamCreateRequest keyPerfParamCreateRequest) {
        return KeyPerfParamEntity.keyPerfParamEntityBuilder()
                .deptId(keyPerfParamCreateRequest.getDeptId())
                .desigId(keyPerfParamCreateRequest.getDesigId())
                .kppObjective(keyPerfParamCreateRequest.getKppObjective())
                .kppPerformanceIndi(keyPerfParamCreateRequest.getKppPerformanceIndi())
                .kppOverallTarget(keyPerfParamCreateRequest.getKppOverallTarget())
                .kppTargetPeriod(keyPerfParamCreateRequest.getKppTargetPeriod())
                .kppUoM(keyPerfParamCreateRequest.getKppUoM())
                .kppOverallWeightage(keyPerfParamCreateRequest.getKppOverallWeightage())
                .kppRating1(keyPerfParamCreateRequest.getKppRating1())
                .kppRating2(keyPerfParamCreateRequest.getKppRating2())
                .kppRating3(keyPerfParamCreateRequest.getKppRating3())
                .kppRating4(keyPerfParamCreateRequest.getKppRating4())
                .kppRating5(keyPerfParamCreateRequest.getKppRating5())
                .kppDescription(keyPerfParamCreateRequest.getRrDescription())
                .remark(keyPerfParamCreateRequest.getRemark())
                .statusCd(keyPerfParamCreateRequest.getStatusCd())
                .createdUserId(keyPerfParamCreateRequest.getEmployeeId())
                .build();
    }

    private KeyPerfParamEntity convertKeyPerfParamUpdateRequestToEntity(KeyPerfParamUpdateRequest keyPerfParamUpdateRequest) {
        return KeyPerfParamEntity.keyPerfParamEntityBuilder()
                .kppId(keyPerfParamUpdateRequest.getKppId())
                .deptId(keyPerfParamUpdateRequest.getDeptId())
                .desigId(keyPerfParamUpdateRequest.getDesigId())
                .kppObjective(keyPerfParamUpdateRequest.getKppObjective())
                .kppPerformanceIndi(keyPerfParamUpdateRequest.getKppPerformanceIndi())
                .kppOverallTarget(keyPerfParamUpdateRequest.getKppOverallTarget())
                .kppTargetPeriod(keyPerfParamUpdateRequest.getKppTargetPeriod())
                .kppUoM(keyPerfParamUpdateRequest.getKppUoM())
                .kppOverallWeightage(keyPerfParamUpdateRequest.getKppOverallWeightage())
                .kppRating1(keyPerfParamUpdateRequest.getKppRating1())
                .kppRating2(keyPerfParamUpdateRequest.getKppRating2())
                .kppRating3(keyPerfParamUpdateRequest.getKppRating3())
                .kppRating4(keyPerfParamUpdateRequest.getKppRating4())
                .kppRating5(keyPerfParamUpdateRequest.getKppRating5())
                .kppDescription(keyPerfParamUpdateRequest.getRrDescription())
                .remark(keyPerfParamUpdateRequest.getRemark())
                .statusCd(keyPerfParamUpdateRequest.getStatusCd())
                .createdUserId(keyPerfParamUpdateRequest.getEmployeeId())
                .build();
    }
}