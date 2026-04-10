package com.gdaniel.glpayroll.userarea.document.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gdaniel.glpayroll.exception.BadRequestException;
import com.gdaniel.glpayroll.exception.NotFoundException;
import com.gdaniel.glpayroll.userarea.document.dto.DocumentDto;
import com.gdaniel.glpayroll.userarea.document.dto.DocumentUploadDto;
import com.gdaniel.glpayroll.userarea.document.service.DocumentService;
import com.gdaniel.glpayroll.userarea.employee.dto.EmployeeDto;
import com.gdaniel.glpayroll.userarea.employee.service.EmployeeService;

import org.springframework.http.MediaType;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;

import java.nio.file.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/documents")
public class DocumentController {

    private final Path root = Paths.get(System.getProperty("user.dir") + "/documents");

    private final DocumentService documentService;
    private final EmployeeService employeeService;

    @GetMapping("/list")
    public ResponseEntity<List<DocumentDto>> listDocumentsForEmployee(@RequestParam long employeeId) {

        Iterable<DocumentDto> documentsList = documentService.findAllById(employeeId);

        return ResponseEntity.ok().body(StreamSupport.stream(documentsList
                .spliterator(), false)
                .collect(Collectors.toList()));

    }

    @PostMapping("/editdocument")
    public ResponseEntity<DocumentDto> editDocument(@RequestBody DocumentDto documentDto) {

        return ResponseEntity.ok().body(documentDto);
    }

    @DeleteMapping("/deletedocument")
    public ResponseEntity<String> deleteDocument(@RequestParam long id) throws IOException {

        DocumentDto document = documentService.findById(id);
        Files.deleteIfExists(root.resolve(document.getUrl()));
        documentService.deleteDocument(id);

        return ResponseEntity.ok().body("Document deleted successfully");
    }

    @PostMapping("/postdocument")
    public ResponseEntity<String> uploadFile(
            @ModelAttribute DocumentUploadDto documents) throws IOException {

        // Ensure the directory exists
        if (!Files.exists(root)) {
            Files.createDirectories(root);
        }

        for (int i = 0; i < documents.getFiles().length; i++) {

            var file = documents.getFiles()[i];

            var originalFileName = file.getOriginalFilename();

            if (originalFileName == null || originalFileName.isEmpty()) {
                throw new BadRequestException("Original file name is missing");
            }

            var fileExtension = getFileExtension(originalFileName);
            var uniqueFileName = UUID.randomUUID().toString().replace("-", "") + "." + fileExtension;

            var alias = documents.getAliases()[i];
            var mimeType = documents.getMimeTypes()[i];

            Files.copy(file.getInputStream(), this.root.resolve(uniqueFileName));

            EmployeeDto employee = employeeService.findById(documents.getEmployeeId());

            DocumentDto dto = new DocumentDto();
            dto.setId(documents.getEmployeeId());
            dto.setUrl(uniqueFileName);
            dto.setAlias(alias);
            dto.setMimeType(mimeType);
            dto.setEmployee(employee);
            dto.setOriginalFileName(originalFileName);
            dto.setDateCreated(java.time.LocalDateTime.now());
            documentService.saveDocument(dto);

        }

        return ResponseEntity.ok("File uploaded successfully");
    }

    @GetMapping("/downloaddocument")
    public ResponseEntity<UrlResource> downloadFile(@RequestParam long id,
            @RequestParam(required = false) Boolean inline)
            throws IOException, BadRequestException {
        // Load file as Resource

        boolean isInline;

        if (inline == null) {
            isInline = true;
        } else {
            isInline = inline.booleanValue();
        }

        DocumentDto document = documentService.findById(id);

        String filename = document.getUrl();

        Path filePath = root.resolve(filename).normalize();

        if (filePath == null || !Files.exists(filePath)) {
            throw new NotFoundException("File not found: " + filename);
        }

        URI uri = filePath.toUri();

        UrlResource resource = new UrlResource(uri);

        // // Check if file exists and is readable
        // if (!((AbstractFileResolvingResource) resource).exists()
        // || !((AbstractFileResolvingResource) resource).isReadable()) {
        // throw new NotFoundException("File not found or not readable: " + filename);
        // }

        String contentType = document.getMimeType();

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        String dType;

        if (isInline) {
            dType = "inline";
        } else {
            dType = "attachment";
        }

        ContentDisposition contentDisposition = ContentDisposition.builder(dType)
                .filename(filename)
                .build();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString())
                .body(resource);
    }

    private String getFileExtension(String fileName) {
        int lastIndexOfDot = fileName.lastIndexOf('.');
        // Check if a dot exists and is not the very first character
        if (lastIndexOfDot == -1 || lastIndexOfDot == 0) {
            return ""; // No extension
        }
        return fileName.substring(lastIndexOfDot + 1);
    }

}
