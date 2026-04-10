package com.gdaniel.glpayroll.userarea.employee.service;

import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gdaniel.glpayroll.adminarea.employeestatus.entity.EmployeeStatusEntity;
import com.gdaniel.glpayroll.adminarea.employeestatus.service.EmployeeStatusService;
import com.gdaniel.glpayroll.adminarea.ratetype.entity.RateTypeEntity;
import com.gdaniel.glpayroll.adminarea.ratetype.service.RateTypeService;
import com.gdaniel.glpayroll.userarea.employee.dto.EmployeeDto;
import com.gdaniel.glpayroll.userarea.employee.entitiy.EmployeeEntity;
import com.gdaniel.glpayroll.userarea.employee.repository.EmployeeRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeStatusService employeeStatusService;
    private final RateTypeService rateTypeService;

    public Iterable<EmployeeDto> findAllWithDocCount(Pageable pageable) {

        int skip = pageable.getPageSize() * pageable.getPageNumber();

        return employeeRepository.findAll()
                .stream()
                .skip(skip).limit(pageable.getPageSize())

                .map(this::convertToDto)
                .collect(Collectors.toList());

    }

    public EmployeeRepository getEmployeeRepository() {
        return employeeRepository;
    }

    public EmployeeDto findById(long id) {
        return convertToDto(findEntityOrThrow(id));
    }

    public EmployeeDto createEmployee(EmployeeDto dto) {
        // Implement business logic related to employees here

        EmployeeEntity entity = convertToEntity(dto);
        entity.setEmployeeId(null);
        return convertToDto(employeeRepository.save(entity));
    }

    public EmployeeDto updateEmployee(EmployeeDto employeeDto) {
        // Implement business logic related to employees here

        EmployeeEntity existingEmployeeEntity = findEntityOrThrow(employeeDto.getId());

        existingEmployeeEntity.setFirstName(employeeDto.getFirstName());
        existingEmployeeEntity.setLastName(employeeDto.getLastName());
        existingEmployeeEntity.setMiddleName(employeeDto.getMiddleName());
        existingEmployeeEntity.setDateHired(employeeDto.getDateHired());
        existingEmployeeEntity.setDateTerminated(employeeDto.getDateTerminated());
        existingEmployeeEntity.setSsn(employeeDto.getSsn());
        existingEmployeeEntity.setEmail(employeeDto.getEmail());
        existingEmployeeEntity.setTelephoneLandline(employeeDto.getTelephoneLandline());
        existingEmployeeEntity.setTelephoneCell(employeeDto.getTelephoneCell());
        existingEmployeeEntity.setRate(employeeDto.getRate());

        EmployeeStatusEntity statusEntity = employeeStatusService.getEmployeeStatusRepository()
                .getReferenceById(employeeDto.getEmployeeStatus().getId());

        existingEmployeeEntity.setEmployeeStatus(statusEntity);

        RateTypeEntity rateTypeEntity = rateTypeService.getRateTypeRepository()
                .getReferenceById(employeeDto.getRateType().getId());

        existingEmployeeEntity.setRatetype(rateTypeEntity);

        existingEmployeeEntity.setTitle(employeeDto.getTitle());
        existingEmployeeEntity.setCompanyId(employeeDto.getCompanyId());
        existingEmployeeEntity.setEmployeeNumber(employeeDto.getEmployeeNumber());

        return convertToDto(employeeRepository.save(existingEmployeeEntity));

    }

    public void deleteEmployee(long id) {
        employeeRepository.deleteById(id);
    }

    private EmployeeEntity findEntityOrThrow(final long id) {
        return employeeRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));
    }

    public EmployeeDto convertToDto(EmployeeEntity entity) {

        EmployeeDto dto = new EmployeeDto();

        dto.setId(entity.getEmployeeId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setMiddleName(entity.getMiddleName());
        dto.setDateHired(entity.getDateHired());
        dto.setSsn(entity.getSsn());
        dto.setDateTerminated(entity.getDateTerminated());
        dto.setEmail(entity.getEmail());
        dto.setTelephoneLandline(entity.getTelephoneLandline());
        dto.setTelephoneCell(entity.getTelephoneCell());
        dto.setRate(entity.getRate());
        dto.setRateType(rateTypeService.convertToDto(entity.getRatetype()));
        dto.setEmployeeStatus(employeeStatusService.convertToDto(entity.getEmployeeStatus()));
        dto.setTitle(entity.getTitle());
        dto.setCompanyId(entity.getCompanyId());
        dto.setEmployeeNumber(entity.getEmployeeNumber());
        dto.setDocumentCount(entity.getDocuments().size());

        if (entity.getUser() != null) {
            dto.setUserId(entity.getUser().getUserId());
        } else {
            dto.setUserId(-1);
        }

        return dto;

    }

    public EmployeeEntity convertToEntity(EmployeeDto dto) {

        EmployeeEntity entity = new EmployeeEntity();

        entity.setEmployeeId(dto.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setMiddleName(dto.getMiddleName());
        entity.setDateHired(dto.getDateHired());
        entity.setDateTerminated(dto.getDateTerminated());
        entity.setSsn(dto.getSsn());
        entity.setEmail(dto.getEmail());
        entity.setTelephoneLandline(dto.getTelephoneLandline());
        entity.setTelephoneCell(dto.getTelephoneCell());
        entity.setRate(dto.getRate());

        EmployeeStatusEntity statusEntity = employeeStatusService.getEmployeeStatusRepository()
                .getReferenceById(dto.getEmployeeStatus().getId());

        entity.setEmployeeStatus(statusEntity);

        RateTypeEntity rateTypeEntity = rateTypeService.getRateTypeRepository()
                .getReferenceById(dto.getRateType().getId());

        entity.setRatetype(rateTypeEntity);

        entity.setTitle(dto.getTitle());
        entity.setCompanyId(dto.getCompanyId());
        entity.setEmployeeNumber(dto.getEmployeeNumber());

        return entity;
    }

}
