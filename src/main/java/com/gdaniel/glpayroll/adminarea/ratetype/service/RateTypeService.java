package com.gdaniel.glpayroll.adminarea.ratetype.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import com.gdaniel.glpayroll.adminarea.ratetype.dto.RateTypeDto;
import com.gdaniel.glpayroll.adminarea.ratetype.entity.RateTypeEntity;
import com.gdaniel.glpayroll.adminarea.ratetype.repository.RateTypeRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RateTypeService {

    private RateTypeRepository rateTypeRepository;

    public RateTypeRepository getRateTypeRepository() {
        return rateTypeRepository;
    }

    public Optional<RateTypeEntity> findById(Long rateTypeID) {
        return rateTypeRepository.findById(rateTypeID);
    }

    public Iterable<RateTypeDto> findAllRateTypes() {

        return rateTypeRepository.findAll().stream()
                .map(rateType -> convertToDto(rateType))
                .toList();

    }

    public void deleteById(Long rateTypeID) {
        rateTypeRepository.deleteById(rateTypeID);
    }

    public RateTypeDto create(RateTypeDto rateType) {
        return convertToDto(rateTypeRepository.save(convertToEntity(rateType)));
    }

    public RateTypeDto update(RateTypeDto rateType) {
        return convertToDto(rateTypeRepository.save(convertToEntity(rateType)));
    }

    public RateTypeDto convertToDto(RateTypeEntity rateType) {
        RateTypeDto dto = new RateTypeDto();
        dto.setId(rateType.getRateTypeId());
        dto.setRateType(rateType.getRateType());
        return dto;
    }

    public RateTypeEntity convertToEntity(RateTypeDto rateTypeDto) {
        RateTypeEntity entity = new RateTypeEntity();
        entity.setRateTypeId(rateTypeDto.getId());
        entity.setRateType(rateTypeDto.getRateType());
        return entity;
    }

}
