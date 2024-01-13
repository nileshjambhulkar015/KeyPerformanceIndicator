package com.futurebizops.kpi.service.serviceimpl;

import com.futurebizops.kpi.constants.KPIConstants;
import com.futurebizops.kpi.entity.KeyPerfParamAudit;
import com.futurebizops.kpi.entity.KeyPerfParamEntity;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.repository.KeyPerfParameterAuditRepo;
import com.futurebizops.kpi.repository.KeyPerfParameterRepo;
import com.futurebizops.kpi.request.KeyPerfParamCreateRequest;
import com.futurebizops.kpi.request.KeyPerfParamUpdateRequest;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.KPPResponse;
import com.futurebizops.kpi.service.KeyPerfParameterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
        //String sortDirection = null;
        Integer pageSize = pageable.getPageSize();
        Integer pageOffset = (int) pageable.getOffset();
        // pageable = KPIUtils.sort(requestPageable, sortParam, pageDirection);
        Optional<Sort.Order> order = pageable.getSort().get().findFirst();
        if (order.isPresent()) {
            sortName = order.get().getProperty();  //order by this field
          //  sortDirection = order.get().getDirection().toString(); // Sort ASC or DESC
        }

        Integer totalCount = keyPerfParameterRepo.getKeyPerfParameterCount(kppId, roleId, deptId, desigId, kppObjective, statusCd);
        List<Object[]> designationData = keyPerfParameterRepo.getKeyPerfParameterDetail(kppId, roleId, deptId, desigId, kppObjective, statusCd, sortName, pageSize, pageOffset);

        List<KPPResponse> kppResponses = designationData.stream().map(KPPResponse::new).collect(Collectors.toList());
        kppResponses = kppResponses.stream()
                .sorted(Comparator.comparing(KPPResponse::getDeptName))
                .collect(Collectors.toList());

        return KPIResponse.builder()
                .isSuccess(true)
                .responseData(new PageImpl<>(kppResponses, pageable, totalCount))
                .responseMessage(KPIConstants.RECORD_FETCH)
                .build();
    }

    @Override
    public KPPResponse findKeyPerfomanceParameterDetailById(Integer kppId) {
        try {
            List<Object[]> designationData = keyPerfParameterRepo.getKeyPerfParameterDetailById(kppId);

            List<KPPResponse> kppResponses = designationData.stream().map(KPPResponse::new).collect(Collectors.toList());

            return kppResponses.get(0);
        } catch (Exception ex) {
            log.error("Inside KeyPerfParameterServiceImpl >> findKeyPerfomanceParameterDetailById()");
            throw new KPIException("KeyPerfParameterServiceImpl class", false, ex.getMessage());
        }
    }

    private KeyPerfParamEntity convertKeyPerfParamCreateRequestToEntity(KeyPerfParamCreateRequest keyPerfParamCreateRequest) {
        KeyPerfParamEntity keyPerfParamEntity = new KeyPerfParamEntity();
        keyPerfParamEntity.setRoleId(keyPerfParamCreateRequest.getRoleId());
        keyPerfParamEntity.setDeptId(keyPerfParamCreateRequest.getDeptId());
        keyPerfParamEntity.setDesigId(keyPerfParamCreateRequest.getDesigId());
        keyPerfParamEntity.setKppObjective(keyPerfParamCreateRequest.getKppObjective());
        keyPerfParamEntity.setKppPerformanceIndi(keyPerfParamCreateRequest.getKppPerformanceIndi());
        keyPerfParamEntity.setKppOverallTarget(keyPerfParamCreateRequest.getKppOverallTarget());
        keyPerfParamEntity.setKppTargetPeriod(keyPerfParamCreateRequest.getKppTargetPeriod());
        keyPerfParamEntity.setKppUoM(keyPerfParamCreateRequest.getKppUoM());
        keyPerfParamEntity.setKppOverallWeightage(keyPerfParamCreateRequest.getKppOverallWeightage());
        keyPerfParamEntity.setKppRating1(keyPerfParamCreateRequest.getKppRating1());
        keyPerfParamEntity.setKppRating2(keyPerfParamCreateRequest.getKppRating2());
        keyPerfParamEntity.setKppRating3(keyPerfParamCreateRequest.getKppRating3());
        keyPerfParamEntity.setKppRating4(keyPerfParamCreateRequest.getKppRating4());
        keyPerfParamEntity.setKppRating5(keyPerfParamCreateRequest.getKppRating5());

        keyPerfParamEntity.setRemark(keyPerfParamCreateRequest.getRemark());
        keyPerfParamEntity.setStatusCd(keyPerfParamCreateRequest.getStatusCd());
        keyPerfParamEntity.setCreatedUserId(keyPerfParamCreateRequest.getEmployeeId());
        return keyPerfParamEntity;
    }

    private KeyPerfParamEntity convertKeyPerfParamUpdateRequestToEntity(KeyPerfParamUpdateRequest keyPerfParamUpdateRequest) {
        KeyPerfParamEntity keyPerfParamEntity = new KeyPerfParamEntity();
        keyPerfParamEntity.setKppId(keyPerfParamUpdateRequest.getKppId());
        keyPerfParamEntity.setRoleId(keyPerfParamUpdateRequest.getRoleId());
        keyPerfParamEntity.setDeptId(keyPerfParamUpdateRequest.getDeptId());
        keyPerfParamEntity.setDesigId(keyPerfParamUpdateRequest.getDesigId());
        keyPerfParamEntity.setKppObjective(keyPerfParamUpdateRequest.getKppObjective());
        keyPerfParamEntity.setKppPerformanceIndi(keyPerfParamUpdateRequest.getKppPerformanceIndi());
        keyPerfParamEntity.setKppOverallTarget(keyPerfParamUpdateRequest.getKppOverallTarget());
        keyPerfParamEntity.setKppTargetPeriod(keyPerfParamUpdateRequest.getKppTargetPeriod());
        keyPerfParamEntity.setKppUoM(keyPerfParamUpdateRequest.getKppUoM());
        keyPerfParamEntity.setKppOverallWeightage(keyPerfParamUpdateRequest.getKppOverallWeightage());
        keyPerfParamEntity.setKppRating1(keyPerfParamUpdateRequest.getKppRating1());
        keyPerfParamEntity.setKppRating2(keyPerfParamUpdateRequest.getKppRating2());
        keyPerfParamEntity.setKppRating3(keyPerfParamUpdateRequest.getKppRating3());
        keyPerfParamEntity.setKppRating4(keyPerfParamUpdateRequest.getKppRating4());
        keyPerfParamEntity.setKppRating5(keyPerfParamUpdateRequest.getKppRating5());

        keyPerfParamEntity.setRemark(keyPerfParamUpdateRequest.getRemark());
        keyPerfParamEntity.setStatusCd(keyPerfParamUpdateRequest.getStatusCd());
        keyPerfParamEntity.setUpdatedUserId(keyPerfParamUpdateRequest.getEmployeeId());
        return keyPerfParamEntity;
    }
}