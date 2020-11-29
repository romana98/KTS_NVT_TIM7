package com.project.tim7.model;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Person {
	
	@Id
	@SequenceGenerator(name="jpaPkSeq", sequenceName="JPA_PK_SEQ", allocationSize=1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jpaPkSeq")
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
