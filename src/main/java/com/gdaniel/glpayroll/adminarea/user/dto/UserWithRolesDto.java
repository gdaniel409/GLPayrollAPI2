package com.gdaniel.glpayroll.adminarea.user.dto;

import com.gdaniel.glpayroll.adminarea.role.dto.RoleSelectDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserWithRolesDto extends UserDto {

    RoleSelectDto[] roles;

}
