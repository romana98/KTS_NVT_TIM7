package com.project.tim7.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "newsletters")
public class Newsletter {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "name", unique = false, nullable = true)
	private String name;
	
	@Column(name = "description", unique = false, nullable = true, length=10485760)
	private String description;
	
	@Column(name = "publishedDate", unique = false, nullable = true)
	private Date publishedDate;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	private CulturalOffer culturalOffer;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	private Picture picture;
	

}
