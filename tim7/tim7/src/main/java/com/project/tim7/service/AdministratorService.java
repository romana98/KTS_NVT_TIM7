package com.project.tim7.service;

import java.util.List;

import com.project.tim7.model.Administrator;
import com.project.tim7.repository.AdministratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministratorService implements ServiceInterface<Administrator> {

	@Autowired
	AdministratorRepository adminRepo;


	@Override
	public List<Administrator> findAll() {
		return null;
	}

	@Override
	public Administrator findOne(int id) {
		return adminRepo.findById(id).orElse(null);
	}

	@Override
	public boolean saveOne(Administrator entity) {
		System.out.println(findByEmailorUsername(entity.getEmail(), entity.getUsername()));
		if(!findByEmailorUsername(entity.getEmail(), entity.getUsername()).isEmpty())
			return false;

		adminRepo.save(entity);
		return true;
	}

	@Override
	public boolean saveAll(List<Administrator> entities) {
		return false;
	}

	@Override
	public boolean delete(int id) {
		return false;
	}

	public List<Administrator> findByEmailorUsername(String email, String username){
		return adminRepo.findByEmailOrUsername(email, username);
	}
}
