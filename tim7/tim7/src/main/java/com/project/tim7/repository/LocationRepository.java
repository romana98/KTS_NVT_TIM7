package com.project.tim7.repository;

import com.project.tim7.model.Newsletter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.tim7.model.Location;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

    public List<Location> findByLatitudeAndLongitude(double latitude,double longitude);

}
