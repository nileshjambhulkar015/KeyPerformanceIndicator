package com.futurebizops.kpi.service.serviceimpl;

import com.futurebizops.kpi.constants.KPIConstants;
import com.futurebizops.kpi.entity.DepartmentAudit;
import com.futurebizops.kpi.entity.DepartmentEntity;
import com.futurebizops.kpi.entity.EmployeeTypeAudit;
import com.futurebizops.kpi.entity.EmployeeTypeEntity;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.repository.EmployeeTypeAuditRepo;
import com.futurebizops.kpi.repository.EmployeeTypeRepo;
import com.futurebizops.kpi.request.DepartmentCreateRequest;
import com.futurebizops.kpi.request.DepartmentUpdateRequest;
import com.futurebizops.kpi.request.EmployeeTypeCreateRequest;
import com.futurebizops.kpi.request.EmployeeTypeUpdateRequest;
import com.futurebizops.kpi.response.DepartmentReponse;
import com.futurebizops.kpi.response.EmployeeResponse;
import com.futurebizops.kpi.response.EmployeeTypeResponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.dropdown.EmployeeTypeDDResponse;
import com.futurebizops.kpi.service.EmployeeTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EmployeeTypeServiceImpl implements EmployeeTypeService {

    @Autowired
    EmployeeTypeRepo employeeTypeRepo;

    @Autowired
    EmployeeTypeAuditRepo employeeTypeAuditRepo;

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public KPIResponse saveEmployeeType(EmployeeTypeCreateRequest employeeTypeCreateRequest) {
        log.debug("Inside EmployeeTypeServiceImpl >> saveEmployeeType() employeeTypeCreateRequest :{} ", employeeTypeCreateRequest);
        Optional<EmployeeTypeEntity> optionalDepartmentEntity = employeeTypeRepo.findByEmpTypeNameEqualsIgnoreCase(employeeTypeCreateRequest.getEmpTypeName());
        if (optionalDepartmentEntity.isPresent()) {
            log.error("Inside EmployeeTypeServiceImpl >> saveEmployeeType()");
            throw new KPIException("EmployeeTypeServiceImpl Class", false, "Employee Type name already exist");
        }

        EmployeeTypeEntity employeeTypeEntity = convertEmployeeTypeCreateRequestToEntity(employeeTypeCreateRequest);
        try {
            employeeTypeRepo.save(employeeTypeEntity);
            EmployeeTypeAudit employeeTypeAudit = new EmployeeTypeAudit(employeeTypeEntity);
            employeeTypeAuditRepo.save(employeeTypeAudit);
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_SUCCESS)
                    .build();
        } catch (Exception ex) {
            log.error("Inside EmployeeTypeServiceImpl >> saveEmployeeType() : {}", ex);
            throw new KPIException("EmployeeTypeServiceImpl >> saveEmployeeType()", false, ex.getMessage());
        }
    }

    @Transactional
    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public KPIResponse deleteEmployeeTypeDetails(Integer empTypeId) {
        log.debug("Inside EmployeeTypeServiceImpl >> deleteEmployeeTypeDetails() empTypeId :{} ", empTypeId);
        KPIResponse busPassResponse = new KPIResponse();
        try {
            employeeTypeRepo.deleteEmployeeTypeDetails(empTypeId);
            busPassResponse.setSuccess(true);
            busPassResponse.setResponseMessage("Employee Type  details deleted Successfully");
            return busPassResponse;
        } catch (Exception ex) {
            log.error("Inside EmployeeTypeServiceImpl >> deleteEmployeeTypeDetails() : {}", ex);
            throw new KPIException("EmployeeTypeServiceImpl >> deleteEmployeeTypeDetails()", false, ex.getMessage());
        }
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public KPIResponse updateEmployeeType(EmployeeTypeUpdateRequest employeeTypeUpdateRequest) {
        log.debug("Inside EmployeeTypeServiceImpl >> updateEmployeeType() employeeTypeUpdateRequest :{} ", employeeTypeUpdateRequest);
        try {
            Optional<EmployeeTypeEntity> optionalEmployeeTypeEntity = employeeTypeRepo.findById(employeeTypeUpdateRequest.getEmpTypeId());
            if (optionalEmployeeTypeEntity.isPresent()) {
                EmployeeTypeEntity employeeTypeEntity = optionalEmployeeTypeEntity.get();
                employeeTypeEntity.setEmpTypeName(employeeTypeUpdateRequest.getEmpTypeName());
                employeeTypeEntity.setUpdatedUserId(employeeTypeUpdateRequest.getEmployeeId());
                employeeTypeRepo.save(employeeTypeEntity);
                EmployeeTypeAudit departmentAudit = new EmployeeTypeAudit(employeeTypeEntity);
                employeeTypeAuditRepo.save(departmentAudit);
                return KPIResponse.builder()
                        .isSuccess(true)
                        .responseMessage(KPIConstants.RECORD_UPDATE)
                        .build();
            }
        } catch (Exception ex) {
            log.error("Inside EmployeeTypeServiceImpl >> updateEmployeeType() : {}", ex);
            throw new KPIException("EmployeeTypeServiceImpl", false, ex.getMessage());
        }
        return KPIResponse.builder()
                .isSuccess(false)
                .responseMessage("Record not Found")
                .build();
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public KPIResponse findEmployeeTypeDetails(Integer empTypeId, String empTypeName, String statusCd) {
        log.debug("Inside EmployeeTypeServiceImpl >> findEmployeeTypeDetails() empTypeId :{}, empTypeName: {} ", empTypeId, empTypeName);
        try {
            List<Object[]> employeeTypeData = employeeTypeRepo.getEmployeeTypeDetail(empTypeId, empTypeName, statusCd);
            List<EmployeeTypeResponse> employeeTypeResponses = employeeTypeData.stream().map(EmployeeTypeResponse::new).collect(Collectors.toList());
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseData(employeeTypeResponses)
                    .responseMessage(KPIConstants.RECORD_FETCH)
                    .build();
        } catch (Exception ex) {
            log.error("Inside EmployeeTypeServiceImpl >>findEmployeeTypeDetails() :{}", ex);
            throw new KPIException("EmployeeTypeServiceImpl >> findEmployeeTypeDetails()", false, ex.getMessage());
        }
    }

    @Override
    @Retryable(include = {KPIException.class}, maxAttemptsExpression = "${retry-max-attempts}")
    public EmployeeTypeResponse findEmployeeTypeDetailsByEmpTypeId(Integer empTypeId) {
        log.debug("Inside EmployeeTypeServiceImpl >> findEmployeeTypeDetailsByEmpTypeId() empTypeId :{}", empTypeId);
        try {
            List<Object[]> employeeTypeData = employeeTypeRepo.findEmployeeTypeDetailsByEmpTypeId(empTypeId);
            List<EmployeeTypeResponse> employeeTypeResponses = employeeTypeData.stream().map(EmployeeTypeResponse::new).collect(Collectors.toList());
            if (employeeTypeResponses.size() > 0) {
                return employeeTypeResponses.get(0);
            }
        } catch (Exception ex) {
            log.error("Inside EmployeeTypeServiceImpl >>findEmployeeTypeDetailsByEmpTypeId() :{}", ex);
            throw new KPIException("EmployeeTypeServiceImpl >> findEmployeeTypeDetailsByEmpTypeId()", false, ex.getMessage());
        }
        return new EmployeeTypeResponse();
    }

    private EmployeeTypeEntity convertEmployeeTypeCreateRequestToEntity(EmployeeTypeCreateRequest employeeTypeCreateRequest) {
        log.debug("Inside EmployeeTypeServiceImpl >> convertEmployeeTypeCreateRequestToEntity() employeeTypeCreateRequest :{}", employeeTypeCreateRequest);
        EmployeeTypeEntity employeeTypeEntity = new EmployeeTypeEntity();

        employeeTypeEntity.setEmpTypeName(employeeTypeCreateRequest.getEmpTypeName());
        employeeTypeEntity.setRemark(employeeTypeCreateRequest.getRemark());
        employeeTypeEntity.setStatusCd(employeeTypeCreateRequest.getStatusCd());
        employeeTypeEntity.setCreatedUserId(employeeTypeCreateRequest.getEmployeeId());
        return employeeTypeEntity;
    }
}
