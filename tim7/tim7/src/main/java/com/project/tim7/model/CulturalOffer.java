package com.project.tim7.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
	
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "locationId", referencedColumnName = "id")
	private Location location;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "culturalOfferId")
	private Set<Comment> comments;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "culturalOfferId")
	private Set<Rating> ratings;

	public CulturalOffer(int id, String description, Date endDate, String name, Date startDate) {
		this.id = id;
		this.description =description;
		this.endDate = endDate;
		this.name = name;
		this.startDate = startDate;
	}

	public CulturalOffer() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public Set<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(Set<Rating> ratings) {
		this.ratings = ratings;
	}

	public Subcategory getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(Subcategory subcategory) {
		this.subcategory = subcategory;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Set<Picture> getPictures() {
		return pictures;
	}

	public void setPictures(Set<Picture> pictures) {
		this.pictures = pictures;
	}

	public Set<Newsletter> getNewsletters() {
		return newsletters;
	}

	public void setNewsletters(Set<Newsletter> newsletters) {
		this.newsletters = newsletters;
	}

	public Set<Registered> getSubscribed() {
		return subscribed;
	}

	public void setSubscribed(Set<Registered> subscribed) {
		this.subscribed = subscribed;
	}
}
