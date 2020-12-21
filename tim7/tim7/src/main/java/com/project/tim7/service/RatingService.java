package com.project.tim7.service;

import java.util.List;

import com.project.tim7.model.Administrator;
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

	@Autowired
	AdministratorService administratorService;
	
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

		return ratingRepository.findById(id).orElse(null);
	}

	@Override
	public Rating saveOne(Rating entity) {

		return ratingRepository.save(entity);
	}

	@Override
	public Rating saveAll(List<Rating> entities) {
		return null;
	}

	@Override
	public boolean delete(int id) {
            return false;
        }

	@Override
	public Rating update(Rating entity) {
		return null;
	}

	public Rating createRating(Rating entity, int culturalOfferId) {
		Registered registered = registeredService.findOne(entity.getRegistered().getId());
		if(registered == null) {
			return null;
		}
		CulturalOffer culturalOffer = culturalOfferService.findOne(culturalOfferId);
		if(culturalOffer == null) {
			return null;
		}
		if(alreadyRated(culturalOffer, registered)) {
			return null;
		}
		entity.setRegistered(registered);
		Rating newRating = saveOne(entity);
		culturalOffer.getRatings().add(newRating);
		culturalOfferService.saveOne(culturalOffer);
		return newRating;
	}

	private boolean alreadyRated(CulturalOffer culturalOffer, Registered registered) {
		for(Rating rating : culturalOffer.getRatings()) {
			if(rating.getRegistered().getId() == registered.getId()) {
				return true;
			}
		}
		return false;
	}

    public int getRating(int culturalOfferId, int id) {

		Administrator administrator = administratorService.findOne(id);
		if(administrator != null){
			return ratingRepository.findAverageRate(culturalOfferId);
		}else{
			return ratingRepository.findRateRegistered(id);
		}

    }
}
