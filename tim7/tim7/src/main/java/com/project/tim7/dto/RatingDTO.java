package com.project.tim7.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

public class RatingDTO {
	
	private int id;
	
	@NotNull
	@Min(0)
	private int registeredId;
	
	@NotNull
	@Min(0)
	@Max(5)
	private double rate;
	@NotNull
	@Min(0)
	private int culturalOfferId;
	
	public RatingDTO() {
		super();
	}

	public RatingDTO(double rate) {
		super();
		this.rate = rate;
	}

	public RatingDTO(int id, @Min(0) @Max(5) double rate) {
		super();
		this.id = id;
		this.rate = rate;
	}

	public int getRegisteredId() {
		return registeredId;
	}

	public void setRegisteredId(int registeredId) {
		this.registeredId = registeredId;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
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
