package com.project.tim7.model;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Person {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int id;
	
	@Column(name = "username", unique = true, nullable = false)
	private String username;
	
	@Column(name = "email", unique = true, nullable = false)
	private String email;
	
	@Column(name = "password", unique = false, nullable = false)
	private String password;
	
	@Column(name = "verified", unique = false, nullable = true)
	private boolean verified;
	
}
