package com.gdaniel.glpayroll.userarea.document.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentUploadDto {

    private long employeeId;
    private String[] aliases;
    private String[] mimeTypes;
    private MultipartFile[] files;

}
