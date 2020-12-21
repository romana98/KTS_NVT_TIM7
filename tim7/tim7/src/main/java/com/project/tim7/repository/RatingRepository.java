package com.project.tim7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.tim7.model.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

    @Query(value = "SELECT avg(rate) from ratings where cultural_offer_id = offerid", nativeQuery = true)
    int findAverageRate(int offerid);

    @Query(value = "SELECT rate from ratings where registered_id = id", nativeQuery = true)
    int findRateRegistered(int id);
}
