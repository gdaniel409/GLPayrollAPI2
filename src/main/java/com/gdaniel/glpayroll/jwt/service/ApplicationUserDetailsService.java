package com.gdaniel.glpayroll.jwt.service;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gdaniel.glpayroll.adminarea.role.entity.RoleEntity;
import com.gdaniel.glpayroll.adminarea.user.entity.UserEntity;
import com.gdaniel.glpayroll.adminarea.user.service.UserService;
import com.gdaniel.glpayroll.jwt.model.UserPrincipal;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.List;

@Service
@AllArgsConstructor
public class ApplicationUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userName)
            throws UsernameNotFoundException {
        return new UserPrincipal(userService.findByUserName(userName));
    }

    public UserEntity findUsersWithRolesByUsername(String userName) {
        return userService.findUsersWithRolesByUserName(userName);
    }

    public List<RoleEntity> loadUserRolesByUsername(String userName) {
        return userService.findRolesByUserName(userName);
    }

    public UserEntity authenticate(String userName, String password)
            throws NoSuchAlgorithmException {

        if (userName.isEmpty() || password.isEmpty()) {
            throw new BadCredentialsException("Unauthorized");
        }

        UserEntity userEntity = userService.findByUserName(userName);

        if (userEntity == null) {
            throw new BadCredentialsException("Unauthorized");
        }

        Boolean verified = verifyPasswordHash(
                password,
                userEntity.getStoredHash(),
                userEntity.getStoredSalt());

        if (verified != null && !verified) {
            throw new BadCredentialsException("Unauthorized");
        }

        return userEntity;
    }

    // Password entered by the user is hashed and compared with the stored hash and
    // salt in the database.
    private Boolean verifyPasswordHash(
            String password,
            String hashHex,
            String saltHex) throws NoSuchAlgorithmException {

        if (password.isBlank() || password.isEmpty())
            throw new IllegalArgumentException(
                    "Password cannot be empty or whitespace only string.");

        if (hashHex.length() != 128)
            throw new IllegalArgumentException(
                    "Invalid length of password hash (64 bytes expected)");

        if (saltHex.length() != 256)
            throw new IllegalArgumentException(
                    "Invalid length of password salt (64 bytes expected).");

        byte[] storedHash = HexFormat.of().parseHex(hashHex);
        byte[] storedSalt = HexFormat.of().parseHex(saltHex);

        var md = MessageDigest.getInstance("SHA-512");
        md.update(storedSalt);

        var computedHash = md.digest(password.getBytes(StandardCharsets.UTF_8));

        for (int i = 0; i < computedHash.length; i++) {
            if (computedHash[i] != storedHash[i])
                return false;
        }

        // The above for loop is the same as below

        return MessageDigest.isEqual(computedHash, storedHash);
    }
}
