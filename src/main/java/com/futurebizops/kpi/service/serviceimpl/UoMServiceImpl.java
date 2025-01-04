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
        log.debug("Inside UoMServiceImpl >> saveUoM() uoMCreateRequest: {}", uoMCreateRequest);
        KPIResponse kpiResponse = new KPIResponse();
        Optional<UoMEntity> optionalUoMEntity = uoMRepo.findByUomNameEqualsIgnoreCase(uoMCreateRequest.getUomName());
        log.info("Inside saveUoM() optionalUoMEntity : {}", optionalUoMEntity.isPresent());
        if (optionalUoMEntity.isPresent()) {
            log.error("Inside UoMServiceImpl >> saveUoM() UoM name already exist");
            kpiResponse.setResponseMessage("UoM name already exist");
            kpiResponse.setSuccess(false);
            return kpiResponse;
        }

        UoMEntity uoMEntity = convertUoMCreateRequestToEntity(uoMCreateRequest);
        try {
            uoMRepo.save(uoMEntity);
            UoMAudit uoMAudit = new UoMAudit(uoMEntity);
            uoMAuditRepo.save(uoMAudit);

            kpiResponse.setResponseMessage("UoM added successfully");
            kpiResponse.setSuccess(true);
            return kpiResponse;
        } catch (Exception ex) {
            log.error("Inside UoMServiceImpl >> saveUoM() : {}", ex);
            throw new KPIException("Inside UoMServiceImpl >> saveUoM()", false, ex.getMessage());
        }

    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public KPIResponse updateUoM(UoMUpdateRequest uoMUpdateRequest) {
        log.debug("Inside UoMServiceImpl >> updateUoM() uoMUpdateRequest: {}", uoMUpdateRequest);

        KPIResponse kpiResponse = new KPIResponse();
        try {
            Optional<UoMEntity> optionalUoMEntity = uoMRepo.findById(uoMUpdateRequest.getUomId());
            log.info("Inside saveUoM() optionalUoMEntity : {}", optionalUoMEntity.isPresent());
            if (optionalUoMEntity.isPresent()) {
                UoMEntity uoMEntity = optionalUoMEntity.get();
                uoMEntity.setUomName(uoMUpdateRequest.getUomName());
                uoMEntity.setRemark(uoMUpdateRequest.getRemark());
                uoMEntity.setUpdatedUserId(uoMUpdateRequest.getEmployeeId());

                uoMRepo.save(uoMEntity);
                log.info("UoM updated successfully for id : {}", uoMUpdateRequest.getUomId());
                kpiResponse.setResponseMessage("UoM updated successfully");
                kpiResponse.setSuccess(true);
                return kpiResponse;
            }
        } catch (Exception ex) {
            log.error("Inside UoMServiceImpl >> updateUoM() : {}", ex);
            throw new KPIException("Inside UoMServiceImpl >> updateUoM()", false, ex.getMessage());
        }
        kpiResponse.setResponseMessage("UoM not found");
        kpiResponse.setSuccess(false);
        return kpiResponse;
    }

    @Transactional
    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public KPIResponse deleteUOMDetails(Integer uomId) {
        log.debug("Inside UoMServiceImpl >> deleteUOMDetails() uomId: {}", uomId);
        KPIResponse kpiResponse = new KPIResponse();
        try {
            uoMRepo.deleteUOMDetails(uomId);
            log.info("UOM details deleted Successfully : {}", uomId);
            kpiResponse.setSuccess(true);
            kpiResponse.setResponseMessage("UOM details deleted Successfully");
            return kpiResponse;
        } catch (Exception ex) {
            log.error("Inside UoMServiceImpl >> deleteUOMDetails() : {}", ex);
            throw new KPIException("Inside UoMServiceImpl >> deleteUOMDetails()", false, ex.getMessage());
        }
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public KPIResponse findUoMDetails(Integer uomId, String uomName, String statusCd, Pageable requestPageable) {
        log.debug("Inside UoMServiceImpl >> findUoMDetails() uomId: {}, uomName : {}", uomId, uomName);

        KPIResponse kpiResponse = new KPIResponse();
        String sortName = null;
        // String sortDirection = null;
        Integer pageSize = requestPageable.getPageSize();
        Integer pageOffset = (int) requestPageable.getOffset();

        Optional<Sort.Order> order = requestPageable.getSort().get().findFirst();
        if (order.isPresent()) {
            sortName = order.get().getProperty();  //order by this field
            // sortDirection = order.get().getDirection().toString(); // Sort ASC or DESC
        }

        log.debug("Inside findUoMDetails() pageSize : {}, pageOffset: {}, sortName : {}", pageSize, pageOffset, sortName);
        try {
            Integer totalCount = uoMRepo.getUoMCount(uomId, uomName, statusCd);
            List<Object[]> uomData = uoMRepo.getUoMDetails(uomId, uomName, statusCd, sortName, pageSize, pageOffset);

            List<UoMResponse> uomResponses = uomData.stream().map(UoMResponse::new).collect(Collectors.toList());

            log.info("Inside findUoMDetails() UOM details fetch Successfully total ; {}", uomResponses.size());
            kpiResponse.setSuccess(true);
            kpiResponse.setResponseMessage("UOM details fetch Successfully");
            kpiResponse.setResponseData(new PageImpl(uomResponses, requestPageable, totalCount));
            return kpiResponse;
        } catch (Exception ex) {
            log.error("Inside UoMServiceImpl >> findUoMDetails() : {}", ex);
            throw new KPIException("Inside UoMServiceImpl >> findUoMDetails()", false, ex.getMessage());
        }
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public KPIResponse findUoMDetails(Integer uomId) {
        log.debug("Inside UoMServiceImpl >> findUoMDetails() uomId: {}", uomId);
        UoMResponse uoMResponse = null;
        KPIResponse kpiResponse = new KPIResponse();
        try {
            Optional<UoMEntity> optionalRegionEntity = uoMRepo.findById(uomId);
            if (optionalRegionEntity.isPresent()) {
                UoMEntity uoMEntity = optionalRegionEntity.get();
                uoMResponse = UoMResponse.builder()
                        .uomId(uoMEntity.getUomId())
                        .uomName(uoMEntity.getUomName())
                        .remark(uoMEntity.getRemark())
                        .build();

                kpiResponse.setSuccess(true);
                kpiResponse.setResponseMessage("UOM details fetch Successfully");
                kpiResponse.setResponseData(uoMResponse);
                return kpiResponse;
            } else {
                kpiResponse.setSuccess(false);
                kpiResponse.setResponseMessage("UOM details not found");
                kpiResponse.setResponseData(uoMResponse);
            }
        } catch (Exception ex) {
            log.error("Inside UoMServiceImpl >> findUoMDetails() : {}", ex);
            throw new KPIException("Inside UoMServiceImpl >> findUoMDetails()", false, ex.getMessage());
        }
        return kpiResponse;
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public List<UoMEntity> findAllUoMDetails() {
        log.debug("Inside UoMServiceImpl >> findUoMDetails()");
        try {
            List<UoMEntity> uoMEntities = uoMRepo.findAll();
            if (uoMEntities.size() > 0) {
                return uoMEntities;
            }
        } catch (Exception ex) {
            log.error("Inside UoMServiceImpl >> updateUoM():{}", ex.getMessage());
            throw new KPIException("UoMServiceImpl", false, "UOM is not set");
        }
        log.error("Inside UoMServiceImpl >> updateUoM()");
        throw new KPIException("UoMServiceImpl", false, "UOM is not set");
    }

    private UoMEntity convertUoMCreateRequestToEntity(UoMCreateRequest uoMCreateRequest) {
        log.debug("Inside UoMServiceImpl >> convertUoMCreateRequestToEntity() uoMCreateRequest : {}", uoMCreateRequest);

        UoMEntity uoMEntity = new UoMEntity();
        uoMEntity.setUomName(uoMCreateRequest.getUomName());
        uoMEntity.setRemark(uoMCreateRequest.getRemark());
        uoMEntity.setStatusCd(uoMCreateRequest.getStatusCd());
        uoMEntity.setCreatedUserId(uoMCreateRequest.getEmployeeId());
        return uoMEntity;
    }
}
