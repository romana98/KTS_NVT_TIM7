package com.project.tim7.service;

import java.util.List;

import com.project.tim7.dto.RatingDTO;
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
	public boolean saveOne(Rating entity) {
		return false;
	}

	@Override
	public boolean saveAll(List<Rating> entities) {
		return false;
	}

	@Override
	public boolean delete(int id) {
		return false;
	}

	@Override
	public Rating update(Rating entity) {
		return null;
	}

	public boolean createRating(Rating entity, int culturalOfferId, int registeredId) {
		Registered registered = registeredService.findOne(registeredId);
		entity.setRegistered(registered);
		CulturalOffer culturalOffer = culturalOfferService.findOne(culturalOfferId);
		if(alreadyRated(culturalOffer, registered)) {
			return false;
		}
		culturalOffer.getRatings().add(entity);
		ratingRepository.save(entity);
		culturalOfferService.saveOne(culturalOffer);
		
		return true;
	}

	private boolean alreadyRated(CulturalOffer culturalOffer, Registered registered) {
		for(Rating rating : culturalOffer.getRatings()) {
			if(rating.getRegistered().getId() == registered.getId()) {
				return true;
			}
		}
		return false;
	}
	
	public int getRatingIdByDTO(RatingDTO ratingDTO) {
		CulturalOffer culturalOffer = culturalOfferService.findOne(ratingDTO.getCulturalOfferId());
		for(Rating rating : culturalOffer.getRatings()) {
			if(rating.getRegistered().getId() == ratingDTO.getRegisteredId()) {
				return rating.getId();
			}
		}
		return 0;
	}
}
