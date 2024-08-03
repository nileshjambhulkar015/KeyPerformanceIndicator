package com.futurebizops.kpi.controller;

import com.futurebizops.kpi.request.ComplaintCreateRequest;
import com.futurebizops.kpi.request.EmployeeComplaintUpdateRequest;
import com.futurebizops.kpi.request.advsearch.ComplaintAdvSearch;
import com.futurebizops.kpi.response.KPIResponse;
import com.futurebizops.kpi.response.dropdown.DepartmentDDResponse;
import com.futurebizops.kpi.service.ComplaintService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Column;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/complaint")
public class ComplaintController {

    @Autowired
    ComplaintService complaintService;

    @PostMapping(value = "/employee-complaint")
    public ResponseEntity<KPIResponse> saveEmployeeComplaintDetails(@RequestBody ComplaintCreateRequest complaintCreateRequest) {
        KPIResponse response = complaintService.saveComplaint(complaintCreateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping(value = "/employee-complaint")
    public ResponseEntity<KPIResponse> updateEmployeeDetails(@RequestBody EmployeeComplaintUpdateRequest complaintUpdateRequest) {
        KPIResponse response = complaintService.updateEmployeeComplaint(complaintUpdateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping(value = "/admin-handle-complaint")
    public ResponseEntity<KPIResponse> updateAdminHandleComplaint(@RequestBody EmployeeComplaintUpdateRequest complaintUpdateRequest) {
        KPIResponse response = complaintService.updateAdminHandleComplaint(complaintUpdateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/employee-complaint")
    public ResponseEntity<KPIResponse> deleteEmployeeDetails(@RequestParam(required = false) Integer empCompId) {
        KPIResponse response = complaintService.deleteEmployeeComplaint(empCompId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = "/emp-assign-complaint-him")
    public ResponseEntity<KPIResponse> updateEmpAssignComplaintHimself(@RequestBody EmployeeComplaintUpdateRequest complaintUpdateRequest) {
        KPIResponse response = complaintService.updateEmpAssignComplaintHimself(complaintUpdateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping(value = "/complaint-search")
    @PageableAsQueryParam
    public ResponseEntity<KPIResponse> findDepartmentDetails(
            @RequestParam(required = false) Integer empId,
            @RequestParam(required = false) String compId,
            @RequestParam(required = false) Integer roleId,
            @RequestParam(required = false) Integer deptId,
            @RequestParam(required = false) String compDesc,
            @RequestParam(required = false) String compStatus,
            @RequestParam(required = false)  Integer compTypeDeptId,
            @RequestParam(required = false)  Integer resolveEmpId,
            @RequestParam(required = false) String statusCd,
            @Parameter(hidden = true) Pageable pageable) {
        KPIResponse response = complaintService.findComplaintDetails(empId, compId,roleId,deptId, compDesc,compStatus,compTypeDeptId,resolveEmpId, statusCd, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping(value = "/complaint-adv-search")
    @PageableAsQueryParam
    public ResponseEntity<KPIResponse> advSearchEmployeeComplaintDetails(@RequestBody ComplaintAdvSearch complaintAdvSearch,@Parameter(hidden = true) Pageable pageable) {
        System.out.println("Advance Search Request : "+complaintAdvSearch);
        KPIResponse response = complaintService.advSearchEmployeeComplaintDetails(complaintAdvSearch, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/download-employee-complaint")
    public KPIResponse exportCompletedToExcelEmployee(HttpServletResponse httpServletResponse,
                                               @RequestParam(required = false) String compFromDate,
                                               @RequestParam(required = false) String compToDate,
                                               @RequestParam(required = false) Integer empId,
                                               @RequestParam(required = false) String empCompDeptId,
                                               @RequestParam(required = false) Integer asCompTypeDeptId,
                                               @RequestParam(required = false) String empCompId,
                                               @RequestParam(required = false) String asCompStatus,
                                               @RequestParam(required = false)  Integer asCompResolveEmpId)  {

        KPIResponse kpiResponse=  complaintService.downloadEmployeeComplaints(httpServletResponse, compFromDate,compToDate,empId,empCompDeptId,asCompTypeDeptId,empCompId,asCompStatus,asCompResolveEmpId);
        return kpiResponse;
    }



    // show complaint details when click on view button of complaint ui table
    @GetMapping(value = "/by-emp-comp-id")
    public ResponseEntity<Object> findAllEmployeeCompById(@RequestParam(required = false) Integer empCompId) {
        return new ResponseEntity<>(complaintService.findAllEmployeeCompById(empCompId), HttpStatus.OK);
    }
}
