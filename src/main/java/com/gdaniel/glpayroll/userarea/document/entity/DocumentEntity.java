package com.gdaniel.glpayroll.userarea.document.entity;

import java.time.LocalDateTime;

import com.gdaniel.glpayroll.userarea.employee.entitiy.EmployeeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbldocuments")
public class DocumentEntity {

    @Id // Specifies the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "LONG") // Configures the primary
    @Column(name = "coldocumentid")
    private Long documentId;

    @Column(name = "colalias", nullable = false, length = 100)
    private String alias;

    @Column(name = "colurl", nullable = false, length = 100)
    private String url;

    @Column(name = "coldatecreated", nullable = false)
    private LocalDateTime datecreated;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "colemployeeid", nullable = false)
    private EmployeeEntity employee;

    @Column(name = "coloriginalfilename", nullable = false, length = 100)
    private String originalFileName;

    @Column(name = "colmimetype", nullable = false, length = 45)
    private String mimeType;

}
