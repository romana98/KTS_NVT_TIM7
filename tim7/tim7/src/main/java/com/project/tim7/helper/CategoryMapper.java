package com.project.tim7.helper;

import com.project.tim7.dto.CategoryDTO;
import com.project.tim7.model.Category;

public class CategoryMapper implements MapperInterface<Category, CategoryDTO> {

	@Override
	public Category toEntity(CategoryDTO dto) {
		return new Category(dto.getId(), dto.getName());
	}

	@Override
	public CategoryDTO toDto(Category entity) {
		return new CategoryDTO(entity.getId(), entity.getName());
	}

	

}
