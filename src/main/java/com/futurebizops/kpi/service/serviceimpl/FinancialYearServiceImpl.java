package com.futurebizops.kpi.service.serviceimpl;

import com.futurebizops.kpi.constants.KPIConstants;
import com.futurebizops.kpi.entity.EmployeeTypeAudit;
import com.futurebizops.kpi.entity.EmployeeTypeEntity;
import com.futurebizops.kpi.entity.FinancialYearEntity;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.repository.EmployeeTypeAuditRepo;
import com.futurebizops.kpi.repository.EmployeeTypeRepo;
import com.futurebizops.kpi.repository.FinancialYearRepo;
import com.futurebizops.kpi.request.EmployeeTypeCreateRequest;
import com.futurebizops.kpi.request.EmployeeTypeUpdateRequest;
import com.futurebizops.kpi.request.FinancialYearCreateRequest;
import com.futurebizops.kpi.request.FinancialYearUpdateRequest;
import com.futurebizops.kpi.response.EmployeeTypeResponse;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.service.EmployeeTypeService;
import com.futurebizops.kpi.service.FinancialYearService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FinancialYearServiceImpl implements FinancialYearService {

    @Autowired
    FinancialYearRepo financialYearRepo;

   // @Autowired
    //EmployeeTypeAuditRepo employeeTypeAuditRepo;

    @Override
    public KPIResponse saveFinancialYear(FinancialYearCreateRequest financialYearCreateRequest) {
        Optional<FinancialYearEntity> optionalDepartmentEntity = financialYearRepo.findByFinYearNameEqualsIgnoreCase(financialYearCreateRequest.getFinYearName() );
        if(optionalDepartmentEntity.isPresent()){
            log.error("Inside FinancialYearServiceImpl >> saveEmployeeType() Financial year already exist");
            throw new KPIException("FinancialYearServiceImpl Class", false, "Financial year name already exist");
        }

        FinancialYearEntity financialYearEntity = convertFinancialYearCreateRequestToEntity(financialYearCreateRequest);
        try {
            financialYearRepo.save(financialYearEntity);
            //EmployeeTypeAudit employeeTypeAudit = new EmployeeTypeAudit(employeeTypeEntity);
            //employeeTypeAuditRepo.save(employeeTypeAudit);
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage(KPIConstants.RECORD_SUCCESS)
                    .build();
        } catch (Exception ex) {
            log.error("Inside FinancialYearServiceImpl >> saveFinancialYear() : {}",ex);
            throw new KPIException("FinancialYearServiceImpl", false, ex.getMessage());
        }

    }

    @Transactional
    @Override
    public KPIResponse deleteFinancialYear(Integer finYearId) {
        KPIResponse busPassResponse = new KPIResponse();
        try {
            financialYearRepo.deleteFinancialYear(finYearId);
            busPassResponse.setSuccess(true);
            busPassResponse.setResponseMessage("Financial Year  details deleted Successfully");
            return busPassResponse;
        } catch (Exception ex) {
            log.error("Inside FinancialYearServiceImpl >> deleteFinancialYear() : {}",ex);
            return KPIResponse.builder()
                    .isSuccess(false)
                    .build();
        }

    }

    @Override
    public KPIResponse updateFinancialYear(FinancialYearUpdateRequest financialYearUpdateRequest) {
        Optional<FinancialYearEntity> financialYearEntity = financialYearRepo.findById(financialYearUpdateRequest.getFinYearId());
        try {
            if(financialYearEntity.isPresent()){
                FinancialYearEntity financialYear = financialYearEntity.get();
                financialYear.setFinYearName(financialYearUpdateRequest.getFinYearName());
                financialYear.setRemark(financialYearUpdateRequest.getRemark());
                financialYearRepo.save(financialYear);
                return KPIResponse.builder()
                        .isSuccess(true)
                        .responseMessage(KPIConstants.RECORD_UPDATE)
                        .build();
            }

        } catch (Exception ex) {
            log.error("Inside FinancialYearServiceImpl >> updateFinancialYear() : {}", ex);
            throw new KPIException("FinancialYearServiceImpl >> updateFinancialYear", false, ex.getMessage());
        }
        return KPIResponse.builder()
                .isSuccess(false)
                .responseMessage("Financial year not updated")
                .build();
    }

    @Override
    public KPIResponse findFinancialYear(Integer finYearId, String empTypeName, String statusCd) {

        List<FinancialYearEntity> financialYearEntities = financialYearRepo.findAll();

        return KPIResponse.builder()
                .isSuccess(true)
                .responseData(financialYearEntities)
                .responseMessage(KPIConstants.RECORD_FETCH)
                .build();
    }


    private FinancialYearEntity convertFinancialYearCreateRequestToEntity(FinancialYearCreateRequest financialYearCreateRequest) {
        FinancialYearEntity financialYearEntity = new FinancialYearEntity();

        financialYearEntity.setFinYearName(financialYearCreateRequest.getFinYearName());
        financialYearEntity.setRemark(financialYearCreateRequest.getRemark());
        financialYearEntity.setStatusCd(financialYearCreateRequest.getStatusCd());
        financialYearEntity.setCreatedUserId(financialYearCreateRequest.getEmployeeId());
        return  financialYearEntity;
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
