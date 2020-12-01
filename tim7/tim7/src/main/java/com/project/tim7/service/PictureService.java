package com.project.tim7.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
		return pictureRepo.findById(id).orElse(null);
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
		Picture picture = findOne(id);
		pictureRepo.delete(picture);
		return true;
	}
	
	public Picture findByPicture(String pictureStr) {
		return pictureRepo.findByPicture(pictureStr);
	}
	
	public Picture saveAndGetOne(Picture entity) {
		return pictureRepo.save(entity);
	}


	@Override
	public Page<Picture> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Picture update(Picture entity) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public long countPictureInNewsletters(int id) {
		return pictureRepo.countPictureInNewsletters(id);
	}
}
