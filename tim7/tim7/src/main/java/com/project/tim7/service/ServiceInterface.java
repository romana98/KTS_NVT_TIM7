package com.project.tim7.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ServiceInterface<T> {
	
	List<T> findAll();

	Page<T> findAll(Pageable pageable);
	
	T findOne(int id);
	
	boolean saveOne(T entity);
	
	boolean saveAll(List<T> entities);
	
	boolean delete(int id);

	T update(T entity);

}
