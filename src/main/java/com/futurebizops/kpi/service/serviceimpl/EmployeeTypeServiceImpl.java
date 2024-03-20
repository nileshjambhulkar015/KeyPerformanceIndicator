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
import org.springframework.stereotype.Service;

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
    public KPIResponse saveEmployeeType(EmployeeTypeCreateRequest employeeTypeCreateRequest) {
        Optional<EmployeeTypeEntity> optionalDepartmentEntity = employeeTypeRepo.findByEmpTypeNameEqualsIgnoreCase(employeeTypeCreateRequest.getEmpTypeName() );
        if(optionalDepartmentEntity.isPresent()){
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
            log.error("Inside EmployeeTypeServiceImpl >> saveEmployeeType()");
            throw new KPIException("EmployeeTypeServiceImpl", false, ex.getMessage());
        }

    }

    @Override
    public KPIResponse updateEmployeeType(EmployeeTypeUpdateRequest employeeTypeUpdateRequest) {
        EmployeeTypeEntity employeeTypeEntity = convertEmployeeTypeUpdateRequestToEntity(employeeTypeUpdateRequest);
        try {
            employeeTypeRepo.save(employeeTypeEntity);
            EmployeeTypeAudit departmentAudit = new EmployeeTypeAudit(employeeTypeEntity);
            employeeTypeAuditRepo.save(departmentAudit);
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_UPDATE)
                    .build();
        } catch (Exception ex) {
            log.error("Inside EmployeeTypeServiceImpl >> updateEmployeeType()");
            throw new KPIException("EmployeeTypeServiceImpl", false, ex.getMessage());
        }
    }

    @Override
    public KPIResponse findEmployeeTypeDetails(Integer empTypeId, String empTypeName, String statusCd) {

        List<Object[]> employeeTypeData = employeeTypeRepo.getEmployeeTypeDetail(empTypeId, empTypeName, statusCd);

        List<EmployeeTypeResponse> employeeTypeResponses = employeeTypeData.stream().map(EmployeeTypeResponse::new).collect(Collectors.toList());

        return KPIResponse.builder()
                .isSuccess(true)
                .responseData(employeeTypeResponses)
                .responseMessage(KPIConstants.RECORD_FETCH)
                .build();
    }


    private EmployeeTypeEntity convertEmployeeTypeCreateRequestToEntity(EmployeeTypeCreateRequest employeeTypeCreateRequest) {
        EmployeeTypeEntity employeeTypeEntity = new EmployeeTypeEntity();

        employeeTypeEntity.setEmpTypeName(employeeTypeCreateRequest.getEmpTypeName());
        employeeTypeEntity.setRemark(employeeTypeCreateRequest.getRemark());
        employeeTypeEntity.setStatusCd(employeeTypeCreateRequest.getStatusCd());
        employeeTypeEntity.setCreatedUserId(employeeTypeCreateRequest.getEmployeeId());
        return  employeeTypeEntity;
    }

    private EmployeeTypeEntity convertEmployeeTypeUpdateRequestToEntity(EmployeeTypeUpdateRequest employeeTypeUpdateRequest) {
        EmployeeTypeEntity employeeTypeEntity = new EmployeeTypeEntity();
        employeeTypeEntity.setEmpTypeId(employeeTypeUpdateRequest.getEmpTypeId());
        employeeTypeEntity.setEmpTypeName(employeeTypeUpdateRequest.getEmpTypeName());
        employeeTypeEntity.setRemark(employeeTypeUpdateRequest.getRemark());
        employeeTypeEntity.setStatusCd(employeeTypeUpdateRequest.getStatusCd());
        employeeTypeEntity.setCreatedUserId(employeeTypeUpdateRequest.getEmployeeId());
        return  employeeTypeEntity;
    }
}
