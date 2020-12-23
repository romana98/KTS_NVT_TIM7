package com.project.tim7.service;

import java.util.List;

import com.project.tim7.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PersonService implements ServiceInterface<Person> {

	@Override
	public List findAll() { return null; }

	@Override
	public Page<Person> findAll(Pageable pageable) {
		return null;
	}

	@Override
	public Person findOne(int id) {
		return null;
	}

	@Override
	public Person saveOne(Person entity) {
		return null;
	}

	@Override
	public boolean delete(int id) {
		return false;
	}

	@Override
	public Person update(Person entity) {
		return null;
	}


}
