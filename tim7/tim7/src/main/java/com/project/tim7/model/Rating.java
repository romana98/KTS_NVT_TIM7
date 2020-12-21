package com.project.tim7.model;

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
@Table(name = "ratings")
public class Rating {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	private Registered registered;
	
	@Column(name = "rate", unique = false, nullable = false)
	private int rate;

	public Rating() {
		super();
	}

	public Rating(int rate) {
		super();
		this.rate = rate;
	}

	public Rating(int id, int rate) {
		super();
		this.id = id;
		this.rate = rate;
	}

	public Rating(int id, int rate, int registeredId) {
		super();
		this.id = id;
		this.rate = rate;
		this.registered = new Registered(registeredId);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Registered getRegistered() {
		return registered;
	}

	public void setRegistered(Registered registered) {
		this.registered = registered;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

}
