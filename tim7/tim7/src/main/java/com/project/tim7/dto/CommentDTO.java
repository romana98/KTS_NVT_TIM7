package com.project.tim7.dto;

import java.util.ArrayList;
import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CommentDTO {
	
	private int id;
	
	@NotBlank
	private String description;
	@NotNull
	private Date publishedDate;
	@NotNull
	@Min(0)
	private int registeredId;
	private ArrayList<String> picturesId;
	@NotNull
	@Min(0)
	private int culturalOfferId;
	
	public CommentDTO() {
		super();
		this.picturesId = new ArrayList<String>();
	}

	public CommentDTO(int id, @NotBlank String description, Date publishedDate, int registeredId,
			ArrayList<String> pictures) {
		super();
		this.id = id;
		this.description = description;
		this.publishedDate = publishedDate;
		this.registeredId = registeredId;
		this.picturesId = pictures;
	}

	public CommentDTO(int id, @NotBlank String description, @NotNull Date publishedDate,
			@NotNull @Min(0) int registeredId) {
		super();
		this.id = id;
		this.description = description;
		this.publishedDate = publishedDate;
		this.registeredId = registeredId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}

	public int getRegisteredId() {
		return registeredId;
	}

	public void setRegisteredId(int registeredId) {
		this.registeredId = registeredId;
	}

	public ArrayList<String> getPicturesId() {
		return picturesId;
	}

	public void setPictures(ArrayList<String> pictures) {
		this.picturesId = pictures;
	}

	public int getCulturalOfferId() {
		return culturalOfferId;
	}

	public void setCulturalOfferId(int culturalOfferId) {
		this.culturalOfferId = culturalOfferId;
	}
	
}
