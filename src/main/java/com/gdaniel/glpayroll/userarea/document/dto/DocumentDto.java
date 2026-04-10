package com.gdaniel.glpayroll.userarea.document.dto;

import java.time.LocalDateTime;

import com.gdaniel.glpayroll.userarea.employee.dto.EmployeeDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentDto {

    private long id;
    private String alias;
    private LocalDateTime dateCreated;
    private String url;
    private EmployeeDto employee;
    private String originalFileName;
    private String mimeType;

}
