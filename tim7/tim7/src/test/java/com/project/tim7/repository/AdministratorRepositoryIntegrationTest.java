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
    public void testCountByEmailOrUsername(){
        long count = administratorRepository.countByEmailOrUsername(NEW_EMAIL_EXIST, DB_USERNAME);

        assertEquals(DB_COUNT_TWO, count);
    }

    @Test
    public void testCountByEmailOrUsernameInvalidEmail(){
        long count = administratorRepository.countByEmailOrUsername(DB_EMAIL_NONEXIST, DB_USERNAME);

        assertEquals(DB_COUNT, count);
    }

    @Test
    public void testCountByEmailOrUsernameInvalidUsername(){
        long count = administratorRepository.countByEmailOrUsername(DB_EMAIL, DB_USERNAME_NONEXIST);

        assertEquals(DB_COUNT, count);
    }

    @Test
    public void testCountByEmailOrUsernameInvalid(){
        long count = administratorRepository.countByEmailOrUsername(DB_EMAIL_NONEXIST, DB_USERNAME_NONEXIST);

        assertEquals(DB_COUNT_INVALID, count);
    }

    @Test
    public void testFindByUsername(){
        Administrator found = administratorRepository.findByUsername(DB_USERNAME);

        assertEquals(DB_USERNAME, found.getUsername());
    }

    @Test
    public void testFindByUsernameInvalid(){
        Administrator found = administratorRepository.findByUsername(DB_USERNAME_NONEXIST);

        assertNull(found);
    }

    @Test
    public void testFindByEmail(){
        Administrator found = administratorRepository.findByEmail(DB_EMAIL);

        assertEquals(DB_EMAIL, found.getEmail());
    }

    @Test
    public void testFindByEmailInvalid(){
        Administrator found = administratorRepository.findByEmail(DB_EMAIL_NONEXIST);

        assertNull(found);
    }

    @Test
    public void testFindByUsernameOrEmail(){
        Administrator found = administratorRepository.findByUsernameOrEmail(DB_USERNAME, DB_EMAIL_NONEXIST);

        assertEquals(DB_USERNAME, found.getUsername());

        assertNotEquals(DB_EMAIL_NONEXIST, found.getEmail());
    }

    @Test
    public void testFindByUsernameOrEmailInvalid(){
        Administrator found = administratorRepository.findByUsernameOrEmail(DB_USERNAME_NONEXIST, DB_EMAIL_NONEXIST);

        assertNull(found);
    }
}
