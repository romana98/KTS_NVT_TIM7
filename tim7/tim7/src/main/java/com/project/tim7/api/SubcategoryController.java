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

import com.project.tim7.dto.SubcategoryDTO;
import com.project.tim7.helper.SubcategoryMapper;
import com.project.tim7.model.Subcategory;
import com.project.tim7.service.SubcategoryService;

@RestController
@RequestMapping(value="/subcategories", produces = MediaType.APPLICATION_JSON_VALUE)
public class SubcategoryController {
	
	@Autowired
	SubcategoryService subcategoryService;
	
	private SubcategoryMapper subcatmapper;
	
	public SubcategoryController() {
		super();
		this.subcatmapper = new SubcategoryMapper();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<SubcategoryDTO>> getAllSubcategories(){
		List<Subcategory> subcategories = subcategoryService.findAll();
		
        return new ResponseEntity<>(toSubcategoryDTOList(subcategories), HttpStatus.OK);

	}
	
	@RequestMapping(value= "/by-page",method = RequestMethod.GET)
    public ResponseEntity<Page<SubcategoryDTO>> getAllSubcategories(Pageable pageable) {
        Page<Subcategory> page = subcategoryService.findAll(pageable);
        List<SubcategoryDTO> dtos = toSubcategoryDTOList(page.toList());
        Page<SubcategoryDTO> pageSubcategoryDTOS = new PageImpl<>(dtos,page.getPageable(),page.getTotalElements());

        return new ResponseEntity<>(pageSubcategoryDTOS, HttpStatus.OK);
    }
	
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Object> updateSubcategory(@Valid @RequestBody SubcategoryDTO subcategoryDTO){
		
		Subcategory newSubcategory = subcategoryService.update(subcatmapper.toEntity(subcategoryDTO));
		if(newSubcategory != null) {
			return new ResponseEntity<>(subcategoryDTO, HttpStatus.OK);
		}else {
			return new ResponseEntity<Object>("Editing failed.", HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@RequestMapping(method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createSubcategory(@Valid @RequestBody SubcategoryDTO subcategory){
		
		if(subcategoryService.addSubcategory(subcategory) == true) {
			return new ResponseEntity<>(HttpStatus.CREATED);
		}else {
			return new ResponseEntity<>("Subcategory already exists.", HttpStatus.BAD_REQUEST);
		}
    }


	private List<SubcategoryDTO> toSubcategoryDTOList(List<Subcategory> subcategories) {
		ArrayList<SubcategoryDTO> dtos = new ArrayList<SubcategoryDTO>();
		for(Subcategory subcategory : subcategories) {
			SubcategoryDTO dto = subcatmapper.toDto(subcategory);
			dtos.add(dto);
		}
		return dtos;
	}

}
