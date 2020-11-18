package com.project.tim7.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "comments")
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "picture", unique = false, nullable = true)
	private String picture;
	
	@Column(name = "description", unique = false, nullable = true)
	private String description;
	
	@Column(name = "publishedDate", unique = false, nullable = true)
	private Date publishedDate;

}
