package com.gdaniel.glpayroll.adminarea.role.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.gdaniel.glpayroll.adminarea.role.entity.RoleEntity;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

        @Query("SELECT r FROM RoleEntity r " +
                        "JOIN r.linkedusers lu " +
                        "JOIN lu.user u " +
                        "WHERE u.userName = :userName")
        public List<RoleEntity> findAllRolesByUserName(String userName);

        @Query("SELECT r FROM RoleEntity r")
        public List<RoleEntity> findAllRoles();

        @Query("SELECT DISTINCT r FROM RoleEntity r " +
                        "JOIN r.linkedusers lu " +
                        "LEFT JOIN lu.user u ON u.userId = :userId")
        public List<RoleEntity> findAllRolesByUserId(long userId);

        // @Query("SELECT DISTINCT r, u FROM UserEntity u " +
        // "LEFT JOIN u.linkedroles lr " +
        // "ON lr.user.userID=:userId " +
        // "LEFT JOIN lr.role r ")
        // public List<RoleEntity> findAllRolesByUserId2(long userId);

}
