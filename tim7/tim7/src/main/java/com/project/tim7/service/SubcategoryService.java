package com.project.tim7.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.tim7.dto.SubcategoryDTO;
import com.project.tim7.helper.SubcategoryMapper;
import com.project.tim7.model.Category;
import com.project.tim7.model.Subcategory;
import com.project.tim7.repository.SubcategoryRepository;

@Service
public class SubcategoryService implements ServiceInterface<Subcategory> {

	@Autowired
	SubcategoryRepository subcategoryRepo;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	CulturalOfferService culturalOfferService;

	@Override
	public List<Subcategory> findAll() {
		return subcategoryRepo.findAll();
	}

	@Override
	public Subcategory findOne(int id) {
		return subcategoryRepo.findById(id).orElse(null);
	}

	@Override
	public Subcategory saveOne(Subcategory entity) {
		return null;
	}

	@Override
	public Subcategory saveAll(List<Subcategory> entities) {
		return null;
	}

	@Override
	public boolean delete(int id) {
		return false;
	}

	@Override
	public Page<Subcategory> findAll(Pageable pageable) {
		return subcategoryRepo.findAll(pageable);
	}

/*
	@Override
	public boolean saveOne(Subcategory entity) {
		
		subcategoryRepo.save(entity);
		return true;
		
	}

	@Override
	public boolean saveAll(List<Subcategory> entities) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		Subcategory subcategory = subcategoryRepo.findById(id).orElse(null);
		if(culturalOfferService.getCulturalOfferReferencingCount(id) != 0) {
			return false;
		}
		if(subcategory == null) {
			return false;
		}
		subcategoryRepo.deleteById(id);
		return true;
		
	}
*/
	@Override
	public Subcategory update(Subcategory entity) {
		
		saveOne(entity);
		return entity;
		
	}
	
	public Subcategory update(Subcategory entity, int categoryId) {
		
		Subcategory subcategory = findOne(entity.getId());
		if(subcategory == null) {
			return null;
		}
		Category category = categoryService.findOne(categoryId);
		if(category == null) {
			return null;
		}
		if(entity.getName().equals(subcategory.getName()) == false) {
			Subcategory subcategory2 = subcategoryRepo.findByName(entity.getName());
			if(subcategory2 != null) {
				return null;
			}
		}
		
		subcategory.setName(entity.getName());
		subcategory.setCategory(category);
		return update(subcategory);
		
		
	}
	
	public Subcategory addSubcategory(Subcategory subcategory, int categoryId) {
		
		Category category = categoryService.findOne(categoryId);
		if(category == null) {
			return null;
		}
		Subcategory subcatName = subcategoryRepo.findByName(subcategory.getName());
		if(subcatName != null) {
			return null;
		}
		subcategory.setCategory(category);
		saveOne(subcategory);
		return subcategory;
	}

	public long getSubcategoriesReferencingCount(int id) {
		return subcategoryRepo.countByCategoryId(id);
	}


}
