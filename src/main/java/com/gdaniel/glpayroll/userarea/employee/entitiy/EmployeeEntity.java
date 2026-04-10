package com.gdaniel.glpayroll.userarea.employee.entitiy;

import java.time.LocalDateTime;
import java.util.List;

import com.gdaniel.glpayroll.adminarea.employeestatus.entity.EmployeeStatusEntity;
import com.gdaniel.glpayroll.adminarea.ratetype.entity.RateTypeEntity;
import com.gdaniel.glpayroll.adminarea.user.entity.UserEntity;
import com.gdaniel.glpayroll.userarea.document.entity.DocumentEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tblemployees")
public class EmployeeEntity {

    @Id // Specifies the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "LONG") // Configures the primary
    @Column(name = "colemployeeid")
    private Long employeeId;

    @Column(name = "colfirstname", nullable = false, length = 45)
    private String firstName;

    @Column(name = "collastname", nullable = false, length = 45)
    private String lastName;

    @Column(name = "colmiddlename", nullable = true, length = 45)
    private String middleName;

    @ManyToOne
    @JoinColumn(name = "colemployeestatusid", nullable = false)
    private EmployeeStatusEntity employeeStatus;

    @Column(name = "coldatehired", nullable = false)
    private LocalDateTime dateHired;

    @Column(name = "coldateterminated", nullable = true)
    private LocalDateTime dateTerminated;

    @Column(name = "colssn", nullable = false, length = 11)
    private String ssn;

    @Column(name = "coltelephonelandline", nullable = true, length = 15)
    private String telephoneLandline;

    @Column(name = "coltelephonecell", nullable = true, length = 15)
    private String telephoneCell;

    @Column(name = "colemail", nullable = false, length = 100)
    private String email;

    @Column(name = "colrate", nullable = false)
    private Double rate;

    @Column(name = "coltitle", nullable = false, length = 100)
    private String title;

    @Column(name = "colcompanyid", nullable = false)
    private Long companyId;

    @Column(name = "colemployeenumber", nullable = false, length = 50)
    private String employeeNumber;

    @ManyToOne
    @JoinColumn(name = "colratetype", nullable = false) //
    // Specifies the foreign key column in the
    private RateTypeEntity ratetype;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DocumentEntity> documents = new java.util.ArrayList<>();

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserEntity user;
}
