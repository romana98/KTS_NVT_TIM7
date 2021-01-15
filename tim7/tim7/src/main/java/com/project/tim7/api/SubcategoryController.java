package com.project.tim7.api;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.project.tim7.dto.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.project.tim7.dto.SubcategoryDTO;
import com.project.tim7.helper.SubcategoryMapper;
import com.project.tim7.model.Subcategory;
import com.project.tim7.service.SubcategoryService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value="/subcategories", produces = MediaType.APPLICATION_JSON_VALUE)
public class SubcategoryController {
	
	@Autowired
	SubcategoryService subcategoryService;
	
	private SubcategoryMapper subcatMapper;
	
	public SubcategoryController() {
		super();
		this.subcatMapper = new SubcategoryMapper();
	}

	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<SubcategoryDTO>> getAllSubcategories(){
		List<Subcategory> subcategories = subcategoryService.findAll();
		
        return new ResponseEntity<>(toSubcategoryDTOList(subcategories), HttpStatus.OK);

	}
	
	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
	@RequestMapping(value= "/by-page",method = RequestMethod.GET)
    public ResponseEntity<Page<SubcategoryDTO>> getAllSubcategories(Pageable pageable) {
        Page<Subcategory> page = subcategoryService.findAll(pageable);
        List<SubcategoryDTO> dtos = toSubcategoryDTOList(page.toList());
        Page<SubcategoryDTO> pageSubcategoryDTOS = new PageImpl<>(dtos,page.getPageable(),page.getTotalElements());

        return new ResponseEntity<>(pageSubcategoryDTOS, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
	@RequestMapping(value = "/by-id/{id}", method = RequestMethod.GET)
	public ResponseEntity<SubcategoryDTO> getSubcategory(@PathVariable Integer id){
		Subcategory found = subcategoryService.findOne(id);
		if(found == null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}else{
			return new ResponseEntity<SubcategoryDTO>(subcatMapper.toDto(found), HttpStatus.OK);
		}
	}
	
	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Object> updateSubcategory(@Valid @RequestBody SubcategoryDTO subcategoryDTO){
		
		Subcategory newSubcategory = subcategoryService.update(subcatMapper.toEntity(subcategoryDTO));
		if(newSubcategory != null) {
			return new ResponseEntity<>(subcategoryDTO, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
	@RequestMapping(method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createSubcategory(@Valid @RequestBody SubcategoryDTO subcategory){
		
		Subcategory newSubcategory = subcategoryService.saveOne(subcatMapper.toEntity(subcategory));
		
		if(newSubcategory != null) {
			subcategory.setId(newSubcategory.getId());
			return new ResponseEntity<>(subcategory, HttpStatus.CREATED);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
    }
	
	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Object> deleteSubcategory(@PathVariable Integer id){

        if(subcategoryService.delete(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>("Subcategory deleting failed.", HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
	@RequestMapping(value="/{id}/by-page", method = RequestMethod.GET)
	public ResponseEntity<Page<SubcategoryDTO>> getAllSubcategories(@PathVariable int id, Pageable pageable){
		Page<Subcategory> page = subcategoryService.findSubcategoriesByCategory(id, pageable);
		List<SubcategoryDTO> dtos = toSubcategoryDTOList(page.toList());
		Page<SubcategoryDTO> pageSubcategoryDTOS = new PageImpl<>(dtos,page.getPageable(),page.getTotalElements());

		return new ResponseEntity<>(pageSubcategoryDTOS, HttpStatus.OK);
	}

	private List<SubcategoryDTO> toSubcategoryDTOList(List<Subcategory> subcategories) {
		ArrayList<SubcategoryDTO> dtos = new ArrayList<SubcategoryDTO>();
		for(Subcategory subcategory : subcategories) {
			SubcategoryDTO dto = subcatMapper.toDto(subcategory);
			dtos.add(dto);
		}
		return dtos;
	}

}
