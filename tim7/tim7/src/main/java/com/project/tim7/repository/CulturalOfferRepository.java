package com.project.tim7.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.tim7.model.CulturalOffer;

@Repository
public interface CulturalOfferRepository extends JpaRepository<CulturalOffer, Integer> {
	
    Long countBySubcategoryId(int subcategoryId);

    CulturalOffer findByName(String name);

    @Query(value = "SELECT * from cultural_offers where subcategory_id in (SELECT id from subcategories where category_id in (SELECT id from categories where upper(name) like upper(CONCAT('%', :value, '%'))))", nativeQuery = true)
	Page<CulturalOffer> filterByCategory(String value, Pageable pageable);

    @Query(value = "SELECT * from cultural_offers where subcategory_id in (SELECT id from subcategories where upper(name) LIKE upper(CONCAT('%', :value, '%')))", nativeQuery = true)
	Page<CulturalOffer> filterBySubcategory(String value, Pageable pageable);

	@Query(value = "SELECT * from cultural_offers where upper(name) LIKE upper(CONCAT('%', :value, '%')) or location_id in (SELECT id from locations where name LIKE upper(CONCAT('%', :value, '%'))) or subcategory_id in (SELECT id from subcategories where upper(name) LIKE upper(CONCAT('%', :value, '%'))) or subcategory_id in (SELECT id from subcategories where category_id in (SELECT id from categories where upper(name) like upper(CONCAT('%', :value, '%'))))", nativeQuery = true)
    Page<CulturalOffer> filterByAll(String value, Pageable pageable);
	
	@Query("SELECT COUNT(co.id) FROM CulturalOffer co JOIN co.subscribed s WHERE co.id = ?1 AND s.id = ?2")
	Long checkIfsubscriptionExists(int idOffer, int idUser);

}
