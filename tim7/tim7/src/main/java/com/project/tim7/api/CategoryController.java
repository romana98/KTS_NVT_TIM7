package com.project.tim7.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.tim7.dto.CategoryDTO;
import com.project.tim7.helper.CategoryMapper;
import com.project.tim7.model.Category;
import com.project.tim7.service.CategoryService;

@RestController
@RequestMapping(value="/categories", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryController  {
	
	@Autowired
	CategoryService categoryService;
	
	private CategoryMapper mapper;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CategoryDTO>> getAllCategories(){
		List<Category> categories = categoryService.findAll();
		
        return new ResponseEntity<>(toCategoryDTOList(categories), HttpStatus.OK);

	}
	
	@RequestMapping(value= "/by-page",method = RequestMethod.GET)
    public ResponseEntity<Page<CategoryDTO>> getAllCategories(Pageable pageable) {
        Page<Category> page = categoryService.findAll(pageable);
        List<CategoryDTO> dtos = toCategoryDTOList(page.toList());
        Page<CategoryDTO> pageCategoryDTOS = new PageImpl<>(dtos,page.getPageable(),page.getTotalElements());

        return new ResponseEntity<>(pageCategoryDTOS, HttpStatus.OK);
    }


	private List<CategoryDTO> toCategoryDTOList(List<Category> categories) {
		ArrayList<CategoryDTO> dtos = new ArrayList<CategoryDTO>();
		for(Category c : categories) {
			CategoryDTO dto = mapper.toDto(c);
			dtos.add(dto);
		}
		return dtos;
	}

}
