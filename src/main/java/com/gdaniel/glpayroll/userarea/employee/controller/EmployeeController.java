package com.gdaniel.glpayroll.userarea.employee.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gdaniel.glpayroll.userarea.employee.dto.EmployeeDto;
import com.gdaniel.glpayroll.userarea.employee.service.EmployeeService;

import org.springframework.data.domain.Pageable;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@AllArgsConstructor
@RequestMapping("api/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    @GetMapping("/list")
    public ResponseEntity<Iterable<EmployeeDto>> listEmployees(Pageable pageable) {

        var employeeList = employeeService.findAllWithDocCount(pageable);
        return ResponseEntity.ok(employeeList);

    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EmployeeDto> create(@RequestBody EmployeeDto dto) {

        return ResponseEntity.ok(employeeService.createEmployee(dto));

    }

    @PostMapping("/edit")
    public ResponseEntity<EmployeeDto> edit(@RequestBody EmployeeDto dto) {

        return ResponseEntity.ok(employeeService.updateEmployee(dto));

    }

}
