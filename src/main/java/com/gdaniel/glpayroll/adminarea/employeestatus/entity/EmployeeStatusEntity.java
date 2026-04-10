package com.gdaniel.glpayroll.adminarea.employeestatus.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gdaniel.glpayroll.userarea.employee.entitiy.EmployeeEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tblemployeestatuses")
public class EmployeeStatusEntity {

    @Id // Specifies the primary key
    @Column(name = "colstatusid", nullable = false)
    private Long statusId;

    @JsonManagedReference
    @OneToMany(mappedBy = "employeeStatus", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EmployeeEntity> employees = new HashSet<>();

    @Column(name = "colstatus", nullable = false, length = 45)
    private String status;

}
