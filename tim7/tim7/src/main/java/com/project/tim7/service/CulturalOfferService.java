package com.project.tim7.service;

import java.util.List;

import com.project.tim7.model.CulturalOffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.tim7.model.CulturalOffer;
import com.project.tim7.repository.CulturalOfferRepository;

@Service
public class CulturalOfferService implements ServiceInterface<CulturalOffer> {
	
  @Autowired
	CulturalOfferRepository culturalOfferRepo;

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
		return culturalOfferRepo.findById(id).orElse(null);
	}

	@Override
	public boolean saveOne(CulturalOffer entity) {
		// TODO Auto-generated method stub
		return false;
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
