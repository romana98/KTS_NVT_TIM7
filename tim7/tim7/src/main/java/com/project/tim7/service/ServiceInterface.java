package com.project.tim7.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ServiceInterface<T> {
	
	List<T> findAll();
	
	T findOne(int id);
	
	Page<T> findAll(Pageable pageable);
	
	boolean saveOne(T entity);
	
	boolean saveAll(List<T> entities);
	
	boolean delete(int id);

}
