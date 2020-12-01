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
	
	@Autowired
	SubcategoryService subcategoryService;

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
		if(categoryRepo.findByName(entity.getName()) == null) {
			categoryRepo.save(entity);
			return true;
		}else {
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
		Category category = categoryRepo.findById(id).orElse(null);
		if(subcategoryService.getSubcategoriesReferencingCount(id) != 0) {
			return false;
		}
		if(category == null) {
			return false;
		}else {
			categoryRepo.deleteById(id);
			return true;
		}
	}

	@Override
	public Category update(Category entity) {
		
		Category category = findOne(entity.getId());
		if(category == null) {
			return null;
		}
		category.setName(entity.getName());
		boolean check = saveOne(category);
		if(check == true) {
			return category;
		}else {
			return null;
		}
		
	}

}
