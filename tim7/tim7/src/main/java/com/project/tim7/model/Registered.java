package com.project.tim7.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "registered")
@OnDelete(action = OnDeleteAction.CASCADE)
public class Registered extends Person {
	
	@ManyToMany(mappedBy = "subscribed",cascade = CascadeType.MERGE)
	private Set<CulturalOffer> subscribedCulturalOffers;
	
	@OneToMany(mappedBy = "registered", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Rating> ratings;

	public Registered(){
		
	}

    public Registered(int id, String email, String username, String password) {
    	super(id, email, username, password);
    }
}
