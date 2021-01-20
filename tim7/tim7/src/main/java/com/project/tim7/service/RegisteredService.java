package com.project.tim7.service;

import java.util.ArrayList;
import java.util.List;

import com.project.tim7.model.Registered;
import com.project.tim7.repository.RegisteredRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class RegisteredService implements ServiceInterface<Registered> {

	@Autowired
	RegisteredRepository regRepo;

	@Autowired
	AdministratorService adminService;
	
	@Autowired
	EmailService emailService;

	@Override
	public List<Registered> findAll() {
		return regRepo.findAll();
	}

	@Override
	public Page<Registered> findAll(Pageable pageable) {
		return regRepo.findAll(pageable);
	}

	@Override
	public Registered findOne(int id) {

		return regRepo.findById(id).orElse(null);
	}

	@Override
	public Registered saveOne(Registered entity) {
		if(countByEmailOrUsername(entity.getEmail(), entity.getUsername()) != 0 ||
				adminService.countByEmailOrUsername(entity.getEmail(), entity.getUsername()) != 0)
			return null;

		return regRepo.save(entity);
	}

	@Override
	public boolean delete(int id) {
		Registered existingReg = findOne(id);
		if(existingReg  != null){
			try {
				regRepo.deleteById(id);
			}catch (Exception e){
				return false;
			}

			return true;
		}
		return false;
	}

	@Override
	public Registered update(Registered entity) {
		Registered reg = findByUsername(entity.getUsername());
		if(reg == null){
			return null;
		}
		if(entity.getEmail().compareTo(reg.getEmail()) != 0)
			if (regRepo.findByEmail(entity.getEmail()) != null ||
					adminService.findByEmail(entity.getEmail()) != null) {
				return null;
			}

		reg.setEmail(entity.getEmail());
		reg.setPassword(entity.getPassword());
		return regRepo.save(reg);
	}

	public long countByEmailOrUsername(String email, String username){
		return regRepo.countByEmailOrUsername(email, username);
	}

	public Registered findByUsernameOrEmail(String username, String email){
		return regRepo.findByUsernameOrEmail(username, email);
	}

	public Registered findByEmail(String email){
		return regRepo.findByEmail(email);
	}

	public Registered findByUsername(String username){
		return regRepo.findByUsername(username);
	}

	public Registered activateAccount(int id) {
		Registered regUser = findOne(id);
		if(regUser == null)
			return null;
		regUser.setVerified(true);
		return regRepo.save(regUser);
	}

	public Registered registerUser(Registered existReg) {
		Registered newReg = saveOne(existReg);
		if(newReg == null)
			return null;

		emailService.sendVerificationMail(newReg.getEmail(), newReg.getId());

        return newReg;
	}

	public List<String> findRegisteredForSubscribedCulturalOffers(int id){
		List<String> emails = new ArrayList<>();
		List<Registered> regs = regRepo.findRegisteredForSubscribedCulturalOffers(id);
		for (Registered reg: regs) {
			emails.add(reg.getEmail());
		}
		return emails;
	}
}
