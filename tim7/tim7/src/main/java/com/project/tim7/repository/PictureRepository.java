package com.project.tim7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.tim7.model.Picture;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Integer>  {

	Picture findByPicture(String picture);

	@Query(value = "SELECT COUNT(id) FROM newsletters WHERE newsletters.picture_id = ?1 AND newsletters.picture_id IN "
			+ "(SELECT id FROM pictures WHERE pictures.cultural_offer_id IS NULL AND pictures.comment_id IS NULL);", nativeQuery=true)
	long countPictureInNewsletters(int id);

}
