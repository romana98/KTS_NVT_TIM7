package com.project.tim7.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.tim7.dto.NewsletterDTO;
import com.project.tim7.model.CulturalOffer;
import com.project.tim7.model.Newsletter;
import com.project.tim7.model.Picture;
import com.project.tim7.repository.CulturalOfferRepository;
import com.project.tim7.repository.NewsletterRepository;
import com.project.tim7.repository.PictureRepository;

@Service
public class NewsletterService implements ServiceInterface<Newsletter> {
	
	@Autowired
	NewsletterRepository newsletterRepo;
	
	@Autowired 
	CulturalOfferRepository culturalOfferRepo;
	
	@Autowired
	PictureRepository pictureRepo;

	@Override
	public List<Newsletter> findAll() {
		return null;
	}

	@Override
	public Newsletter findOne(int id) {
		return newsletterRepo.findById(id).orElse(null);
	}

	@Override
	public boolean saveOne(Newsletter entity) {
		if (findOne(entity.getId()) != null) 
			return false;		
		newsletterRepo.save(entity);
		return true;
	}

	@Override
	public boolean saveAll(List<Newsletter> entities) {
		return false;
	}

	@Override
	public boolean delete(int id) {
		return false;
	}
	
	public boolean saveNewsletter(Newsletter entity, int culturalOfferId, String pictureStr) {
		CulturalOffer culturalOffer = findOneCulturalOffer(culturalOfferId);
		if (culturalOffer == null) 
			return false;
		entity.setCulturalOffer(culturalOffer);
		
		Picture picture = new Picture(pictureStr);
		pictureRepo.save(picture);
		entity.setPicture(picture);
		
		if (saveOne(entity))
			return true;
		return false;
	}
	
	public CulturalOffer findOneCulturalOffer(int id) {
		return culturalOfferRepo.findById(id).orElse(null);
	}

}
