package com.gdaniel.glpayroll.adminarea.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private long id;
    private String username;
    private String password;
    private long employeeId;
}
