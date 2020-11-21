package com.project.tim7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.tim7.model.Picture;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Integer>  {

}
