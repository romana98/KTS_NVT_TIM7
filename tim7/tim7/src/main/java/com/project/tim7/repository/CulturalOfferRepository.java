package com.project.tim7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.tim7.model.CulturalOffer;

@Repository
public interface CulturalOfferRepository extends JpaRepository<CulturalOffer, Integer> {
	
    Long countBySubcategoryId(int subcategoryId);


}
