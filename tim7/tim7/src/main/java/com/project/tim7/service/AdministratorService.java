package com.project.tim7.service;

import java.util.List;

import com.project.tim7.model.Administrator;
import com.project.tim7.repository.AdministratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	public Page<Administrator> findAll(Pageable pageable) {
		return adminRepo.findAll(pageable);
	}

	@Override
	public Administrator findOne(int id) {
		return adminRepo.findById(id).orElse(null);
	}

	@Override
	public boolean saveOne(Administrator entity) {
		if(countByEmailorUsername(entity.getEmail(), entity.getUsername()) != 0)
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
		Administrator existingAdmin  = findOne(id);
		if(existingAdmin  != null){
			adminRepo.deleteById(id);
			return true;
		}
		return false;
	}

	public long countByEmailorUsername(String email, String username){
		return adminRepo.countByEmailOrUsername(email, username);
	}

	@Override
	public Page<Administrator> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}
}
