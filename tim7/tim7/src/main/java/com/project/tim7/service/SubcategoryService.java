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

		Subcategory subcatName = subcategoryRepo.findByName(entity.getName());
		if(subcatName != null) {
			return null;
		}
		Category category = categoryService.findOne(entity.getCategory().getId());
		if(category == null){
			return null;
		}
		entity.setCategory(category);
		subcategoryRepo.save(entity);
		return entity;
	}

	@Override
	public Page<Subcategory> findAll(Pageable pageable) {
		return subcategoryRepo.findAll(pageable);
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

	@Override
	public Subcategory update(Subcategory entity) {

		Subcategory subcategory = findOne(entity.getId());
		if(subcategory == null) {
			return null;
		}
		Category category = categoryService.findOne(entity.getCategory().getId());
		if(category == null) {
			return null;
		}

		if(entity.getName().equals(subcategory.getName())){
			return subcategory;
		}else{
			if(subcategoryRepo.findByName(entity.getName()) == null){
				subcategory.setName(entity.getName());
				subcategoryRepo.save(subcategory);
				return subcategory;
			}else{
				return null;
			}
		}
	}

	public long getSubcategoriesReferencingCount(int id) {

		return subcategoryRepo.countByCategoryId(id);
	}

    public Page<Subcategory> findSubcategoriesByCategory(int id, Pageable pageable) {

		return subcategoryRepo.findByCategoryId(id, pageable);
    }
}
