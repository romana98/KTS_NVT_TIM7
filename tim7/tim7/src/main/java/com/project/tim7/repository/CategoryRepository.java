package com.project.tim7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;


import com.project.tim7.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>  {
	
	Category findByName(String name);
	
	@Query(value = "select * from categories where categories.id in\n" +
			"(select subcategories.category_id from subcategories where subcategories.category_id = categories.id AND subcategories.id in\n" +
			"(select cultural_offers.subcategory_id from cultural_offers where cultural_offers.subcategory_id = subcategories.id AND cultural_offers.id in\n" +
			"(select subscribed_cultural_offers.cultural_offer_id from subscribed_cultural_offers where subscribed_cultural_offers.cultural_offer_id = cultural_offers.id and subscribed_cultural_offers.registered_id = ?1)))", nativeQuery = true)
	List<Category> findSubscribedCategories(int id);

}
