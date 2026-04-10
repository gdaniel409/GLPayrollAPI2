package com.gdaniel.glpayroll.adminarea.user.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.gdaniel.glpayroll.abstractarea.entities.UsersRolesEntity;
import com.gdaniel.glpayroll.userarea.employee.entitiy.EmployeeEntity;

@Entity
@Data
@NoArgsConstructor
@Table(name = "tblusers")
public class UserEntity implements UserDetails {

    @Id // Specifies the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "LONG") // Configures the primary
    @Column(name = "coluserid", nullable = false, updatable = false)
    private Long userId;

    @Column(name = "colusername", nullable = false, unique = true)
    private String userName;

    @OneToOne
    @JoinColumn(name = "colemployeeid", nullable = true)
    private EmployeeEntity employee;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UsersRolesEntity> linkedroles = new java.util.ArrayList<>();

    @Column(name = "colstoredhash", nullable = false, length = 128)
    private String storedHash;

    @Column(name = "colstoredsalt", nullable = false, length = 256)
    private String storedSalt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return this.linkedroles.stream()
                .map(userrole -> new SimpleGrantedAuthority(userrole.getRole().getRoleName()))
                .collect(Collectors.toList());

    }

    @Override
    public @Nullable String getPassword() {
        return storedHash;
    }

    @Override
    public String getUsername() {
        return userName;
    }

}
