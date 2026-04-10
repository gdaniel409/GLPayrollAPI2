package com.gdaniel.glpayroll.adminarea.user.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gdaniel.glpayroll.adminarea.user.dto.UserDto;
import com.gdaniel.glpayroll.adminarea.user.dto.UserWithRolesDto;
import com.gdaniel.glpayroll.adminarea.user.service.UserService;

import jakarta.validation.Valid;
import java.security.NoSuchAlgorithmException;

@AllArgsConstructor
@RestController
@RequestMapping("api/admin/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/list")
    public ResponseEntity<Iterable<UserDto>> getUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/getuserbyemployeeid")
    public ResponseEntity<UserWithRolesDto> getUserByEmployeeId(Long id) {
        return ResponseEntity.ok(userService.findUserByEmployeeId(id));
    }

    @GetMapping("/delete")
    public ResponseEntity<Void> delete(long id) {

        userService.removeUserById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserWithRolesDto userDto)
            throws NoSuchAlgorithmException {

        return ResponseEntity.ok(userService.createUser(userDto));
    }

    @PostMapping("/update")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserWithRolesDto userDto)
            throws NoSuchAlgorithmException {

        return ResponseEntity.ok(userService.updateUser(userDto));
    }

}
