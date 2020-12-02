package com.project.tim7.dto;

import java.util.ArrayList;
import java.util.Date;

import javax.validation.constraints.NotBlank;

public class CommentDTO {
	
	private int id;
	
	@NotBlank
	private String description;
	private Date publishedDate;
	private int registeredId;
	private ArrayList<String> pictures;
	private int culturalOfferId;
	
	public CommentDTO() {
		super();
		this.pictures = new ArrayList<String>();
	}

	public CommentDTO(int id, @NotBlank String description, Date publishedDate, int registeredId,
			ArrayList<String> pictures) {
		super();
		this.id = id;
		this.description = description;
		this.publishedDate = publishedDate;
		this.registeredId = registeredId;
		this.pictures = pictures;
	}

	public CommentDTO(int id2, String description2, Date publishedDate2, int id3) {
		this.id = id2;
		this.description = description2;
		this.publishedDate = publishedDate2;
		this.registeredId = id3;
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

	public ArrayList<String> getPictures() {
		return pictures;
	}

	public void setPictures(ArrayList<String> pictures) {
		this.pictures = pictures;
	}

	public int getCulturalOfferId() {
		return culturalOfferId;
	}

	public void setCulturalOfferId(int culturalOfferId) {
		this.culturalOfferId = culturalOfferId;
	}
	
}
