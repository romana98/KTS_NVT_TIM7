package com.project.tim7.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.project.tim7.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>  {
	
	Category findByName(String name);

}
