package com.project.tim7.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.tim7.model.CulturalOffer;
import com.project.tim7.model.Newsletter;
import com.project.tim7.model.Picture;
import com.project.tim7.repository.NewsletterRepository;
import com.project.tim7.repository.PictureRepository;

@Service
public class NewsletterService implements ServiceInterface<Newsletter> {
	
	@Autowired
	NewsletterRepository newsletterRepo;
	
	@Autowired 
	CulturalOfferService culturalOfferService;
	
	@Autowired
	PictureService pictureService;

	@Override
	public List<Newsletter> findAll() {
		return newsletterRepo.findAll();
	}
	
	@Override
	public Page<Newsletter> findAll(Pageable pageable) {
		return newsletterRepo.findAll(pageable);
	}

	@Override
	public Newsletter findOne(int id) {
		return newsletterRepo.findById(id).orElse(null);
	}

	@Override
	public boolean saveOne(Newsletter entity) {
		return false;
	}

	@Override
	public boolean saveAll(List<Newsletter> entities) {
		return false;
	}

	@Override
	public boolean delete(int id) {
		return false;
	}

	@Override
	public Object update(Object entity) {
		return false;
	}

	@Override
	public Page findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	
	public boolean saveNewsletter(Newsletter entity, int culturalOfferId, String pictureStr) {
		CulturalOffer culturalOffer = culturalOfferService.findOne(culturalOfferId);
		if (culturalOffer == null) 
			return false;
		entity.setCulturalOffer(culturalOffer);
		
		Picture picture = pictureService.findByPicture(pictureStr);
		if (picture == null)
			picture = pictureService.saveAndGetOne(new Picture(pictureStr));
		entity.setPicture(picture);
		
		newsletterRepo.save(entity);
		return true;
	}

}
