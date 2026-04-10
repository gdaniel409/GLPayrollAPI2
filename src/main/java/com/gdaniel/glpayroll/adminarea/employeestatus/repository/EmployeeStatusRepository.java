package com.gdaniel.glpayroll.adminarea.employeestatus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.gdaniel.glpayroll.adminarea.employeestatus.entity.EmployeeStatusEntity;

@Repository
public interface EmployeeStatusRepository extends JpaRepository<EmployeeStatusEntity, Long> {

    @Query("SELECT e FROM EmployeeStatusEntity e WHERE e.statusId = ?1")
    EmployeeStatusEntity findById(long id);

    @Query("DELETE FROM EmployeeStatusEntity e WHERE e.statusId = :id")
    void deleteById(Long id);

}
