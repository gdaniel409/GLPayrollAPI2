package com.gdaniel.glpayroll.adminarea.ratetype.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gdaniel.glpayroll.adminarea.ratetype.dto.RateTypeDto;
import com.gdaniel.glpayroll.adminarea.ratetype.service.RateTypeService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@AllArgsConstructor
@RequestMapping("api/admin/ratetype")
public class RateTypeController {

    private final RateTypeService rateService;

    @GetMapping("/list")
    public ResponseEntity<Iterable<RateTypeDto>> listEmployeeStatuses() {
        Iterable<RateTypeDto> rateTypeList = rateService.findAllRateTypes();
        return ResponseEntity.ok(rateTypeList);
    }

    @PostMapping("/create")
    public ResponseEntity<RateTypeDto> createRateType(RateTypeDto rateTypeDto) {

        RateTypeDto createdRateType = rateService.create(rateTypeDto);
        return ResponseEntity.ok(createdRateType);
    }

    @PostMapping("/update")
    public ResponseEntity<RateTypeDto> updateRateType(RateTypeDto rateTypeDto) {
        RateTypeDto updatedRateType = rateService.update(rateTypeDto);
        return ResponseEntity.ok(updatedRateType);
    }

    @GetMapping("/delete")
    public void getMethodName(@RequestParam String param) {
        rateService.deleteById(Long.parseLong(param));
    }

}