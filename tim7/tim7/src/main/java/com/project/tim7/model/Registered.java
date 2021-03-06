package com.project.tim7.model;

import java.util.List;
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

	public Registered(String email, String username, String password) {
		super(email, username, password);
	}

    public Registered(int registeredId) {
		super(registeredId);
    }

    @Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return super.isVerified();
	}

	public Set<CulturalOffer> getSubscribedCulturalOffers() {
		return subscribedCulturalOffers;
	}

	public void setSubscribedCulturalOffers(Set<CulturalOffer> subscribedCulturalOffers) {
		this.subscribedCulturalOffers = subscribedCulturalOffers;
	}

	public Set<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(Set<Rating> ratings) {
		this.ratings = ratings;
	}
	
	
}
