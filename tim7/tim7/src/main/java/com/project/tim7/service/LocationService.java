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

	public Location findOneByLongitudeAndLatitude(double longitude, double latitude) {
		return locationRepository.findByLatitudeAndLongitude(latitude,longitude);
	}

	@Override
	public Location saveOne(Location entity) {
		Location location = locationRepository.findByLatitudeAndLongitude(entity.getLatitude(),entity.getLongitude());
		if(location == null){
			locationRepository.save(entity);
			return entity;
		}
		return null;
	}

	@Override
	public boolean delete(int id) {

		Location location = findOne(id);
		if(location == null){
			return false;
		}else{
			locationRepository.delete(location);
			return true;
		}
	}

	@Override
	public Location update(Location entity) {
		return null;
	}


}
