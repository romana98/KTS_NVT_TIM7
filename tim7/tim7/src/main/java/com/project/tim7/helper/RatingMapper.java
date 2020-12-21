package com.project.tim7.helper;

import com.project.tim7.dto.RatingDTO;
import com.project.tim7.model.Rating;

public class RatingMapper implements MapperInterface<Rating, RatingDTO> {

	@Override
	public Rating toEntity(RatingDTO dto) {

		return new Rating(dto.getId(), dto.getRate(), dto.getRegisteredId());
	}

	@Override
	public RatingDTO toDto(Rating entity) {
		return new RatingDTO(entity.getId(), entity.getRate());
	}

}
