package com.gdaniel.glpayroll.userarea.document.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gdaniel.glpayroll.userarea.document.entity.DocumentEntity;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {

    // void saveDocument(String employeeId, String string, String string2, String
    // string3, String originalFilename);

    @Query("SELECT d FROM DocumentEntity d WHERE d.employee.id = :employeeId")
    Iterable<DocumentEntity> findByEmployeeId(@Param("employeeId") Long employeeId);
}
