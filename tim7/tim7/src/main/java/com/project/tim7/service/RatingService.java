package com.project.tim7.service;

import java.util.List;

import com.project.tim7.model.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RatingService implements ServiceInterface<Rating> {

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


}
