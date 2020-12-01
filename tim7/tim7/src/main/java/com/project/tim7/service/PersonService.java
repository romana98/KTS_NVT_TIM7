package com.project.tim7.service;

import java.util.List;

import com.project.tim7.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PersonService implements ServiceInterface<Person> {

	@Override
	public List findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Person> findAll(Pageable pageable) {
		return null;
	}

	@Override
	public Person findOne(int id) {
		return null;
	}

	@Override
	public boolean saveOne(Person entity) {
		return false;
	}

	@Override
	public boolean saveAll(List<Person> entities) {
		return false;
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
