package com.project.tim7.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CommentService implements ServiceInterface {

	@Override
	public List findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object findOne(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean saveOne(Object entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveAll(List entities) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Page findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
