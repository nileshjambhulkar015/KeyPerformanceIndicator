package com.futurebizops.kpi.service.serviceImpl;

import com.futurebizops.kpi.constants.KPIConstants;
import com.futurebizops.kpi.entity.DepartmentAudit;
import com.futurebizops.kpi.entity.DepartmentEntity;
import com.futurebizops.kpi.entity.DesignationEntity;
import com.futurebizops.kpi.entity.RegionAudit;
import com.futurebizops.kpi.entity.RegionEntity;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.repository.RegionAuditRepo;
import com.futurebizops.kpi.repository.RegionRepo;
import com.futurebizops.kpi.request.DepartmentCreateRequest;
import com.futurebizops.kpi.request.DepartmentUpdateRequest;
import com.futurebizops.kpi.request.RegionCreateRequest;
import com.futurebizops.kpi.request.RegionUpdateRequest;
import com.futurebizops.kpi.response.DesignationReponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.RegionResponse;
import com.futurebizops.kpi.service.RegionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RegionServiceImpl implements RegionService {

    @Autowired
    private RegionRepo regionRepo;

    @Autowired
    private RegionAuditRepo regionAuditRepo;

    @Override
    public KPIResponse saveRegion(RegionCreateRequest regionCreateRequest) {
        Optional<RegionEntity> optionalRegionEntity = regionRepo.findByRegionNameEqualsIgnoreCase(regionCreateRequest.getRegionName());
        if(optionalRegionEntity.isPresent()){
            log.error("Inside RegionServiceImpl >> saveRegion()");
            throw new KPIException("RegionServiceImpl", false, "Region name already exist");
        }

        RegionEntity regionEntity = convertRegionCreateRequestToEntity(regionCreateRequest);
        try {
            regionRepo.save(regionEntity);
            RegionAudit partAudit = new RegionAudit(regionEntity);
            regionAuditRepo.save(partAudit);
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_SUCCESS)
                    .build();
        } catch (Exception ex) {
            log.error("Inside RegionServiceImpl >> saveRegion()");
            throw new KPIException("RegionServiceImpl", false, ex.getMessage());
        }
    }

    @Override
    public KPIResponse updateRegion(RegionUpdateRequest regionUpdateRequest) {
        RegionEntity regionEntity = convertRegionUpdateRequestToEntity(regionUpdateRequest);
        try {
            regionRepo.save(regionEntity);
            RegionAudit regionAudit = new RegionAudit(regionEntity);
            regionAuditRepo.save(regionAudit);
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_UPDATE)
                    .build();
        } catch (Exception ex) {
            log.error("Inside RegionServiceImpl >> updateRegion()");
            throw new KPIException("RegionServiceImpl", false, ex.getMessage());
        }
    }

    @Override
    public KPIResponse findRegionDetails(Integer regionId, String regionName, String statusCd, Pageable requestPageable) {
        Page<DesignationEntity> departmentEntities = null;

        String sortName = null;
        String sortDirection = null;
        Integer pageSize = requestPageable.getPageSize();
        Integer pageOffset = (int) requestPageable.getOffset();

        Optional<Sort.Order> order = requestPageable.getSort().get().findFirst();
        if (order.isPresent()) {
            sortName = order.get().getProperty();  //order by this field
            sortDirection = order.get().getDirection().toString(); // Sort ASC or DESC
        }

        Integer totalCount = regionRepo.getRegionCount(regionId, regionName, statusCd);
        List<Object[]> regionData = regionRepo.getRegionDetails(regionId, regionName, statusCd, sortName, pageSize, pageOffset);

        List<RegionResponse> regionResponses = regionData.stream().map(RegionResponse::new).collect(Collectors.toList());

        return KPIResponse.builder()
                .isSuccess(true)
                .responseData(new PageImpl(regionResponses, requestPageable, totalCount))
                .responseMessage(KPIConstants.RECORD_FETCH)
                .build();
    }

    private RegionEntity convertRegionCreateRequestToEntity(RegionCreateRequest regionCreateRequest) {
        return RegionEntity.regionEntityBuilder()
                .regionName(regionCreateRequest.getRegionName())
                .remark(regionCreateRequest.getRemark())
                .statusCd(regionCreateRequest.getStatusCd())
                .createdUserId(regionCreateRequest.getEmployeeId())
                .build();
    }

    private RegionEntity convertRegionUpdateRequestToEntity(RegionUpdateRequest regionUpdateRequest) {
        return RegionEntity.regionEntityBuilder()
                .regionId(regionUpdateRequest.getRegionId())
                .regionName(regionUpdateRequest.getRegionName())
                .remark(regionUpdateRequest.getRemark())
                .statusCd(regionUpdateRequest.getStatusCd())
                .createdUserId(regionUpdateRequest.getEmployeeId())
                .build();
    }
}
