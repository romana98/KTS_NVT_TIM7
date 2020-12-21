package com.project.tim7.service;

import com.project.tim7.model.Registered;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.project.tim7.constants.RegisteredConstants.*;
import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class RegisteredServiceIntegrationTest {

    @Autowired
    RegisteredService registeredService;

    @Test
    public void testFindAll() {
        List<Registered> found = registeredService.findAll();

        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.size());
    }

    @Test
    public void testFindAllPageable() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
        Page<Registered> found = registeredService.findAll(pageable);

        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.getNumberOfElements());
    }

    @Test
    public void testFindAllPageableInvalid() {
        Pageable pageable = PageRequest.of(2,PAGEABLE_SIZE);
        Page<Registered> found = registeredService.findAll(pageable);

        assertEquals(FIND_ALL_NUMBER_OF_ITEMS_EMPTY_PAGE, found.getNumberOfElements());
    }

    @Test
    public void testFindOne() {
        Registered found = registeredService.findOne(REG_ID);

        assertEquals(REG_ID, found.getId());
    }

    @Test
    public void testFindOneInvalid() {
        Registered found = registeredService.findOne(REG_ID_NONEXIST);

        assertNull(found);
    }

    @Test
    public void testSaveOne() {
        Registered reg = new Registered(NEW_EMAIL, NEW_USERNAME, NEW_PASSWORD);
        Registered saved = registeredService.saveOne(reg);

        assertEquals(NEW_EMAIL, saved.getEmail());
        assertEquals(NEW_USERNAME, saved.getUsername());
        assertEquals(NEW_PASSWORD, saved.getPassword());

        //returning db to original state
        boolean isDeleted = registeredService.delete(saved.getId());
        assertTrue(isDeleted);
    }

    @Test
    public void testSaveOneInvalidUsername() {
        Registered reg = new Registered(NEW_EMAIL, DB_USERNAME, NEW_PASSWORD);
        Registered saved = registeredService.saveOne(reg);

        assertNull(saved);
    }

    @Test
    @Sql(scripts = "classpath:insert-service-reg-data-h2.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testDelete() {
        boolean deleted = registeredService.delete(REG_ID_NO_DEPENDENCY);

        assertTrue(deleted);
    }

    @Test
    public void testDeleteInvalid() {
        boolean deleted = registeredService.delete(REG_ID_NONEXIST);

        assertFalse(deleted);
    }

    @Test
    public void testDeleteInvalidDependency() {
        boolean deleted = registeredService.delete(REG_ID);

        assertFalse(deleted);
    }

    @Test
    public void testUpdate() {
        Registered reg = new Registered(REG_ID, NEW_EMAIL, DB_USERNAME, NEW_PASSWORD);
        Registered updated = registeredService.update(reg);

        assertEquals(NEW_EMAIL, updated.getEmail());
        assertEquals(NEW_PASSWORD, updated.getPassword());

        //returning db to original state
        Registered reg_restore = new Registered(REG_ID, DB_EMAIL, DB_USERNAME, DB_PASSWORD);
        registeredService.update(reg_restore);
    }

    @Test
    public void testUpdateNewEmail() {
        Registered reg = new Registered(REG_ID, NEW_EMAIL, DB_USERNAME, DB_PASSWORD);
        Registered updated = registeredService.update(reg);

        assertEquals(NEW_EMAIL, updated.getEmail());
        assertEquals(DB_PASSWORD, updated.getPassword());

        //returning db to original state
        Registered reg_restore = new Registered(REG_ID, DB_EMAIL, DB_USERNAME, DB_PASSWORD);
        registeredService.update(reg_restore);
    }

    @Test
    public void testUpdateNewPassword() {
        Registered reg = new Registered(REG_ID, DB_EMAIL, DB_USERNAME, NEW_PASSWORD);
        Registered updated = registeredService.update(reg);

        assertEquals(DB_EMAIL, updated.getEmail());
        assertEquals(NEW_PASSWORD, updated.getPassword());

        //returning db to original state
        Registered reg_restore = new Registered(REG_ID, DB_EMAIL, DB_USERNAME, DB_PASSWORD);
        registeredService.update(reg_restore);
    }

    @Test
    public void testUpdateExistAdminEmail() {
        Registered reg = new Registered(REG_ID, NEW_EMAIL_EXIST_ADMIN, DB_USERNAME, DB_PASSWORD);
        Registered updated = registeredService.update(reg);

        assertNull(updated);
    }

    @Test
    public void testUpdateExistRegEmail() {
        Registered reg = new Registered(REG_ID, NEW_EMAIL_EXIST, DB_USERNAME, DB_PASSWORD);
        Registered updated = registeredService.update(reg);

        assertNull(updated);
    }

    @Test
    public void testUpdateInvalidUsername() {
        Registered reg = new Registered(REG_ID, NEW_EMAIL, DB_USERNAME_NONEXIST, DB_PASSWORD);
        Registered updated = registeredService.update(reg);

        assertNull(updated);
    }

    @Test
    public void testCountByEmailOrUsername(){
        long count = registeredService.countByEmailOrUsername(DB_EMAIL, DB_USERNAME_NONEXIST);

        assertEquals(DB_COUNT, count);
    }

    @Test
    public void testCountByEmailOrUsernameInvalid(){
        long count = registeredService.countByEmailOrUsername(DB_EMAIL_NONEXIST, DB_USERNAME_NONEXIST);

        assertEquals(DB_COUNT_INVALID, count);
    }

    @Test
    public void testFindByUsernameOrEmail(){
        Registered found = registeredService.findByUsernameOrEmail(DB_USERNAME, DB_EMAIL_NONEXIST);

        assertEquals(DB_USERNAME, found.getUsername());
        assertNotEquals(DB_EMAIL_NONEXIST, found.getEmail());
    }

    @Test
    public void testFindByUsernameOrEmailInvalid(){
        Registered found = registeredService.findByUsernameOrEmail(DB_USERNAME_NONEXIST, DB_EMAIL_NONEXIST);

        assertNull(found);
    }

    @Test
    public void testFindByEmail(){
        Registered found = registeredService.findByEmail(DB_EMAIL);

        assertEquals(DB_EMAIL, found.getEmail());
    }

    @Test
    public void testFindByEmailInvalid(){
        Registered found = registeredService.findByEmail(DB_EMAIL_NONEXIST);

        assertNull(found);
    }

    @Test
    public void testFindByUsername(){
        Registered found = registeredService.findByUsername(DB_USERNAME);

        assertEquals(DB_USERNAME, found.getUsername());
    }

    @Test
    public void testFindByUsernameInvalid(){
        Registered found = registeredService.findByUsername(DB_USERNAME_NONEXIST);

        assertNull(found);
    }

    @Test
    public void testActivateAccount() {
        Registered found = registeredService.activateAccount(REG_ID);

        assertEquals(REG_ID, found.getId());
        assertTrue(found.isVerified());

        //returning db to original state
        Registered reg_restore = new Registered(REG_ID, DB_EMAIL, DB_USERNAME, DB_PASSWORD);
        reg_restore.setVerified(false);
        registeredService.update(reg_restore);
    }

    @Test
    public void testActivateAccountInvalid() {
        Registered found = registeredService.activateAccount(REG_ID_NONEXIST);

        assertNull(found);
    }

    @Test
    public void testRegisterUser() {
        Registered reg = new Registered(NEW_EMAIL, NEW_USERNAME, NEW_PASSWORD);
        Registered saved = registeredService.saveOne(reg);

        assertEquals(NEW_EMAIL, saved.getEmail());
        assertEquals(NEW_USERNAME, saved.getUsername());
        assertEquals(NEW_PASSWORD, saved.getPassword());

        //returning db to original state
        boolean isDeleted = registeredService.delete(saved.getId());
        assertTrue(isDeleted);
    }

    @Test
    public void testRegisterUserInvalid() {
        Registered reg = new Registered(NEW_EMAIL, DB_USERNAME, NEW_PASSWORD);
        Registered saved = registeredService.saveOne(reg);

        assertNull(saved);
    }

    @Test
    public void testFindRegisteredForSubscribedCulturalOffers(){
        List<String> emails = registeredService.findRegisteredForSubscribedCulturalOffers(CULTURAL_OFFER_ID);

        assertEquals(COUNT_EMAILS, emails.size());
    }

    @Test
    public void testFindRegisteredForSubscribedCulturalOffersInvalid(){
        List<String> emails = registeredService.findRegisteredForSubscribedCulturalOffers(CULTURAL_OFFER_ID_INVALID);

        assertEquals(COUNT_EMAILS_INVALID, emails.size());
    }

}
