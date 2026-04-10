package com.gdaniel.glpayroll.adminarea.ratetype.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.gdaniel.glpayroll.adminarea.ratetype.entity.RateTypeEntity;

@Repository
public interface RateTypeRepository extends JpaRepository<RateTypeEntity, Long> {

    @Query("SELECT r FROM RateTypeEntity r WHERE r.rateTypeId = ?1")
    RateTypeEntity findById(long id);

    @Query("DELETE FROM RateTypeEntity r WHERE r.rateTypeId = ?1")
    void deleteById(Long rateTypeID);

}
