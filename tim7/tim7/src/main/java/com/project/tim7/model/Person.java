package com.project.tim7.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;


@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//@GenericGenerator(name="PERSON_SEQ", strategy = "sequence",
//		parameters = { @Parameter(name="sequence", value="PERSON_SEQ") } )

public abstract class Person {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERSON_SEQ")
	@SequenceGenerator(sequenceName = "person_seq", name = "PERSON_SEQ", allocationSize = 1)
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
