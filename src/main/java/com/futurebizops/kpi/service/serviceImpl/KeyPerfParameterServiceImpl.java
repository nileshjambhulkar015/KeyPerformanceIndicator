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
import com.futurebizops.kpi.response.DesignationReponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.KPPResponse;
import com.futurebizops.kpi.service.KeyPerfParameterService;
import com.futurebizops.kpi.utils.KPIUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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
    public KPIResponse findKeyPerfomanceParameterDetails(Integer kppId, Integer roleId, Integer deptId, Integer desigId, String kppObjective, String statusCd, Pageable pageable) {
        String sortName = null;
        String sortDirection = null;
        Integer pageSize = pageable.getPageSize();
        Integer pageOffset = (int) pageable.getOffset();
        // pageable = KPIUtils.sort(requestPageable, sortParam, pageDirection);
        Optional<Sort.Order> order = pageable.getSort().get().findFirst();
        if (order.isPresent()) {
            sortName = order.get().getProperty();  //order by this field
            sortDirection = order.get().getDirection().toString(); // Sort ASC or DESC
        }

        Integer totalCount = keyPerfParameterRepo.getKeyPerfParameterCount(kppId, roleId, deptId, desigId, kppObjective, statusCd);
        List<Object[]> designationData = keyPerfParameterRepo.getKeyPerfParameterDetail(kppId, roleId, deptId, desigId, kppObjective, statusCd, sortName, pageSize, pageOffset);

        List<KPPResponse> kppResponses = designationData.stream().map(KPPResponse::new).collect(Collectors.toList());
        kppResponses = kppResponses.stream()
                .sorted(Comparator.comparing(KPPResponse::getDeptName))
                .collect(Collectors.toList());

        return KPIResponse.builder()
                .isSuccess(true)
                .responseData(new PageImpl(kppResponses, pageable, totalCount))
                .responseMessage(KPIConstants.RECORD_FETCH)
                .build();
    }

    @Override
    public KPPResponse findKeyPerfomanceParameterDetailById(Integer kppId) {
        List<Object[]> designationData = keyPerfParameterRepo.getKeyPerfParameterDetailById(kppId);

        List<KPPResponse> kppResponses = designationData.stream().map(KPPResponse::new).collect(Collectors.toList());

        return kppResponses.get(0);
    }


    @Override
    public List<KPPResponse>  getKeyPerfomanceParameter(Integer deptId, Integer desigId, String statusCd) {
        List<KeyPerfParamEntity> keyPerfParamEntities = keyPerfParameterRepo.findByDeptIdAndDesigIdAndStatusCd(deptId, desigId, statusCd);
        List<KPPResponse> kppResponses = convertEntityListToResponse(keyPerfParamEntities);
        return kppResponses;
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
                .roleId(keyPerfParamCreateRequest.getRoleId())
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
                .roleId(keyPerfParamUpdateRequest.getRoleId())
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