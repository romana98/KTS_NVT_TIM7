package com.project.tim7.service;

import java.util.List;

import com.project.tim7.model.CulturalOffer;
import com.project.tim7.model.Rating;
import com.project.tim7.model.Registered;
import com.project.tim7.repository.RatingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RatingService implements ServiceInterface<Rating> {

	@Autowired
	RatingRepository ratingRepository;
	
	@Autowired
	RegisteredService registeredService;
	
	@Autowired
	CulturalOfferService culturalOfferService;
	
	@Override
	public List findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Rating> findAll(Pageable pageable) {
		return null;
	}

	@Override
	public Rating findOne(int id) {
		return null;
	}

	@Override
	public Rating saveOne(Rating entity) {
		return null;
	}

	@Override
	public Rating saveAll(List<Rating> entities) {
		return null;
	}
	/*
        @Override
        public boolean saveOne(Rating entity) {
            ratingRepository.save(entity);
            return true;
        }

        @Override
        public boolean saveAll(List<Rating> entities) {
            return false;
        }
*/
        @Override
        public boolean delete(int id) {
            return false;
        }

	@Override
	public Rating update(Rating entity) {
		return null;
	}

	public int createRating(Rating entity, int culturalOfferId, int registeredId) {
		Registered registered = registeredService.findOne(registeredId);
		if(registered == null) {
			return -1;
		}
		CulturalOffer culturalOffer = culturalOfferService.findOne(culturalOfferId);
		if(culturalOffer == null) {
			return -1;
		}
		if(alreadyRated(culturalOffer, registered)) {
			return -1;
		}
		entity.setRegistered(registered);
		Rating newRating = saveAndReturn(entity);
		culturalOffer.getRatings().add(newRating);
		culturalOfferService.saveOne(culturalOffer);
		return newRating.getId();
	}

	private Rating saveAndReturn(Rating entity) {
		saveOne(entity);
		return entity;
	}

	private boolean alreadyRated(CulturalOffer culturalOffer, Registered registered) {
		for(Rating rating : culturalOffer.getRatings()) {
			if(rating.getRegistered().getId() == registered.getId()) {
				return true;
			}
		}
		return false;
	}
}
