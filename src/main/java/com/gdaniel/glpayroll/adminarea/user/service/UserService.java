package com.gdaniel.glpayroll.adminarea.user.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.gdaniel.glpayroll.adminarea.role.repository.RoleRepository;
import com.gdaniel.glpayroll.adminarea.role.service.RoleService;
import com.gdaniel.glpayroll.abstractarea.entities.UsersRolesEntity;
import com.gdaniel.glpayroll.adminarea.role.dto.RoleSelectDto;
import com.gdaniel.glpayroll.adminarea.role.entity.RoleEntity;

import com.gdaniel.glpayroll.adminarea.user.dto.UserDto;
import com.gdaniel.glpayroll.adminarea.user.dto.UserWithRolesDto;
import com.gdaniel.glpayroll.adminarea.user.entity.UserEntity;
import com.gdaniel.glpayroll.adminarea.user.repository.UserRepository;
import com.gdaniel.glpayroll.exception.BadRequestException;
import com.gdaniel.glpayroll.exception.NotFoundException;
import com.gdaniel.glpayroll.userarea.employee.entitiy.EmployeeEntity;
import com.gdaniel.glpayroll.userarea.employee.repository.EmployeeRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserService {

    private final RoleRepository roleRepository;
    private final RoleService roleService;
    private final UserRepository userRepository;
    private final EmployeeRepository erepos;

    public UserEntity findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public UserEntity findUsersWithRolesByUserName(String userName) {
        return userRepository.findUsersWithRolesByUserName(userName);
    }

    public List<RoleEntity> findRolesByUserName(String userName) {
        return userRepository.findRolesByUserName(userName);
    }

    public List<UserDto> findAllUsers() {
        var userEntityList = new ArrayList<>(userRepository.findAll());

        return userEntityList
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public UserWithRolesDto findUserByEmployeeId(final Long id) {

        // select user
        UserEntity user = userRepository.findUserByEmployeeId(id);

        // select roles belonging to user
        List<RoleEntity> userRoles = userRepository.findRolesByUserId(user.getUserId());

        // find all roles left join user
        List<RoleEntity> rolesEntities = this.roleRepository
                .findAll();

        List<RoleSelectDto> rolesDto = new ArrayList<RoleSelectDto>();

        rolesEntities.forEach(roleEntity -> {

            rolesDto.add(this.roleService.convertToRoleSelectedDto(roleEntity, userRoles));

        });

        UserWithRolesDto userWithRoles = new UserWithRolesDto();

        RoleSelectDto[] rarray = rolesDto.toArray(RoleSelectDto[]::new);

        userWithRoles.setRoles(rarray);
        userWithRoles.setPassword("");
        userWithRoles.setUsername(user.getUsername());

        return userWithRoles;
    }

    public UserDto createUser(UserWithRolesDto userDto)
            throws NoSuchAlgorithmException {

        if (userDto.getPassword().isBlank())
            throw new IllegalArgumentException(
                    "Password is required");

        var existsUserName = userRepository.selectExistsUserName(userDto.getUsername());
        if (Boolean.TRUE.equals(existsUserName))
            throw new BadRequestException(
                    "Username " + userDto.getUsername() + " taken");

        byte[] salt = createSalt();
        byte[] hashedPassword = createPasswordHash(userDto.getPassword(), salt);

        String hexString = HexFormat.of().formatHex(hashedPassword);
        String saltString = HexFormat.of().formatHex(salt);

        UserEntity user = new UserEntity();

        if (userDto.getEmployeeId() != -1) {

            EmployeeEntity empEntity = erepos.getReferenceById(userDto.getEmployeeId());
            user.setEmployee(empEntity);
        }

        RoleSelectDto[] rolesArray = userDto.getRoles();
        ArrayList<UsersRolesEntity> list = new ArrayList<UsersRolesEntity>();

        for (int n = 0; n < rolesArray.length; n++) {

            if (rolesArray[n].getSelected()) {

                UsersRolesEntity userRole = new UsersRolesEntity();
                RoleEntity rentity = this.roleRepository.getReferenceById(rolesArray[n].getId());
                userRole.setRole(rentity);
                userRole.setUser(user);

                list.add(userRole);

            }
        }

        user.setLinkedroles(list);
        user.setUserName(userDto.getUsername());
        user.setStoredSalt(saltString);
        user.setStoredHash(hexString);

        userRepository.save(user);
        return convertToDto(user);
    }

    public UserDto updateUser(UserWithRolesDto userDto)
            throws NoSuchAlgorithmException {

        UserEntity user = findOrThrow(userDto.getId());

        if (!userDto.getUsername().isBlank()) {
            user.setUserName(userDto.getUsername());
        }

        if (!userDto.getPassword().isBlank()) {

            byte[] salt = createSalt();
            byte[] hashedPassword = createPasswordHash(userDto.getPassword(), salt);

            String hexString = HexFormat.of().formatHex(hashedPassword);
            String saltString = HexFormat.of().formatHex(salt);

            user.setStoredSalt(saltString);
            user.setStoredHash(hexString);
        }

        List<UsersRolesEntity> roleset = user.getLinkedroles();
        roleset.clear();

        RoleSelectDto[] roles = userDto.getRoles();

        for (int n = 0; n < roles.length; n++) {

            if (roles[n].getSelected()) {

                UsersRolesEntity urentity = new UsersRolesEntity();
                urentity.setUser(user);
                RoleEntity rentity = this.roleRepository.getReferenceById(roles[n].getId());
                urentity.setRole(rentity);

                roleset.add(urentity);
            }
        }

        userRepository.save(user);
        return userDto;
    }

    public void removeUserById(Long id) {
        findOrThrow(id);
        userRepository.deleteById(id);
    }

    private byte[] createSalt() {
        var random = new SecureRandom();
        var salt = new byte[128];
        random.nextBytes(salt);

        return salt;
    }

    private byte[] createPasswordHash(String password, byte[] salt)
            throws NoSuchAlgorithmException {
        var md = MessageDigest.getInstance("SHA-512");
        md.update(salt);

        return md.digest(password.getBytes(StandardCharsets.UTF_8));
    }

    private UserEntity findOrThrow(final Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(
                        () -> new NotFoundException("User by id " + id + " was not found"));
    }

    private UserDto convertToDto(UserEntity entity) {
        UserDto dto = new UserDto();

        dto.setId(entity.getUserId());
        dto.setUsername(entity.getUsername());
        dto.setPassword("");
        dto.setEmployeeId(entity.getEmployee().getEmployeeId());

        return dto;
    }

}
