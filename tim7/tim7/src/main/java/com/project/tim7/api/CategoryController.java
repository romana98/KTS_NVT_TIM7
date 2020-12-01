package com.project.tim7.api;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	private CategoryMapper catmapper;
	
	public CategoryController() {
		super();
		this.catmapper = new CategoryMapper();
	}

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
	
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Object> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO){
		
		Category newCategory = categoryService.update(catmapper.toEntity(categoryDTO));
		if(newCategory != null) {
			return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
		}else {
			return new ResponseEntity<Object>("Editing failed.", HttpStatus.BAD_REQUEST);
		}
		
	}


	private List<CategoryDTO> toCategoryDTOList(List<Category> categories) {
		ArrayList<CategoryDTO> dtos = new ArrayList<CategoryDTO>();
		for(Category category : categories) {
			CategoryDTO dto = catmapper.toDto(category);
			dtos.add(dto);
		}
		return dtos;
	}

}
