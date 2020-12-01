package com.project.tim7.service;

import java.util.List;

public interface ServiceInterface<T> {
	
	List<T> findAll();
	
	T findOne(int id);
	
	boolean saveOne(T entity);
	
	boolean saveAll(List<T> entities);
	
	boolean delete(int id);

}
