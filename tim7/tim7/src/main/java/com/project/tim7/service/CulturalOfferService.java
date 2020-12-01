package com.project.tim7.service;

import java.util.List;

import com.project.tim7.model.CulturalOffer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CulturalOfferService implements ServiceInterface<CulturalOffer> {

	@Override
	public List findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<CulturalOffer> findAll(Pageable pageable) {
		return null;
	}

	@Override
	public CulturalOffer findOne(int id) {
		return null;
	}

	@Override
	public boolean saveOne(CulturalOffer entity) {
		return false;
	}

	@Override
	public boolean saveAll(List<CulturalOffer> entities) {
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


}
