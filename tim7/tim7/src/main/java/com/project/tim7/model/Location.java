package com.project.tim7.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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

}
