package com.project.tim7.helper;

import com.project.tim7.dto.SubcategoryDTO;
import com.project.tim7.model.Subcategory;

public class SubcategoryMapper implements MapperInterface<Subcategory, SubcategoryDTO> {

	@Override
	public Subcategory toEntity(SubcategoryDTO dto) {
		return new Subcategory(dto.getId(), dto.getName(), dto.getCategoryId());
	}

	@Override
	public SubcategoryDTO toDto(Subcategory entity) {
		return new SubcategoryDTO(entity.getId(), entity.getName());
	}

}
