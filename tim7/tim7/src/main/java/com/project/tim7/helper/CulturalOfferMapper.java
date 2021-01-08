package com.project.tim7.helper;

import com.project.tim7.dto.CulturalOfferDTO;
import com.project.tim7.model.CulturalOffer;
import com.project.tim7.model.Picture;

import java.util.ArrayList;
import java.util.List;

public class CulturalOfferMapper implements MapperInterface<CulturalOffer,CulturalOfferDTO>{

    @Override
    public CulturalOffer toEntity(CulturalOfferDTO dto) {
        return new CulturalOffer(dto.getId(),dto.getName(),dto.getEndDate(),dto.getDescription(),dto.getStartDate());
    }

    @Override
    public CulturalOfferDTO toDto(CulturalOffer entity) {
        ArrayList<String> pictures = new ArrayList<String>();
        for(Picture pic:entity.getPictures()){
            pictures.add(pic.getPicture());
        }
        CulturalOfferDTO cdto = new CulturalOfferDTO(entity.getId(), entity.getName(), entity.getDescription(), entity.getStartDate(),
                entity.getEndDate(),entity.getLocation().getId(),entity.getSubcategory().getId(),pictures);

        cdto.setSubcategoryName(entity.getSubcategory().getName());
        cdto.setCategoryName(entity.getSubcategory().getCategory().getName());
        cdto.setLocationName(entity.getLocation().getName());
        cdto.setLatitude(entity.getLocation().getLatitude());
        cdto.setLongitude(entity.getLocation().getLongitude());
        return cdto;
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
