package com.project.tim7.helper;

import com.project.tim7.dto.NewsletterDTO;
import com.project.tim7.model.Newsletter;

public class NewsletterMapper implements MapperInterface<Newsletter, NewsletterDTO> {

	@Override
	public Newsletter toEntity(NewsletterDTO dto) {
		return new Newsletter(dto.getId(), dto.getName(), dto.getDescription(), dto.getPublishedDate());
	}

	@Override
	public NewsletterDTO toDto(Newsletter entity) {
		return new NewsletterDTO(entity.getId(), entity.getName(), entity.getDescription(), entity.getPublishedDate(), entity.getCulturalOffer().getId(), entity.getPicture().getPicture());
	}

}
