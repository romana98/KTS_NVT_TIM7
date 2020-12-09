package com.project.tim7.service;

import java.lang.reflect.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.project.tim7.dto.CulturalOfferDTO;
import com.project.tim7.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.tim7.repository.CulturalOfferRepository;

@Service
public class CulturalOfferService implements ServiceInterface<CulturalOffer> {
	
	@Autowired
	CulturalOfferRepository culturalOfferRepo;

	@Autowired
	LocationService locationService;

	@Autowired
	SubcategoryService subcategoryService;

	@Autowired
	PictureService pictureService;

	@Override
	public List<CulturalOffer> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<CulturalOffer> findAll(Pageable pageable) {
		return culturalOfferRepo.findAll(pageable);
	}
  
	@Override
	public CulturalOffer findOne(int id) {
		return culturalOfferRepo.findById(id).orElse(null);
	}

	@Override
	public boolean saveOne(CulturalOffer entity) {
		if(entity.getLocation() == null || entity.getSubcategory() == null){
			return false;
		}
		culturalOfferRepo.save(entity);
		return true;
	}

	public boolean saveOne(CulturalOffer entity, int locationId, int subcategoryId, ArrayList<String> pictures) {

		if(culturalOfferRepo.findByName(entity.getName()) != null){
			return false;
		}

		Location location = locationService.findOne(locationId);
		Subcategory subcategory = subcategoryService.findOne(subcategoryId);

		if(location == null || subcategory == null){
			return false;
		}

		Set<Picture> pictureSet = pictureService.getPictures(pictures);

		entity.setPictures(pictureSet);
		entity.setLocation(location);
		entity.setSubcategory(subcategory);

		culturalOfferRepo.save(entity);
		return true;
	}

	@Override
	public boolean saveAll(List<CulturalOffer> entities) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		CulturalOffer culturalOffer = findOne(id);
		if(culturalOffer != null){
			culturalOfferRepo.delete(culturalOffer);
			return true;
		}
		return false;
	}

	public boolean update(CulturalOfferDTO entity){
		CulturalOffer culturalOffer = culturalOfferRepo.findById(entity.getId()).orElse(null);
		if(culturalOffer == null){
			return false;
		}
		Location location = locationService.findOne(entity.getLocation());
		Subcategory subcategory = subcategoryService.findOne(entity.getSubcategory());

		if(location == null || subcategory == null || (culturalOfferRepo.findByName(entity.getName()) != null)){
			return false;
		}

		culturalOffer.setName(entity.getName());
		culturalOffer.setDescription(entity.getDescription());
		culturalOffer.setEndDate(entity.getEndDate());
		culturalOffer.setStartDate(entity.getStartDate());
		culturalOffer.setLocation(location);
		culturalOffer.setSubcategory(subcategory);
		culturalOfferRepo.save(culturalOffer);
		return true;
	}

	@Override
	public CulturalOffer update(CulturalOffer entity) {
		return null;
	}

	public long getCulturalOfferReferencingCount(int id) {
		return culturalOfferRepo.countBySubcategoryId(id);
	}


}
