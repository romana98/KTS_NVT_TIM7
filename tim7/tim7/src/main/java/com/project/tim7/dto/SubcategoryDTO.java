package com.project.tim7.dto;

import com.project.tim7.model.Category;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class SubcategoryDTO {
	
private int id;
	
	@NotBlank
	private String name;
	
	@NotNull
	@Min(0)
	private int categoryId;

	@NotBlank
	private String categoryName;

	public SubcategoryDTO() {
		super();
	}

	public SubcategoryDTO(int id, @NotBlank String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public SubcategoryDTO(String name, int categoryId){
		super();
		this.name = name;
		this.categoryId = categoryId;
	}

	public SubcategoryDTO(int id, @NotBlank String name, @NotBlank int categoryId) {
		super();
		this.id = id;
		this.name = name;
		this.categoryId = categoryId;
	}

	public SubcategoryDTO(int id, @NotBlank String name, Category category) {
		super();
		this.id = id;
		this.name = name;
		this.categoryId = category.getId();
		this.categoryName = category.getName();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}
