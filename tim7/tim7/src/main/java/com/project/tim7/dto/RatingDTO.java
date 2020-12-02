package com.project.tim7.dto;

import com.sun.istack.NotNull;

public class RatingDTO {
	
	private int id;
	
	private int registeredId;
	
	@NotNull
	private int rate;
	private int culturalOfferId;
	
	public RatingDTO() {
		super();
	}

	public RatingDTO(int rate) {
		super();
		this.rate = rate;
	}

	public RatingDTO(int id, int rate2) {
		this.id = id;
		this.rate = rate2;
	}

	public int getRegisteredId() {
		return registeredId;
	}

	public void setRegisteredId(int registeredId) {
		this.registeredId = registeredId;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public int getCulturalOfferId() {
		return culturalOfferId;
	}

	public void setCulturalOfferId(int culturalOfferId) {
		this.culturalOfferId = culturalOfferId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
