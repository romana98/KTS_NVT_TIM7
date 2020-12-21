package com.project.tim7.repository;

import com.project.tim7.model.Registered;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.project.tim7.constants.RegisteredConstants.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class RegisteredRepositoryIntegrationTest {

    @Autowired
    RegisteredRepository registeredRepository;

    @Test
    public void testCountByEmailOrUsername(){
        long count = registeredRepository.countByEmailOrUsername(NEW_EMAIL_EXIST, DB_USERNAME);

        assertEquals(DB_COUNT_TWO, count);
    }

    @Test
    public void testCountByEmailOrUsernameInvalidEmail(){
        long count = registeredRepository.countByEmailOrUsername(DB_EMAIL_NONEXIST, DB_USERNAME);

        assertEquals(DB_COUNT, count);
    }

    @Test
    public void testCountByEmailOrUsernameInvalidUsername(){
        long count = registeredRepository.countByEmailOrUsername(DB_EMAIL, DB_USERNAME_NONEXIST);

        assertEquals(DB_COUNT, count);
    }

    @Test
    public void testCountByEmailOrUsernameInvalid(){
        long count = registeredRepository.countByEmailOrUsername(DB_EMAIL_NONEXIST, DB_USERNAME_NONEXIST);

        assertEquals(DB_COUNT_INVALID, count);
    }

    @Test
    public void testFindByEmail(){
        Registered found = registeredRepository.findByEmail(DB_EMAIL);

        assertEquals(DB_EMAIL, found.getEmail());
    }

    @Test
    public void testFindByEmailInvalid(){
        Registered found = registeredRepository.findByEmail(DB_EMAIL_NONEXIST);

        assertNull(found);
    }

    @Test
    public void testFindByUsername(){
        Registered found = registeredRepository.findByUsername(DB_USERNAME);

        assertEquals(DB_EMAIL, found.getEmail());
    }

    @Test
    public void testFindByUsernameInvalid(){
        Registered found = registeredRepository.findByUsername(DB_USERNAME_NONEXIST);

        assertNull(found);
    }

    @Test
    public void testFindByUsernameOrEmail(){
        Registered found = registeredRepository.findByUsernameOrEmail(DB_USERNAME, DB_EMAIL_NONEXIST);

        assertEquals(DB_USERNAME, found.getUsername());

        assertNotEquals(DB_EMAIL_NONEXIST, found.getEmail());
    }

    @Test
    public void testFindByUsernameOrEmailInvalid(){
        Registered found = registeredRepository.findByUsernameOrEmail(DB_USERNAME_NONEXIST, DB_EMAIL_NONEXIST);

        assertNull(found);
    }

    @Test
    public void testFindRegisteredForSubscribedCulturalOffers() {
        List<Registered> found = registeredRepository.findRegisteredForSubscribedCulturalOffers(CULTURAL_OFFER_ID);

        assertEquals(COUNT_CULTURAL_OFFER, found.size());
    }

    @Test
    public void testFindRegisteredForSubscribedCulturalOffersInvalid() {
        List<Registered> found = registeredRepository.findRegisteredForSubscribedCulturalOffers(CULTURAL_OFFER_ID_INVALID);

        assertEquals(COUNT_CULTURAL_OFFER_EMPTY, found.size());
    }
}
