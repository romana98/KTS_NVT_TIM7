package com.project.tim7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.tim7.model.Administrator;

import java.util.List;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {
      Long countByEmailOrUsername(String email, String username);
}
