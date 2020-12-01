package com.project.tim7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.tim7.model.Picture;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Integer>  {

	public Picture findByPicture(String picture);
	
	@Query("SELECT COUNT (n.id) FROM Newsletter n WHERE n.picture.id = ?1")
	public long countPictureInNewsletters(int id);
}
