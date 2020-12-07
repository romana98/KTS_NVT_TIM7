package com.project.tim7.helper;

import com.project.tim7.dto.NewsletterDTO;
import com.project.tim7.dto.NewsletterDetailsDTO;
import com.project.tim7.model.Newsletter;

public class NewsletterMapper implements MapperInterface<Newsletter, NewsletterDetailsDTO> {

	@Override
	public Newsletter toEntity(NewsletterDetailsDTO dto) {
		return new Newsletter(dto.getId(), dto.getName(), dto.getDescription(), dto.getPublishedDate());
	}
	
	@Override
	public NewsletterDetailsDTO toDto(Newsletter entity) {
		return null;
	}

	public NewsletterDetailsDTO toNewsletterDetailsDto(Newsletter entity) {
		return new NewsletterDetailsDTO(entity.getId(), entity.getName(), entity.getDescription(), entity.getPublishedDate(), entity.getCulturalOffer().getId(), entity.getPicture().getPicture());
	}

	public NewsletterDTO toNewsletterDto(Newsletter entity) {
		return new NewsletterDTO(entity.getId(), entity.getName(), entity.getPublishedDate(), entity.getCulturalOffer().getId());
	}

}
