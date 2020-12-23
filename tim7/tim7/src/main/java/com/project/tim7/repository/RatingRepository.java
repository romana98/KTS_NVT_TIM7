package com.project.tim7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.tim7.model.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

    @Query(value = "SELECT avg(rate) from ratings where cultural_offer_id = ?1", nativeQuery = true)
    double findAverageRate(int offerId);

    @Query(value = "SELECT rate from ratings where cultural_offer_id = ?1 AND registered_id = ?2", nativeQuery = true)
    double findRateRegistered(int offerId, int userId);

    @Query(value = "SELECT COUNT(*) from ratings where cultural_offer_id = ?1 AND registered_id = ?2", nativeQuery = true)
    long alreadyRated(int offerId, int userId);

}
