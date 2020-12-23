package com.project.tim7.service;

import java.util.List;

import com.project.tim7.dto.RatingDTO;
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
	public List<Rating> findAll() {
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

		return null;
	}

	@Override
	public boolean delete(int id) {

		Rating rating = findOne(id);
		if(rating == null){
			return false;
		}else{
			ratingRepository.delete(rating);
			return true;
		}
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
		if(alreadyRated(culturalOffer, registered) != 0) {
			return null;
		}
		entity.setRegistered(registered);
		ratingRepository.save(entity);
		culturalOffer.getRatings().add(entity);
		culturalOfferService.saveOne(culturalOffer);
		return entity;
	}

	public long alreadyRated(CulturalOffer culturalOffer, Registered registered) {

		return ratingRepository.alreadyRated(culturalOffer.getId(), registered.getId());
		//return ratingRepository.countByCultural_Offer_IdAndRegistered_Id(culturalOffer.getId(), registered.getId());
	}

    public double getAverageRating(int culturalOfferId) {

		CulturalOffer culturalOffer = culturalOfferService.findOne(culturalOfferId);
		if(culturalOffer == null){
			return -1.0;
		}else{
			return ratingRepository.findAverageRate(culturalOfferId);
		}
    }

	public double getRating(int culturalOfferId, int userId) {

		if(culturalOfferService.findOne(culturalOfferId) == null){
			//offer doesn't exist
			return -1.0;
		}else if(registeredService.findOne(userId) == null){
			//if user is not registered, check if it's admin
			if(administratorService.findOne(userId) == null){
				//if user doesn't exist at all, return -2
				return -2.0;
			}else{
				//if it's admin, return -3
				return -3.0;
			}
		}else if(alreadyRated(new CulturalOffer(culturalOfferId), new Registered(userId)) == 0){
			//registered didn't leave a rate, return -4
			return -4.0;
		}else{
			//registered already left a rate, return it
			return ratingRepository.findRateRegistered(culturalOfferId, userId);
		}

	}
}
