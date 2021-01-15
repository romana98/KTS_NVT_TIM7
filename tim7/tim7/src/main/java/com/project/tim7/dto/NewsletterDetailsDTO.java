package com.project.tim7.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import com.sun.istack.NotNull;

public class NewsletterDetailsDTO {
	
	private int id;
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String description;
	
	@PastOrPresent
	private Date publishedDate;
	
	@NotNull
	private int culturalOfferId;
	
	private String culturalOffer;
	
	private String picture;

	public NewsletterDetailsDTO(int id, @NotBlank String name, String description, @NotBlank @PastOrPresent Date publishedDate,
			@NotBlank int culturalOfferId, String picture, String culturalOffer) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.publishedDate = publishedDate;
		this.culturalOfferId = culturalOfferId;
		this.picture = picture;
		this.culturalOffer = culturalOffer;
	}
	
	public NewsletterDetailsDTO(int id, @NotBlank String name, String description, @NotBlank @PastOrPresent Date publishedDate,
			@NotBlank int culturalOfferId, String picture) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.publishedDate = publishedDate;
		this.culturalOfferId = culturalOfferId;
		this.picture = picture;
	}
	
	public NewsletterDetailsDTO() {}

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

	public int getCulturalOfferId() {
		return culturalOfferId;
	}

	public void setCulturalOfferId(int culturalOfferId) {
		this.culturalOfferId = culturalOfferId;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getCulturaloffer() {
		return culturalOffer;
	}

	public void setCulturaloffer(String culturaloffer) {
		this.culturalOffer = culturaloffer;
	}
	
	
	
	

}
