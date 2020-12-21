package com.project.tim7.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
		return null;
	}

	@Override
	public Page<Picture> findAll(Pageable pageable) {
		return null;
	}

	@Override
	public Picture saveAll(List<Picture> entities) {
		return null;
	}
	
	@Override
	public Picture findOne(int id) {
		return pictureRepo.findById(id).orElse(null);
	}

	@Override
	public Picture saveOne(Picture entity) {
		if (findByPicture(entity.getPicture()) != null)
            return null;
        return pictureRepo.save(entity);
	}

    @Override
    public boolean delete(int id) {
        Picture picture = findOne(id);
        try {
            pictureRepo.delete(picture);
        } catch (Exception e) {
        	return false;
        }
        return true;
    }

	public Picture findByPicture(String pictureStr) {
		return pictureRepo.findByPicture(pictureStr);
	}

	@Override
	public Picture update(Picture entity) {
		return pictureRepo.save(entity);
	}
	
	public long countPictureInNewsletters(int id) {
		return pictureRepo.countPictureInNewsletters(id);
	}
	
	public Picture saveAndReturn(Picture entity) {
		pictureRepo.save(entity);
		return entity;
	}

	public Set<Picture> getPictures(ArrayList<String> pictures) {

		Set<Picture> commentPictures = new HashSet<Picture>();		for(String picture : pictures) {
			Picture p = findByPicture(picture);
			if(p == null) {
				Picture newPicture = new Picture(picture);
				Picture pictureComment = saveAndReturn(newPicture);
				commentPictures.add(pictureComment);
			}else {
				commentPictures.add(p);
			}
		}
		return commentPictures;
	}

	public boolean checkDuplicates(ArrayList<String> pictures) {
		
		for(int i = 0; i < pictures.size()-1; i++) {
			for(int j = i+1; j < pictures.size(); j++) {
				if(pictures.get(i).equals(pictures.get(j))) {
					return true;
				}
			}
		}
		
		return false;
	}

}
