package com.project.tim7.repository;

import com.project.tim7.model.Administrator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static com.project.tim7.constants.AdministratorConstants.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class AdministratorRepositoryIntegrationTest {

    @Autowired
    private AdministratorRepository administratorRepository;

    @Test
    public void countByEmailOrUsername(){
        long count = administratorRepository.countByEmailOrUsername(NEW_EMAIL_EXIST, DB_USERNAME);

        assertEquals(DB_COUNT_TWO, count);
    }

    @Test
    public void countByEmailOrUsernameInvalidEmail(){
        long count = administratorRepository.countByEmailOrUsername(DB_EMAIL_NONEXIST, DB_USERNAME);

        assertEquals(DB_COUNT, count);
    }

    @Test
    public void countByEmailOrUsernameInvalidUsername(){
        long count = administratorRepository.countByEmailOrUsername(DB_EMAIL, DB_USERNAME_NONEXIST);

        assertEquals(DB_COUNT, count);
    }

    @Test
    public void countByEmailOrUsernameInvalid(){
        long count = administratorRepository.countByEmailOrUsername(DB_EMAIL_NONEXIST, DB_USERNAME_NONEXIST);

        assertEquals(DB_COUNT_INVALID, count);
    }

    @Test
    public void findByUsername(){
        Administrator found = administratorRepository.findByUsername(DB_USERNAME);

        assertEquals(DB_USERNAME, found.getUsername());
    }

    @Test
    public void findByUsernameInvalid(){
        Administrator found = administratorRepository.findByUsername(DB_USERNAME_NONEXIST);

        assertNull(found);
    }

    @Test
    public void findByEmail(){
        Administrator found = administratorRepository.findByEmail(DB_EMAIL);

        assertEquals(DB_EMAIL, found.getEmail());
    }

    @Test
    public void findByEmailInvalid(){
        Administrator found = administratorRepository.findByEmail(DB_EMAIL_NONEXIST);

        assertNull(found);
    }

    @Test
    public void findByUsernameOrEmail(){
        Administrator found = administratorRepository.findByUsernameOrEmail(DB_USERNAME, DB_EMAIL_NONEXIST);

        assertEquals(DB_USERNAME, found.getUsername());

        assertNotEquals(DB_EMAIL_NONEXIST, found.getEmail());
    }

    @Test
    public void findByUsernameOrEmailInvalid(){
        Administrator found = administratorRepository.findByUsernameOrEmail(DB_USERNAME_NONEXIST, DB_EMAIL_NONEXIST);

        assertNull(found);
    }
}
