package com.project.tim7.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.project.tim7.model.Comment;
import com.project.tim7.model.CulturalOffer;
import com.project.tim7.model.Person;
import com.project.tim7.model.Picture;
import com.project.tim7.model.Registered;
import com.project.tim7.repository.CommentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;


@Service
public class CommentService implements ServiceInterface<Comment> {

	@Autowired
	CommentRepository commentRepo;
	
	@Autowired
	RegisteredService registeredService;
	
	@Autowired
	PictureService pictureService;
	
	@Autowired
	CulturalOfferService culturalOfferService;
	
	@Override
	public List findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Comment> findAll(Pageable pageable) {
		return null;
	}

	@Override
	public Comment findOne(int id) {
		return commentRepo.findById(id).orElse(null);
	}

	@Override
	public boolean saveOne(Comment entity) {
		commentRepo.save(entity);
		return true;
	}

	@Override
	public boolean saveAll(List<Comment> entities) {
		return false;
	}

	@Override
	public boolean delete(int id) {
		return false;
	}

	@Override
	public Comment update(Comment entity) {
		return null;
	}

	public int createComment(Comment entity, int registeredId, ArrayList<String> pictures, int culturalOfferId) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Person person = (Person) authentication.getPrincipal();
		Registered registered = registeredService.findOne(person.getId());
		if(registered == null || registered.getId() != registeredId) {
			return -1;
		}
		CulturalOffer culturalOffer = culturalOfferService.findOne(culturalOfferId);
		if(culturalOffer == null) {
			return -1;
		}
		entity.setRegistered(registered);
		entity.setPictures(getPictures(pictures));
		Comment newComment = saveAndReturn(entity);
		culturalOffer.getComments().add(newComment);
		culturalOfferService.saveOne(culturalOffer);
		
		return newComment.getId();
	}

	private Comment saveAndReturn(Comment entity) {
		saveOne(entity);
		return entity;
	}

	private Set<Picture> getPictures(ArrayList<String> pictures) {
		
		Set<Picture> commentPictures = new HashSet<Picture>();
		for(String picture : pictures) {
			Picture p = pictureService.findByPicture(picture);
			if(p == null) {
				Picture newPicture = new Picture(picture);
				Picture pictureComment = pictureService.saveAndReturn(newPicture);
				commentPictures.add(pictureComment);
			}else {
				commentPictures.add(p);
			}
		}
		return commentPictures;
	}

}
