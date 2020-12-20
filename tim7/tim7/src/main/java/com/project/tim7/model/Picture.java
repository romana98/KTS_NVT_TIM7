package com.project.tim7.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pictures")
public class Picture {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "picture", unique = false, nullable = false)
	private String picture;

	public Picture(String picture) {
		super();
		this.picture = picture;
	}
	
	public Picture() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	@Override
	public String toString() {
		return "Picture{" +
				"picture='" + picture + '\'' +
				'}';
	}

	public Picture(int id, String picture) {
		super();
		this.id = id;
		this.picture = picture;
	}
	
	
}
