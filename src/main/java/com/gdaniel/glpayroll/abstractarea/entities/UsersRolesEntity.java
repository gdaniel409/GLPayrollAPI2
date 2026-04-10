package com.gdaniel.glpayroll.abstractarea.entities;

import com.gdaniel.glpayroll.adminarea.role.entity.RoleEntity;
import com.gdaniel.glpayroll.adminarea.user.entity.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "tblusersroles")
public class UsersRolesEntity {

    @Id // Specifies the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "LONG") // Configures the primary
    @Column(name = "coluserroleid")
    private Long roleId;

    @ManyToOne
    @JoinColumn(name = "colroleId", nullable = false)
    private RoleEntity role;

    @ManyToOne
    @JoinColumn(name = "coluserId", nullable = false)
    private UserEntity user;

}
