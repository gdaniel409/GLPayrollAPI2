package com.gdaniel.glpayroll.userarea.document.service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.gdaniel.glpayroll.exception.BadRequestException;
import com.gdaniel.glpayroll.userarea.document.dto.DocumentDto;
import com.gdaniel.glpayroll.userarea.document.entity.DocumentEntity;
import com.gdaniel.glpayroll.userarea.document.repository.DocumentRepository;
import com.gdaniel.glpayroll.userarea.employee.service.EmployeeService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class DocumentService {
    private DocumentRepository documentRepository;
    private EmployeeService employeeService;

    public DocumentDto saveDocument(DocumentDto theData) {
        // Convert DTO to Entity

        long employeeId = theData.getEmployee().getId();

        DocumentEntity entity = new DocumentEntity();

        entity.setEmployee(employeeService.getEmployeeRepository()
                .findById(employeeId).orElseThrow(() -> new BadRequestException("Employee not found")));

        entity.setMimeType(theData.getMimeType());
        entity.setAlias(theData.getAlias());
        entity.setOriginalFileName(theData.getOriginalFileName());
        entity.setUrl(theData.getUrl());
        entity.setDatecreated(java.time.LocalDateTime.now());
        // Save Entity to the database
        documentRepository.save(entity);
        return convertToDto(entity);
    }

    public DocumentDto findById(long id) {

        DocumentEntity entity = documentRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Document not found"));

        return convertToDto(entity);

    }

    public Iterable<DocumentDto> findAllById(long id) {

        Iterable<DocumentEntity> entities = documentRepository.findByEmployeeId(id);

        return StreamSupport.stream(entities.spliterator(), false)
                .map(this::convertToDto)
                .collect(Collectors.toList());

    }

    public void deleteDocument(long id) {
        documentRepository.deleteById(id);
    }

    public DocumentDto convertToDto(DocumentEntity entity) {
        DocumentDto dto = new DocumentDto();

        dto.setId(entity.getDocumentId());

        dto.setEmployee(employeeService.convertToDto(entity.getEmployee()));
        dto.setAlias(entity.getAlias());
        dto.setMimeType(entity.getMimeType());
        dto.setOriginalFileName(entity.getOriginalFileName());
        dto.setUrl(entity.getUrl());
        dto.setDateCreated(entity.getDatecreated());

        return dto;
    }

    public DocumentEntity convertToEntity(DocumentDto dto) {
        DocumentEntity entity = new DocumentEntity();

        entity.setDocumentId(dto.getId());
        entity.setEmployee(employeeService.getEmployeeRepository()
                .findById(dto.getEmployee().getId()).orElseThrow(() -> new BadRequestException("Employee not found")));

        entity.setAlias(dto.getAlias());
        entity.setMimeType(dto.getMimeType());
        entity.setOriginalFileName(dto.getOriginalFileName());
        entity.setUrl(dto.getUrl());
        entity.setDatecreated(dto.getDateCreated());

        return entity;
    }

}
