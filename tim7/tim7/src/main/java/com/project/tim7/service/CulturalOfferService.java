package com.project.tim7.service;

import java.util.List;

import com.project.tim7.model.CulturalOffer;
import com.project.tim7.model.Location;
import com.project.tim7.model.Subcategory;
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

	@Override
	public List<CulturalOffer> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<CulturalOffer> findAll(Pageable pageable) {
		return null;
	}
  
	@Override
	public CulturalOffer findOne(int id) {
		//TODO izmeni kad budes radio ako bude trebalo, morao sam nesto da imam zbog testiranja :D 
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

	public boolean saveOne(CulturalOffer entity, int locationId, int subcategoryId) {

		if(culturalOfferRepo.findByName(entity.getName()) != null){
			return false;
		}

		Location location = locationService.findOne(locationId);
		Subcategory subcategory = subcategoryService.findOne(subcategoryId);

		if(location == null || subcategory == null){
			return false;
		}

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
		return false;
	}

	@Override
	public CulturalOffer update(CulturalOffer entity) {
		return null;
	}

	public long getCulturalOfferReferencingCount(int id) {
		return culturalOfferRepo.countBySubcategoryId(id);
	}


}
