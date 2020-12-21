package com.project.tim7.service;

import java.util.List;

import com.project.tim7.model.Administrator;
import com.project.tim7.model.Registered;
import com.project.tim7.repository.AdministratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AdministratorService implements ServiceInterface<Administrator> {

	@Autowired
	AdministratorRepository adminRepo;

	@Autowired
	RegisteredService regService;


	@Override
	public List<Administrator> findAll() {
		return adminRepo.findAll();
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
	public Administrator saveOne(Administrator entity) {
		if(countByEmailOrUsername(entity.getEmail(), entity.getUsername()) != 0 ||
				regService.countByEmailOrUsername(entity.getEmail(), entity.getUsername()) != 0)
			return null;
		
		return adminRepo.save(entity);
	}

	@Override
	public boolean delete(int id) {
		Administrator existingAdmin = findOne(id);
		if(existingAdmin  != null){
			adminRepo.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public Administrator update(Administrator entity) {
		Administrator admin = findByUsername(entity.getUsername());
		if(admin == null){
			return null;
		}
		if(entity.getEmail().compareTo(admin.getEmail()) != 0)
			if (adminRepo.findByEmail(entity.getEmail()) != null ||
					regService.findByEmail(entity.getEmail()) != null) {
				return null;
			}

		admin.setEmail(entity.getEmail());
		admin.setPassword(entity.getPassword());
		return adminRepo.save(admin);
	}

	public long countByEmailOrUsername(String email, String username){
		return adminRepo.countByEmailOrUsername(email, username);
	}

	public Administrator findByEmail(String email){
		return adminRepo.findByEmail(email);
	}

	public Administrator findByUsername(String username){
		return adminRepo.findByUsername(username);
	}

	public Administrator findByUsernameOrEmail(String username, String email){
		return adminRepo.findByUsernameOrEmail(username, email);
	}

}
