package com.gdaniel.glpayroll.adminarea.role.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gdaniel.glpayroll.adminarea.role.dto.RoleDto;
import com.gdaniel.glpayroll.adminarea.role.dto.RoleSelectDto;
import com.gdaniel.glpayroll.adminarea.role.service.RoleService;

import lombok.AllArgsConstructor;

//Complete the RoleController class with necessary annotations and imports.
// This class will handle HTTP requests related to roles in the admin section of the application.

@RestController
@AllArgsConstructor
@RequestMapping("api/admin/role")
public class RoleController {

    private RoleService roleService;

    @GetMapping("/list")
    public ResponseEntity<Iterable<RoleDto>> listRoles() {

        Iterable<RoleDto> roleList = roleService.findAllRoles();
        return ResponseEntity.ok(roleList);

    }

    @GetMapping("/listforuserid")
    public ResponseEntity<Iterable<RoleSelectDto>> listRolesForUser(long userId) {

        Iterable<RoleSelectDto> roleList;

        roleList = roleService.findAllRolesForUserId(userId);

        return ResponseEntity.ok(roleList);

    }

    @GetMapping("/create")
    public ResponseEntity<RoleDto> create(RoleDto dto) {

        RoleDto savedRole = roleService.createRole(dto);
        return ResponseEntity.ok(savedRole);
    }

    @GetMapping("/edit")
    public ResponseEntity<RoleDto> edit(RoleDto dto) {

        RoleDto updatedRole = roleService.updateRole(dto);
        return ResponseEntity.ok(updatedRole);
    }

    @GetMapping("/delete")
    public ResponseEntity<Void> delete(long id) {

        roleService.deleteRole(id);
        return ResponseEntity.ok().build();
    }

}
