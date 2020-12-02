package com.project.tim7.service;

import java.util.List;

import com.project.tim7.model.Location;
import com.project.tim7.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LocationService implements ServiceInterface<Location> {

	@Autowired
	LocationRepository locationRepository;

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
		return locationRepository.findById(id).orElse(null);
	}

	@Override
	public boolean saveOne(Location entity) {
		int count = locationRepository.findByLatitudeAndLongitude(entity.getLatitude(),entity.getLongitude()).size();
		if(count == 0){
			locationRepository.save(entity);
			return true;
		}
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
