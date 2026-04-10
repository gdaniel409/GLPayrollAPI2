package com.gdaniel.glpayroll.adminarea.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gdaniel.glpayroll.adminarea.role.entity.RoleEntity;
import com.gdaniel.glpayroll.adminarea.user.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
        @Query("" +
                        "SELECT CASE WHEN COUNT(u) > 0 THEN " +
                        "TRUE ELSE FALSE END " +
                        "FROM UserEntity u " +
                        "WHERE u.userName = ?1")
        Boolean selectExistsUserName(String userName);

        @Query("SELECT u FROM UserEntity u JOIN u.employee e WHERE e.employeeId = ?1")
        UserEntity findUserByEmployeeId(long employeeId);

        // @Column(unique = true) is needed in entity
        @Query("" +
                        "SELECT u " +
                        "FROM UserEntity u " +
                        "WHERE u.userName = ?1")
        UserEntity findByUserName(String userName);

        @Query("" +
                        "SELECT r " +
                        "FROM RoleEntity r " +
                        "JOIN r.linkedusers lu " +
                        "JOIN lu.user u " +
                        "WHERE u.userName = ?1")
        List<RoleEntity> findRolesByUserName(String userName);

        @Query("" +
                        "SELECT r " +
                        "FROM RoleEntity r " +
                        "JOIN r.linkedusers lu " +
                        "JOIN lu.user u " +
                        "WHERE u.userId = ?1")
        List<RoleEntity> findRolesByUserId(long userId);

        // @Query("SELECT DISTINCT u FROM UserEntity u JOIN FETCH u.roles " +
        // "JOIN FETCH u.employee e " +
        // "WHERE u.userName = :userName")
        @Query("" +
                        "SELECT DISTINCT u FROM UserEntity u " +
                        "WHERE u.userName = ?1")
        UserEntity findUsersWithRolesByUserName(String userName);

}
