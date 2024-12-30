package com.futurebizops.kpi.service.serviceimpl;

import com.futurebizops.kpi.constants.KPIConstants;
import com.futurebizops.kpi.entity.RegionEntity;
import com.futurebizops.kpi.entity.RoleAudit;
import com.futurebizops.kpi.entity.RoleEntity;
import com.futurebizops.kpi.entity.UoMAudit;
import com.futurebizops.kpi.entity.UoMEntity;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.repository.UoMAuditRepo;
import com.futurebizops.kpi.repository.UoMRepo;
import com.futurebizops.kpi.request.RoleCreateRequest;
import com.futurebizops.kpi.request.RoleUpdateRequest;
import com.futurebizops.kpi.request.UoMCreateRequest;
import com.futurebizops.kpi.request.UoMUpdateRequest;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.RegionResponse;
import com.futurebizops.kpi.response.RoleResponse;
import com.futurebizops.kpi.response.UoMResponse;
import com.futurebizops.kpi.service.UoMService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UoMServiceImpl implements UoMService {

    @Autowired
    UoMRepo uoMRepo;

    @Autowired
    UoMAuditRepo uoMAuditRepo;

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public KPIResponse saveUoM(UoMCreateRequest uoMCreateRequest) {
        Optional<UoMEntity> optionalUoMEntity = uoMRepo.findByUomNameEqualsIgnoreCase(uoMCreateRequest.getUomName());
        if(optionalUoMEntity.isPresent()){
            log.error("Inside UoMServiceImpl >> saveUoM()");
            return KPIResponse.builder()
                    .isSuccess(false)
                    .responseMessage("UoM name already exist")
                    .build();
        }

        UoMEntity uoMEntity = convertUoMCreateRequestToEntity(uoMCreateRequest);
        try {
            uoMRepo.save(uoMEntity);
            UoMAudit uoMAudit = new UoMAudit(uoMEntity);
            uoMAuditRepo.save(uoMAudit);
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_SUCCESS)
                    .build();
        } catch (Exception ex) {
            log.error("Inside UoMServiceImpl >> saveUoM()");
            throw new KPIException("UoMServiceImpl", false, ex.getMessage());
        }

    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public KPIResponse updateUoM(UoMUpdateRequest uoMUpdateRequest) {
        try {
        Optional<UoMEntity> optionalUoMEntity = uoMRepo.findById(uoMUpdateRequest.getUomId());
        if(optionalUoMEntity.isPresent()){
            UoMEntity uoMEntity = optionalUoMEntity.get();
            uoMEntity.setUomName(uoMUpdateRequest.getUomName());
            uoMEntity.setRemark(uoMUpdateRequest.getRemark());
            uoMEntity.setUpdatedUserId(uoMUpdateRequest.getEmployeeId());

            uoMRepo.save(uoMEntity);
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_UPDATE)
                    .build();
        }

        } catch (Exception ex) {
            log.error("Inside UoMServiceImpl >> updateUoM()");
            throw new KPIException("UoMServiceImpl", false, ex.getMessage());
        }
        return KPIResponse.builder()
                .isSuccess(false)
                .responseMessage("Record not found")
                .build();

    }

    @Transactional
    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public KPIResponse deleteUOMDetails(Integer uomId) {
        KPIResponse busPassResponse = new KPIResponse();
        try {
            uoMRepo.deleteUOMDetails(uomId);
            busPassResponse.setSuccess(true);
            busPassResponse.setResponseMessage("UOM details deleted Successfully");
            return busPassResponse;
        } catch (Exception ex) {
            log.error("Inside UoMServiceImpl >> deleteUOMDetails() : {}",ex);
            return KPIResponse.builder()
                    .isSuccess(false)
                    .build();
        }

    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public KPIResponse findUoMDetails(Integer uomId, String uomName, String statusCd, Pageable requestPageable) {
        String sortName = null;
        // String sortDirection = null;
        Integer pageSize = requestPageable.getPageSize();
        Integer pageOffset = (int) requestPageable.getOffset();

        Optional<Sort.Order> order = requestPageable.getSort().get().findFirst();
        if (order.isPresent()) {
            sortName = order.get().getProperty();  //order by this field
            // sortDirection = order.get().getDirection().toString(); // Sort ASC or DESC
        }

        Integer totalCount = uoMRepo.getUoMCount(uomId, uomName, statusCd);
        List<Object[]> uomData = uoMRepo.getUoMDetails(uomId, uomName, statusCd, sortName, pageSize, pageOffset);

        List<UoMResponse> uomResponses = uomData.stream().map(UoMResponse::new).collect(Collectors.toList());

        return KPIResponse.builder()
                .isSuccess(true)
                .responseData(new PageImpl(uomResponses, requestPageable, totalCount))
                .responseMessage(KPIConstants.RECORD_FETCH)
                .build();
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public KPIResponse findUoMDetails(Integer uomId) {
        UoMResponse uoMResponse = null;
        KPIResponse kpiResponse = null;
        Optional<UoMEntity> optionalRegionEntity = uoMRepo.findById(uomId);
        if (optionalRegionEntity.isPresent()) {
            UoMEntity uoMEntity = optionalRegionEntity.get();
            uoMResponse = UoMResponse.builder()
                    .uomId(uoMEntity.getUomId())
                    .uomName(uoMEntity.getUomName())
                    .remark(uoMEntity.getRemark())
                    .build();
            kpiResponse = KPIResponse.builder()
                    .isSuccess(true)
                    .responseData(uoMResponse)
                    .responseMessage(KPIConstants.RECORD_FETCH)
                    .build();
        } else {
            kpiResponse = KPIResponse.builder()
                    .isSuccess(false)
                    .responseMessage("Record not found")
                    .build();
        }
        return kpiResponse;
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public List<UoMEntity> findAllUoMDetails() {
        try {
            List<UoMEntity> uoMEntities = uoMRepo.findAll();
            if (uoMEntities.size() > 0) {
                return uoMEntities;
            }
        } catch(Exception ex){
        log.error("Inside UoMServiceImpl >> updateUoM():{}", ex.getMessage());
        throw new KPIException("UoMServiceImpl", false, "UOM is not set");
        }
        log.error("Inside UoMServiceImpl >> updateUoM()");
        throw new KPIException("UoMServiceImpl", false, "UOM is not set");
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public RoleResponse findUoMById(Integer roleId) {
        return null;
    }



    private UoMEntity convertUoMCreateRequestToEntity(UoMCreateRequest uoMCreateRequest) {
        UoMEntity uoMEntity = new UoMEntity();
        uoMEntity.setUomName(uoMCreateRequest.getUomName());
        uoMEntity.setRemark(uoMCreateRequest.getRemark());
        uoMEntity.setStatusCd(uoMCreateRequest.getStatusCd());
        uoMEntity.setCreatedUserId(uoMCreateRequest.getEmployeeId());
        return uoMEntity;
    }

    private UoMEntity convertUoMUpdateRequestToEntity(UoMUpdateRequest uoMUpdateRequest) {
        UoMEntity uoMEntity = new UoMEntity();
        uoMEntity.setUomId(uoMUpdateRequest.getUomId());
        uoMEntity.setUomName(uoMUpdateRequest.getUomName());
        uoMEntity.setRemark(uoMUpdateRequest.getRemark());
        uoMEntity.setStatusCd(uoMUpdateRequest.getStatusCd());
        uoMEntity.setUpdatedUserId(uoMUpdateRequest.getEmployeeId());
        return uoMEntity;
    }
}
