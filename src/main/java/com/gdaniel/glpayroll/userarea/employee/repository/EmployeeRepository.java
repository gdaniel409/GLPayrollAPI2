package com.gdaniel.glpayroll.userarea.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.gdaniel.glpayroll.userarea.employee.entitiy.EmployeeEntity;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    @Query("SELECT e FROM EmployeeEntity e WHERE e.employeeId = ?1")
    EmployeeEntity findByEmployeeId(long employeeId);
}
