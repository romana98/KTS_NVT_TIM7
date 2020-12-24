package com.project.tim7.service;
import java.util.List;
import java.util.Set;
import com.project.tim7.dto.CulturalOfferDTO;
import com.project.tim7.dto.FilterDTO;
import com.project.tim7.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.tim7.repository.CulturalOfferRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CulturalOfferService implements ServiceInterface<CulturalOffer> {

	@Autowired
	private CulturalOfferRepository culturalOfferRepo;

	@Autowired
	LocationService locationService;

	@Autowired
	SubcategoryService subcategoryService;

	@Autowired
	PictureService pictureService;
	
	@Autowired
	CategoryService categoryService;

    @Autowired
	RegisteredService registeredService;

	@Override
	public List<CulturalOffer> findAll() {
		return culturalOfferRepo.findAll();
	}

	/**
	 * Returning all Cultural Offers by Page.
	 * @param pageable - Page Object with size and page number.
	 * @return - Returning number of Cultural Offers according to Page.
	 */
	@Override
	public Page<CulturalOffer> findAll(Pageable pageable) {
		return culturalOfferRepo.findAll(pageable);
	}

	/**
	 * Finding only Cultural Offer with given identification number.
	 * @param id - Identification number Cultural Offer.
	 * @return - Returning Cultural Offer with given id as parameter, otherwise returning null.
	 */
	@Override
	public CulturalOffer findOne(int id) {
		return culturalOfferRepo.findById(id).orElse(null);
	}

	/**
	 * Saving cultural offer or returning null if param is null.
	 * @param entity - New cultural offer to be added.
	 * @return - Returning created Cultural Offer or null if adding is not successful.
	 */
	@Transactional
	@Override
	public CulturalOffer saveOne(CulturalOffer entity) {
		if (entity != null){
			this.culturalOfferRepo.save(entity);
			return entity;
		}
		return null;
	}


	/**
	 * Creating new Cultural Offer using Data Transfer Object.
	 * @param entity - Data Transfer Object which contains all primary attributes of Cultural Offer.
	 * @return - Returning created Cultural Offer or null if Cultural Offer couldn't be created.
	 */
	@Transactional
	public CulturalOffer saveOne(CulturalOfferDTO entity) {

		//Checking if Cultural Offer with given name already exists.
		if(culturalOfferRepo.findByName(entity.getName()) != null){
			return null;
		}

		CulturalOffer culturalOffer = new CulturalOffer(entity.getId(), entity.getName(),entity.getStartDate(), entity.getDescription(), entity.getEndDate());

		//Extracting Location and Subcategory if they exist in Data Transfer Object.
		Location location = locationService.findOne(entity.getLocation());
		Subcategory subcategory = subcategoryService.findOne(entity.getSubcategory());

		if(location == null || subcategory == null){
			return null;
		}

		//Converting picture ArrayList into a Set.
		Set<Picture> pictureSet = pictureService.getPictures(entity.getPictures());

		//Setting remaining attributes.
		culturalOffer.setLocation(location);
		culturalOffer.setSubcategory(subcategory);
		culturalOffer.setPictures(pictureSet);

		//Sending entity in database for update.
		return saveOne(culturalOffer);

	}

	/**
	 * Updating old CulturalOffer with attributes of new CulturalOffer.
	 * @param entity - New CulturalOffer.
	 * @return - Returning updated entity or null if Cultural Offer doesn't exist.
	 */
	@Override
	public CulturalOffer update(CulturalOffer entity) {
		CulturalOffer culturalOffer = culturalOfferRepo.findById(entity.getId()).orElse(null);

		if (culturalOffer == null){
			return null;
		}

		culturalOffer.setName(entity.getName());
		culturalOffer.setDescription(entity.getDescription());
		culturalOffer.setEndDate(entity.getEndDate());
		culturalOffer.setStartDate(entity.getStartDate());
		culturalOffer.setLocation(entity.getLocation());
		culturalOffer.setSubcategory(entity.getSubcategory());
		culturalOfferRepo.save(culturalOffer);
		return culturalOffer;
	}

	/**
	 * Updating Cultural Offer using Data Transfer Object.
	 * @param entity - Data Transfer Object which contains all primary attributes of Cultural Offer.
	 * @return - Returning updated Cultural Offer or null if Cultural Offer couldn't be created.
	 */
	public CulturalOffer update(CulturalOfferDTO entity){
		CulturalOffer culturalOffer = new CulturalOffer(entity.getId(), entity.getName(),entity.getStartDate(), entity.getDescription(), entity.getEndDate());

		//Extracting non-primary attributes Location and Subcategory if they exist in Data Transfer Object.
		Location location = locationService.findOne(entity.getLocation());
		Subcategory subcategory = subcategoryService.findOne(entity.getSubcategory());

		if(location == null || subcategory == null || (culturalOfferRepo.findByName(culturalOffer.getName()) != null)){
			return null;
		}

		//Setting all attributes.
		culturalOffer.setId(entity.getId());
		culturalOffer.setName(entity.getName());
		culturalOffer.setDescription(entity.getDescription());
		culturalOffer.setEndDate(entity.getEndDate());
		culturalOffer.setStartDate(entity.getStartDate());
		culturalOffer.setLocation(location);
		culturalOffer.setSubcategory(subcategory);

		//Sending entity in database for update.
		return update(culturalOffer);
	}

	/**
	 * Deleting Cultural Offer with given id and returning indicator of success.
	 * @param id Identification number of Cultural Offer to be deleted.
	 * @return - Returning true if deleting was successful, otherwise false.
	 */
	@Override
	public boolean delete(int id) {
		CulturalOffer culturalOffer = findOne(id);
		if(culturalOffer != null){
			culturalOfferRepo.delete(culturalOffer);
			return true;
		}
		return false;
	}

	public long getCulturalOfferReferencingCount(int id) {
		return culturalOfferRepo.countBySubcategoryId(id);
	}

	public CulturalOffer subscribe(int idOffer, int idUser) {
		CulturalOffer culturalOffer = findOne(idOffer);
		Registered registered = registeredService.findOne(idUser);
		if (culturalOfferRepo.checkIfsubscriptionExists(idOffer, idUser) != 0)
			return null;
		culturalOffer.getSubscribed().add(registered);
		registered.getSubscribedCulturalOffers().add(culturalOffer);
		return saveOne(culturalOffer);	
	}

	public Page<CulturalOffer> filter(FilterDTO filterDTO, Pageable pageable) {

		if(filterDTO.getValue().equals("")) { return findAll(pageable);}

		switch (filterDTO.getParameter()){
			case "category":
				return culturalOfferRepo.filterByCategory(filterDTO.getValue(), pageable);
			case "subcategory" :
				return culturalOfferRepo.filterBySubcategory(filterDTO.getValue(), pageable);
			case "location" :
				return culturalOfferRepo.filterByLocation(filterDTO.getValue(), pageable);
			case "name" :
				return culturalOfferRepo.filterByName(filterDTO.getValue(), pageable);
			default:
				return culturalOfferRepo.filterByAll(filterDTO.getValue(), pageable);
		}
	}
}
