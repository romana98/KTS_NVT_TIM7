package com.project.tim7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.tim7.model.Registered;

@Repository
public interface RegisteredRepository extends JpaRepository<Registered, Integer> {
    Registered findByUsername(String username);
}
