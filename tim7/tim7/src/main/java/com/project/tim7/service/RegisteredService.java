package com.project.tim7.service;

import java.util.List;


import com.project.tim7.model.Registered;
import com.project.tim7.repository.RegisteredRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.jws.Oneway;

@Service
public class RegisteredService implements ServiceInterface<Registered> {

	@Autowired
	RegisteredRepository regRepo;

	@Autowired
	AdministratorService adminService;

	@Override
	public List findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Registered> findAll(Pageable pageable) {
		return null;
	}

	@Override
	public Registered findOne(int id) {
		return regRepo.findById(id).orElse(null);
	}

	@Override
	public boolean saveOne(Registered entity) {
		return false;
	}

	@Override
	public boolean saveAll(List<Registered> entities) {
		return false;
	}

	@Override
	public boolean delete(int id) {
		return false;
	}

	@Override
	public Registered update(Registered entity) {
		Registered reg = findOne(entity.getId());
		if(reg == null){
			return null;
		}
		if(entity.getUsername().compareTo(reg.getUsername()) != 0)
			if (regRepo.findByUsername(entity.getUsername()) != null) {
				return null;
			}

		reg.setUsername(entity.getUsername());
		reg.setPassword(entity.getPassword());
		return regRepo.save(reg);
	}

	public Registered save(Registered entity){
		if(countByEmailOrUsername(entity.getEmail(), entity.getUsername()) != 0 ||
		adminService.countByEmailOrUsername(entity.getEmail(), entity.getUsername()) != 0)
			return null;

		return regRepo.save(entity);

	}

	public long countByEmailOrUsername(String email, String username){
		return regRepo.countByEmailOrUsername(email, username);
	}

	public Registered findByUsernameOrEmail(String username, String email){
		return regRepo.findByUsernameOrEmail(username, email);
	}


}
