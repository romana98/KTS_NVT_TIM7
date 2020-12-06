package com.project.tim7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.tim7.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    Person findByUsername(String username);
    Person findByEmail(String email);
}
