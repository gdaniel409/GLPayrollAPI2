package com.gdaniel.glpayroll.adminarea.ratetype.entity;

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
@Table(name = "tblratetypes")
public class RateTypeEntity {

    @Id
    @Column(name = "colratetypeid", nullable = false)
    private Long rateTypeId;

    @JsonManagedReference
    @OneToMany(mappedBy = "ratetype", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EmployeeEntity> employees = new HashSet<>();

    @Column(name = "colratetype", nullable = false, length = 45)
    private String rateType;

}
