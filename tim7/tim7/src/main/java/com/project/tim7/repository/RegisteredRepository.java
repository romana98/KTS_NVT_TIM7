package com.project.tim7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.tim7.model.Registered;

@Repository
public interface RegisteredRepository extends JpaRepository<Registered, Integer> {
    Long countByEmailOrUsername(String email, String username);
    Registered findByUsername(String username);
    Registered findByEmail(String email);
    Registered findByUsernameOrEmail(String username, String email);
}
