package com.project.tim7.service;

import java.util.List;

import com.project.tim7.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CommentService implements ServiceInterface<Comment> {

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
		return null;
	}

	@Override
	public boolean saveOne(Comment entity) {
		return false;
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

}
