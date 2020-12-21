package com.project.tim7.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.tim7.model.Subcategory;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, Integer> {
	
	Subcategory findByName(String name);
	
    Long countByCategoryId(int categoryId);

    Page<Subcategory> findByCategoryId(int id, Pageable pageable);
}
