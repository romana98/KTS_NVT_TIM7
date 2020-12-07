package com.project.tim7.helper;

import com.project.tim7.dto.CulturalOfferDTO;
import com.project.tim7.dto.NewsletterDetailsDTO;
import com.project.tim7.model.CulturalOffer;
import com.project.tim7.model.Newsletter;

import java.util.ArrayList;
import java.util.List;

public class CulturalOfferMapper implements MapperInterface<CulturalOffer,CulturalOfferDTO>{

    @Override
    public CulturalOffer toEntity(CulturalOfferDTO dto) {
        return new CulturalOffer(dto.getId(),dto.getDescription(),dto.getEndDate(),dto.getName(),dto.getStartDate());
    }

    @Override
    public CulturalOfferDTO toDto(CulturalOffer entity) {
        return new CulturalOfferDTO(entity.getId(), entity.getName(), entity.getDescription(), entity.getStartDate(),
                entity.getEndDate(),entity.getLocation().getId(),entity.getSubcategory().getId());
    }

    public List<CulturalOfferDTO> toCulturalOfferDTOList(List<CulturalOffer> culturalOffers) {
        ArrayList<CulturalOfferDTO> payload = new ArrayList<CulturalOfferDTO>();
        for(CulturalOffer culturalOffer : culturalOffers) {
            CulturalOfferDTO DTO = toDto(culturalOffer);
            payload.add(DTO);
        }
        return payload;
    }

}
