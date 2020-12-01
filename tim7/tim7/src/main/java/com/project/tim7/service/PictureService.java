package com.project.tim7.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.tim7.model.Picture;
import com.project.tim7.repository.PictureRepository;

@Service
public class PictureService implements ServiceInterface<Picture> {
	
	@Autowired 
	PictureRepository pictureRepo;

	@Override
	public List<Picture> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Picture findOne(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean saveOne(Picture entity) {
		if (findByPicture(entity.getPicture()) != null)
			return false;
		pictureRepo.save(entity);
		return true;
	}

	@Override
	public boolean saveAll(List<Picture> entities) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public Picture findByPicture(String pictureStr) {
		return pictureRepo.findByPicture(pictureStr);
	}
	
	public Picture saveAndGetOne(Picture entity) {
		return pictureRepo.save(entity);
	}


}
