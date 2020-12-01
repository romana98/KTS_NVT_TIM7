package com.project.tim7.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.tim7.dto.SubcategoryDTO;
import com.project.tim7.helper.SubcategoryMapper;
import com.project.tim7.model.Subcategory;
import com.project.tim7.repository.SubcategoryRepository;

@Service
public class SubcategoryService implements ServiceInterface<Subcategory> {

	@Autowired
	SubcategoryRepository subcategoryRepo;
	
	@Autowired
	CategoryService categoryService;

	@Override
	public List<Subcategory> findAll() {
		return subcategoryRepo.findAll();
	}

	@Override
	public Subcategory findOne(int id) {
		return subcategoryRepo.findById(id).orElse(null);
	}

	@Override
	public Page<Subcategory> findAll(Pageable pageable) {
		return subcategoryRepo.findAll(pageable);
	}

	@Override
	public boolean saveOne(Subcategory entity) {
		if(subcategoryRepo.findByName(entity.getName()) == null) {
			subcategoryRepo.save(entity);
			return true;
		}else {
			return false;
		}
	}

	@Override
	public boolean saveAll(List<Subcategory> entities) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Subcategory update(Subcategory entity) {
		
		Subcategory subcategory = findOne(entity.getId());
		if(subcategory == null) {
			return null;
		}
		subcategory.setName(entity.getName());
		boolean check = saveOne(subcategory);
		if(check == true) {
			return subcategory;
		}else {
			return null;
		}
		
	}
	
	public boolean addSubcategory(Subcategory subcategory, int categoryId) {
		subcategory.setCategory(categoryService.findOne(categoryId));
		return saveOne(subcategory);
	}


}
