package com.gdaniel.glpayroll.adminarea.employeestatus.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gdaniel.glpayroll.adminarea.employeestatus.dto.EmployeeStatusDto;
import com.gdaniel.glpayroll.adminarea.employeestatus.service.EmployeeStatusService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//Comment: This controller will handle all the requests related to employee status management in the admin panel. It will include endpoints for creating, updating, retrieving, and deleting employee statuses. The actual implementation of these endpoints will be added later as we develop the service layer and repository layer for employee status management.
// The @RestController annotation indicates that this class will handle RESTful web requests, and the @RequestMapping annotation specifies the base URL for all endpoints in this controller. The @AllArgsConstructor annotation from Lombok will generate a constructor with parameters for all fields, which can be useful for dependency injection when we add service components later on.

@RestController
@AllArgsConstructor
@RequestMapping("api/admin/employeestatus")
public class EmployeeStatusController {

    private final EmployeeStatusService employeeStatusService;

    @GetMapping("/list")
    public ResponseEntity<Iterable<EmployeeStatusDto>> listEmployeeStatuses() {
        Iterable<EmployeeStatusDto> statusList = employeeStatusService.findAllEmployeeStatuses();
        return ResponseEntity.ok(statusList);
    }

    @PostMapping("/create")
    public ResponseEntity<EmployeeStatusDto> createEmployeeStatus(EmployeeStatusDto dto) {
        EmployeeStatusDto createdStatus = employeeStatusService.createEmployeeStatus(dto);
        return ResponseEntity.ok(createdStatus);
    }

    @PostMapping("/edit")
    public ResponseEntity<EmployeeStatusDto> editEmployeeStatus(EmployeeStatusDto dto) {
        EmployeeStatusDto updatedStatus = employeeStatusService.updateEmployeeStatus(dto);
        return ResponseEntity.ok(updatedStatus);
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> deleteEmployeeStatus(@RequestParam Long id) {
        employeeStatusService.deleteEmployeeStatus(id);
        return ResponseEntity.noContent().build();
    }
}
