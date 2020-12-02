package com.project.tim7.model;

import javax.persistence.*;

@Entity
@Table(name = "locations")
public class Location {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "longitude", unique = false, nullable = true)
	private double longitude;
	
	@Column(name = "latitude", unique = false, nullable = true)
	private double latitude;
	
	@Column(name = "name", unique = false, nullable = true)
	private String name;

	public Location(int id, double longitude, double latitude, String name) {
		this.id = id;
		this.longitude = longitude;
		this.latitude = latitude;
		this.name = name;
	}

	public Location() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
