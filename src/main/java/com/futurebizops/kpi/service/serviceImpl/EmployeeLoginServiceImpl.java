package com.futurebizops.kpi.service.serviceImpl;

import com.futurebizops.kpi.entity.EmployeeEntity;
import com.futurebizops.kpi.entity.EmployeeLoginEntity;
import com.futurebizops.kpi.repository.EmployeeLoginAuditRepo;
import com.futurebizops.kpi.repository.EmployeeLoginRepo;
import com.futurebizops.kpi.repository.EmployeeRepo;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.LoginResponse;
import com.futurebizops.kpi.service.EmployeeLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeLoginServiceImpl implements EmployeeLoginService {

    @Autowired
    private EmployeeLoginRepo employeeLoginRepo;

    @Autowired
    private EmployeeLoginAuditRepo employeeLoginAuditRepo;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Override
    public KPIResponse employeeLogin(String userName, String userPassword) {

        Optional<EmployeeLoginEntity> employeeLoginEntity = employeeLoginRepo.findByEmpMobileNoAndEmpPasswordAndStatusCd(userName, userPassword, "A");
        String empMbNo = null;
        if (employeeLoginEntity.isPresent()) {
            empMbNo = employeeLoginEntity.get().getEmpMobileNo();
            EmployeeEntity employeeEntity = employeeRepo.findByEmpMobileNoAndStatusCd(empMbNo, "A");
            LoginResponse loginResponse = LoginResponse.builder()
                    .empId(employeeEntity.getEmpId())
                    .empFirstName(employeeEntity.getEmpFirstName())
                    .empMiddleName(employeeEntity.getEmpMiddleName())
                    .empLastName(employeeEntity.getEmpLastName())
                    .roleId(employeeEntity.getRoleId())
                    .empMobileNo(employeeEntity.getEmpMobileNo())
                    .emailId(employeeEntity.getEmailId())
                    .build();
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseData(loginResponse)
                    .responseMessage("Login successfully")
                    .build();
        }
        return KPIResponse.builder()
                .isSuccess(false)
                .responseMessage("User name or password is wrong")
                .build();
    }
}
