package com.project.tim7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.tim7.model.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

}
