package com.project.tim7.service;

import java.util.ArrayList;
import java.util.List;


import com.project.tim7.model.Registered;
import com.project.tim7.repository.RegisteredRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
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
	public Registered saveOne(Registered entity) {
		return null;
	}

	/*
        @Override
        public boolean saveOne(Registered entity) {
            return false;
        }

        @Override
        public boolean saveAll(List<Registered> entities) {
            return false;
        }
*/
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
		if(entity.getEmail().compareTo(reg.getEmail()) != 0)
			if (regRepo.findByEmail(entity.getEmail()) != null ||
					adminService.findByEmail(entity.getEmail()) != null) {
				return null;
			}

		reg.setEmail(entity.getEmail());
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

	public Registered findByEmail(String email){
		return regRepo.findByEmail(email);
	}

	public Registered activateAccount(int id, String email) {
		Registered regUser = findOne(id);
		regUser.setVerified(true);
		Registered activatedReg = regRepo.save(regUser);
		if (activatedReg != null) 
			return activatedReg;
		else
			return null;
	}
	
	public Registered addUser(Registered existReg) {
		Registered newReg = save(existReg);
        try {
			emailService.sendVerificationMail(newReg.getEmail(), newReg.getId());
		} catch (MailException e) {
			e.printStackTrace();
		}
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
