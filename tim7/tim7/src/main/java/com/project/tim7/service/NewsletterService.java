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
		newsletterRepo.save(entity);
		return true;
	}

	@Override
	public boolean saveAll(List<Newsletter> entities) {
		return false;
	}
	
	@Override
	public Newsletter update(Newsletter entity) {
		return null;
	}

	public boolean updateNewsletter(Newsletter entity, String pictureStr) {
		Newsletter newsletter = findOne(entity.getId());
		if(newsletter == null) {
			return false;
		}
		newsletter.setName(entity.getName());
		newsletter.setDescription(entity.getDescription());
		Picture updatedPicture = pictureService.findByPicture(pictureStr);
		if (updatedPicture == null) 
			updatedPicture = pictureService.update(new Picture(pictureStr));
		newsletter.setPicture(updatedPicture);
		saveOne(newsletter);
		return true;
	}

	@Override
	public boolean delete(int id) {
		Newsletter newsletter = findOne(id);
		if(newsletter == null) {
			return false;
		}
		Picture picture = newsletter.getPicture();
		
		newsletterRepo.delete(newsletter);
		if (pictureService.countPictureInNewsletters(picture.getId()) == 0) {
			pictureService.delete(picture.getId());
		}
		return true;
	}
	
	public boolean saveNewsletter(Newsletter entity, int culturalOfferId, String pictureStr) {
		CulturalOffer culturalOffer = culturalOfferService.findOne(culturalOfferId);
		if (culturalOffer == null) 
			return false;
		entity.setCulturalOffer(culturalOffer);
		
		Picture picture = pictureService.findByPicture(pictureStr);
		if (picture == null)
			picture = pictureService.update(new Picture(pictureStr));
		entity.setPicture(picture);
		
		entity.setId(0);
		saveOne(entity);
		return true;
	}
	
	public List<Newsletter> findNewsletterForUser(int idRegisteredUser) {
		return newsletterRepo.findNewsletterForUser(idRegisteredUser);
	}
	
	public Page<Newsletter> findNewsletterForUser(int idRegisteredUser, Pageable pageable) {
		return newsletterRepo.findNewsletterForUser(idRegisteredUser, pageable);
	}
	
	public List<Newsletter> findNewsletterForCulturalOffer(int idCulturalOffer) {
		return newsletterRepo.findNewsletterForCulturalOffer(idCulturalOffer);
	}
	
	public Page<Newsletter> findNewsletterForCulturalOffer(int idCulturalOffer, Pageable pageable) {
		return newsletterRepo.findNewsletterForCulturalOffer(idCulturalOffer, pageable);
	}


}
