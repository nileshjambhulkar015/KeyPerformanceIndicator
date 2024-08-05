package com.futurebizops.kpi.service.serviceimpl;

import com.futurebizops.kpi.constants.KPIConstants;
import com.futurebizops.kpi.entity.RequestTypeAudit;
import com.futurebizops.kpi.entity.RequestTypeEntity;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.repository.RequestTypeAuditRepo;
import com.futurebizops.kpi.repository.RequestTypeRepo;
import com.futurebizops.kpi.request.RequestTypeCreateRequest;
import com.futurebizops.kpi.request.RequestTypeUpdateRequest;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.RequestTypeReponse;
import com.futurebizops.kpi.response.dropdown.DepartmentDDResponse;
import com.futurebizops.kpi.response.dropdown.RequestTypeDDResponse;
import com.futurebizops.kpi.service.RequestTypeService;
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
public class RequestTypeServiceImpl implements RequestTypeService {

    @Autowired
    RequestTypeRepo requestTypeRepo;

    @Autowired
    RequestTypeAuditRepo requestTypeAuditRepo;

    @Override
    public KPIResponse saveRequestType(RequestTypeCreateRequest requestTypeCreateRequest) {
        Optional<RequestTypeEntity> optionalRequestType = requestTypeRepo.findByDeptIdAndReqTypeNameEqualsIgnoreCase(requestTypeCreateRequest.getDeptId(), requestTypeCreateRequest.getReqTypeName());
        if (optionalRequestType.isPresent()) {
            log.error("Inside RequestTypeServiceImpl >> saveRequestType()");
            throw new KPIException("RequestTypeServiceImpl Class", false, "Request Type name already exist");
        }

        RequestTypeEntity requestTypeEntity = convertRequestTypeCreateRequestToEntity(requestTypeCreateRequest);
        try {
            requestTypeRepo.save(requestTypeEntity);
            RequestTypeAudit requestTypeAudit = new RequestTypeAudit(requestTypeEntity);
            requestTypeAuditRepo.save(requestTypeAudit);
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_SUCCESS)
                    .build();
        } catch (Exception ex) {
            log.error("Inside RequestTypeServiceImpl >> saveRequestType()");
            throw new KPIException("RequestTypeServiceImpl", false, ex.getMessage());
        }
    }

    @Override
    public KPIResponse findRequestTypeDetails(Integer reqTypeId, String reqTypeName, String statusCd, Pageable requestPageable) {
        String sortName = null;
        //  String sortDirection = null;
        Integer pageSize = requestPageable.getPageSize();
        Integer pageOffset = (int) requestPageable.getOffset();
        // pageable = KPIUtils.sort(requestPageable, sortParam, pageDirection);
        Optional<Sort.Order> order = requestPageable.getSort().get().findFirst();
        if (order.isPresent()) {
            sortName = order.get().getProperty();  //order by this field
            //sortDirection = order.get().getDirection().toString(); // Sort ASC or DESC
        }

        Integer totalCount = requestTypeRepo.getRequestTypeCount(reqTypeId, reqTypeName, statusCd);
        List<Object[]> requestData = requestTypeRepo.getRequestTypeDetail(reqTypeId, reqTypeName, statusCd, sortName, pageSize, pageOffset);

        List<RequestTypeReponse> requestTypeReponses = requestData.stream().map(RequestTypeReponse::new).collect(Collectors.toList());

        requestTypeReponses = requestTypeReponses.stream()
                .sorted(Comparator.comparing(RequestTypeReponse::getReqTypeName))
                .collect(Collectors.toList());

        return KPIResponse.builder()
                .isSuccess(true)
                .responseData(new PageImpl<>(requestTypeReponses, requestPageable, totalCount))
                .responseMessage(KPIConstants.RECORD_FETCH)
                .build();
    }

    @Override
    public RequestTypeReponse findAllRequestTypeById(Integer reqTypeId) {
        List<Object[]> requestTypeData = requestTypeRepo.getRequestTypeByIdDetail(reqTypeId);
        List<RequestTypeReponse> requestTypeReponses = requestTypeData.stream().map(RequestTypeReponse::new).collect(Collectors.toList());
        return requestTypeReponses.get(0);
    }

    @Override
    public KPIResponse updateRequestType(RequestTypeUpdateRequest requestTypeUpdateRequest) {
        RequestTypeEntity requestTypeEntity = convertRequestTypeUpdateRequestToEntity(requestTypeUpdateRequest);
        try {
            requestTypeRepo.save(requestTypeEntity);
            RequestTypeAudit departmentAudit = new RequestTypeAudit(requestTypeEntity);
            requestTypeAuditRepo.save(departmentAudit);
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_UPDATE)
                    .build();
        } catch (Exception ex) {
            log.error("Inside RequestTypeServiceImpl >> updateRequestType()");
            throw new KPIException("RequestTypeServiceImpl", false, ex.getMessage());
        }
    }

    @Override
    public List<DepartmentDDResponse> findAllDepartmentFromRequestType() {
        List<Object[]> requestData = requestTypeRepo.findAllDepartmentFromRequestType();
        List<DepartmentDDResponse> departmentDDResponses = null;
        if (requestData.size() > 0) {
            departmentDDResponses = requestData.stream().map(DepartmentDDResponse::new).collect(Collectors.toList());
        }
        return departmentDDResponses;
    }

    @Override
    public List<RequestTypeDDResponse> findAllRequestTypeByDeptId(Integer deptId) {
        List<Object[]> requestData = requestTypeRepo.findAllRequestTypeByDeptId(deptId);
        List<RequestTypeDDResponse> requestTypeDDResponses = null;
        if (requestData.size() > 0) {
            requestTypeDDResponses = requestData.stream().map(RequestTypeDDResponse::new).collect(Collectors.toList());
        }
        return requestTypeDDResponses;
    }

    private RequestTypeEntity convertRequestTypeCreateRequestToEntity(RequestTypeCreateRequest requestTypeCreateRequest) {
        RequestTypeEntity requestTypeEntity = new RequestTypeEntity();
        requestTypeEntity.setDeptId(requestTypeCreateRequest.getDeptId());

        requestTypeEntity.setReqTypeName(requestTypeCreateRequest.getReqTypeName());
        requestTypeEntity.setRemark(requestTypeCreateRequest.getRemark());
        requestTypeEntity.setStatusCd(requestTypeCreateRequest.getStatusCd());
        requestTypeEntity.setCreatedUserId(requestTypeCreateRequest.getEmployeeId());
        return requestTypeEntity;
    }

    private RequestTypeEntity convertRequestTypeUpdateRequestToEntity(RequestTypeUpdateRequest requestTypeUpdateRequest) {
        RequestTypeEntity requestTypeEntity = new RequestTypeEntity();
        requestTypeEntity.setReqTypeId(requestTypeUpdateRequest.getReqTypeId());
        requestTypeEntity.setDeptId(requestTypeUpdateRequest.getDeptId());

        requestTypeEntity.setReqTypeName(requestTypeUpdateRequest.getReqTypeName());
        requestTypeEntity.setRemark(requestTypeUpdateRequest.getRemark());
        requestTypeEntity.setStatusCd(requestTypeUpdateRequest.getStatusCd());
        requestTypeEntity.setCreatedUserId(requestTypeUpdateRequest.getEmployeeId());
        return requestTypeEntity;
    }
}
