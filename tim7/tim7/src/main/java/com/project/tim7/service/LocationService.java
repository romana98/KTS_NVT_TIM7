package com.project.tim7.service;

import java.util.List;

import com.project.tim7.model.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LocationService implements ServiceInterface<Location> {

	@Override
	public List findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Location> findAll(Pageable pageable) {
		return null;
	}

	@Override
	public Location findOne(int id) {
		return null;
	}

	@Override
	public boolean saveOne(Location entity) {
		return false;
	}

	@Override
	public boolean saveAll(List<Location> entities) {
		return false;
	}

	@Override
	public boolean delete(int id) {
		return false;
	}

	@Override
	public Location update(Location entity) {
		return null;
	}


}
