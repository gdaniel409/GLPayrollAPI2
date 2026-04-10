package com.gdaniel.glpayroll.adminarea.employeestatus.service;

import java.util.Collection;
import org.springframework.stereotype.Service;
import com.gdaniel.glpayroll.adminarea.employeestatus.dto.EmployeeStatusDto;
import com.gdaniel.glpayroll.adminarea.employeestatus.entity.EmployeeStatusEntity;
import com.gdaniel.glpayroll.adminarea.employeestatus.repository.EmployeeStatusRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class EmployeeStatusService {

    private final EmployeeStatusRepository employeeStatusRepository;

    public EmployeeStatusRepository getEmployeeStatusRepository() {
        return employeeStatusRepository;
    }

    public Iterable<EmployeeStatusDto> findAllEmployeeStatuses() {

        Iterable<EmployeeStatusEntity> statusList = employeeStatusRepository.findAll();

        return ((Collection<EmployeeStatusEntity>) statusList)
                .stream()
                .map(this::convertToDto)
                .toList();

    }

    public EmployeeStatusDto createEmployeeStatus(EmployeeStatusDto dto) {
        EmployeeStatusEntity entity = convertToEntity(dto);
        entity.setStatusId(null);
        return convertToDto(employeeStatusRepository.save(entity));
    }

    public EmployeeStatusDto updateEmployeeStatus(EmployeeStatusDto dto) {
        EmployeeStatusEntity existingEntity = employeeStatusRepository.findById(dto.getId());
        existingEntity.setStatus(dto.getStatus());
        return convertToDto(employeeStatusRepository.save(existingEntity));
    }

    public void deleteEmployeeStatus(Long id) {
        employeeStatusRepository.deleteById(id);
    }

    public EmployeeStatusDto convertToDto(EmployeeStatusEntity entity) {

        EmployeeStatusDto dto = new EmployeeStatusDto();
        dto.setId(entity.getStatusId());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    public EmployeeStatusEntity convertToEntity(EmployeeStatusDto dto) {

        EmployeeStatusEntity entity = new EmployeeStatusEntity();
        entity.setStatusId(dto.getId());
        entity.setStatus(dto.getStatus());
        return entity;
    }

}
