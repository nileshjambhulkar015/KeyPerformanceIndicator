package com.futurebizops.kpi.service.serviceimpl;

import com.futurebizops.kpi.entity.EmployeeKppMasterEntity;
import com.futurebizops.kpi.exception.KPIException;
import com.futurebizops.kpi.repository.EmployeeKppMasterRepo;
import com.futurebizops.kpi.repository.EmployeeLoginAuditRepo;
import com.futurebizops.kpi.repository.EmployeeLoginRepo;
import com.futurebizops.kpi.repository.EmployeeRepo;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.LoginResponse;
import com.futurebizops.kpi.service.EmployeeLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmployeeLoginServiceImpl implements EmployeeLoginService {

    @Autowired
    private EmployeeLoginRepo employeeLoginRepo;

    @Autowired
    private EmployeeLoginAuditRepo employeeLoginAuditRepo;

    @Autowired
    EmployeeKppMasterRepo employeeKppMasterRepo;

    @Override
    public KPIResponse employeeLogin(String userName, String userPassword) {
        List<Object[]> employeeLogin = employeeLoginRepo.employeeLogin(userName, userPassword);
        List<LoginResponse> loginResponses = employeeLogin.stream().map(LoginResponse::new).collect(Collectors.toList());
        if (!loginResponses.isEmpty()) {
            Optional<EmployeeKppMasterEntity> entityOptional = employeeKppMasterRepo.findByEmpId(loginResponses.get(0).getEmpId());
            if(entityOptional.isPresent()) {
                log.info("Login successfully");
                return KPIResponse.builder()
                        .isSuccess(true)
                        .responseData(loginResponses.get(0))
                        .responseMessage("Login successfully")
                        .build();
            } else {
                log.info("Please contact GM. Kpp is not set for you");
                throw new KPIException("EmployeeLoginServiceImpl", false, "Please contact GM. Kpp is not set for you");
            }
        } else {
            log.error("Inside EmployeeLoginServiceImpl >> employeeLogin()");
            throw new KPIException("EmployeeLoginServiceImpl", false, "Login failed");
        }
    }

    @Transactional
    @Override
    public KPIResponse updateLoginPassword(String userName, String userPassword) {
        int result = employeeLoginRepo.updatePassword(userName, userPassword);
        if (result > 0) {
            return KPIResponse.builder()
                    .isSuccess(true)
                    .responseMessage("Password changed successfully")
                    .build();
        }
        return KPIResponse.builder()
                .isSuccess(false)
                .responseMessage("Try again..")
                .build();
    }
}
