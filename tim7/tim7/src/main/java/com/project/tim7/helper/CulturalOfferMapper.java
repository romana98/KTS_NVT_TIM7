package com.project.tim7.helper;

import com.project.tim7.dto.CulturalOfferDTO;
import com.project.tim7.model.CulturalOffer;

public class CulturalOfferMapper implements MapperInterface<CulturalOffer,CulturalOfferDTO>{

    @Override
    public CulturalOffer toEntity(CulturalOfferDTO dto) {
        return new CulturalOffer(dto.getId(),dto.getDescription(),dto.getEndDate(),dto.getName(),dto.getStartDate());
    }

    @Override
    public CulturalOfferDTO toDto(CulturalOffer entity) {
        return null;
    }
}
