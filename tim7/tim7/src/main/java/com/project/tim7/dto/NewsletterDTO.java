package com.project.tim7.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import com.sun.istack.NotNull;

public class NewsletterDTO {
	
	private int id;
	
	@NotBlank
	private String name;
	
	@PastOrPresent
	private Date publishedDate;
	
	@NotNull
	private int culturalOfferId;

	public NewsletterDTO(int id, @NotBlank String name, @PastOrPresent Date publishedDate, int culturalOfferId) {
		super();
		this.id = id;
		this.name = name;
		this.publishedDate = publishedDate;
		this.culturalOfferId = culturalOfferId;
	}
	
	public NewsletterDTO() {}

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
	
	
}

