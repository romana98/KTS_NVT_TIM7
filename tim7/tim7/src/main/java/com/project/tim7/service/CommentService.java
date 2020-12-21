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

		return commentRepo.findAll(pageable);
	}

	@Override
	public Comment findOne(int id) {
		return commentRepo.findById(id).orElse(null);
	}

	@Override
	public Comment saveOne(Comment entity) {

		commentRepo.save(entity);
		return entity;
	}

	@Override
	public boolean delete(int id) {
		return false;
	}

	@Override
	public Comment update(Comment entity) {
		return null;
	}

	public Comment createComment(Comment entity, int registeredId, ArrayList<String> pictures, int culturalOfferId) {
		
		if(pictureService.checkDuplicates(pictures)) {
			return null;
		}
		
		Registered registered = registeredService.findOne(registeredId);
		if(registered == null) {
			return null;
		}
		CulturalOffer culturalOffer = culturalOfferService.findOne(culturalOfferId);
		if(culturalOffer == null) {
			return null;
		}
		entity.setRegistered(registered);
		entity.setPictures(pictureService.getPictures(pictures));
		Comment newComment = saveOne(entity);
		culturalOffer.getComments().add(newComment);
		culturalOfferService.saveOne(culturalOffer);
		return newComment;
	}

    public Page<Comment> findCommentsByCulturalOffer(int culturalOfferId, Pageable pageable) {

		return commentRepo.findCommentsOfCulturalOffer(culturalOfferId, pageable);

    }
}
