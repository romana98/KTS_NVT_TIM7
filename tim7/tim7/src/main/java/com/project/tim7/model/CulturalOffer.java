package com.project.tim7.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "culturalOffers")
public class CulturalOffer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "name", unique = false, nullable = true)
	private String name;
	
	@Column(name = "description", unique = false, nullable = true, length=10485760)
	private String description;
	
	@Column(name = "startDate", unique = false, nullable = true)
	private Date startDate;
	
	@Column(name = "endDate", unique = false, nullable = true)
	private Date endDate;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "culturalOfferId")
	private Set<Picture> pictures;
	
	@OneToMany(mappedBy = "culturalOffer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Newsletter> newsletters;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	private Subcategory subcategory;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "subscribedCulturalOffers", joinColumns = { @JoinColumn(name = "culturalOfferId") }, inverseJoinColumns = {
	@JoinColumn(name = "RegisteredId") })
	private Set<Registered> subscribed;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "locationId", referencedColumnName = "id")
	private Location location;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "culturalOfferId")
	private Set<Comment> comments;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "culturalOfferId")
	private Set<Rating> ratings;
}
