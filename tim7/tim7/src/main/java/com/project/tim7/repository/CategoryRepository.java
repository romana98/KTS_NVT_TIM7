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
	
	@Query("SELECT c FROM Category c JOIN c.subcategories sub JOIN sub.culturalOffers co JOIN co.subscribed s WHERE s.id = ?1")
	public List<Category> findSubscribedCategories(int id);

}
