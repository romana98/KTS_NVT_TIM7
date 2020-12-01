package com.project.tim7.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.tim7.model.Category;
import com.project.tim7.repository.CategoryRepository;

@Service
public class CategoryService implements ServiceInterface<Category> {

	@Autowired
	CategoryRepository categoryRepo;

	@Override
	public List<Category> findAll() {
		return categoryRepo.findAll();
	}

	@Override
	public Category findOne(int id) {
		return categoryRepo.findById(id).orElse(null);
	}

	@Override
	public Page<Category> findAll(Pageable pageable) {
		return categoryRepo.findAll(pageable);
	}

	@Override
	public boolean saveOne(Category entity) {
		try {
			categoryRepo.save(entity);
			return true;
		}catch(Exception e) {
			return false;
		}
	}

	@Override
	public boolean saveAll(List<Category> entities) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	public Category update(Category entity) {
		
		Category oldCategory = findOne(entity.getId());
		entity.setSubcategories(oldCategory.getSubcategories());
		boolean verified = saveOne(entity);
		if(verified == false) {
			return null;
		}else {
			return entity;
		}
		
	}
	
	

}
