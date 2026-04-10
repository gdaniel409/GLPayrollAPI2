package com.gdaniel.glpayroll.adminarea.role.entity;

import java.util.List;

import com.gdaniel.glpayroll.abstractarea.entities.UsersRolesEntity;
import com.gdaniel.glpayroll.adminarea.user.entity.UserEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tblroles")
public class RoleEntity {

    @Id // Specifies the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "LONG") // Configures the primary
    @Column(name = "colroleid")
    private Long roleId;

    @Column(name = "colrolename", nullable = false, length = 45)
    private String roleName;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UsersRolesEntity> linkedusers = new java.util.ArrayList<>();

}
