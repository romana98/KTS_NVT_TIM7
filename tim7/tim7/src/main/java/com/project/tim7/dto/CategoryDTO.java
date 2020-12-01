package com.project.tim7.dto;

import javax.validation.constraints.NotBlank;

public class CategoryDTO {
	
	
	private int id;
	
	@NotBlank
	private String name;
		
	public CategoryDTO() {
		super();
	}

	public CategoryDTO(int id, String name) {
		super();
		this.id = id;
		this.name = name;
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
}
