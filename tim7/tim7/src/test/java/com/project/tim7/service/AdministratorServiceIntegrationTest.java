package com.project.tim7.service;

import com.project.tim7.model.Administrator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.project.tim7.constants.AdministratorConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class AdministratorServiceIntegrationTest {

    @Autowired
    AdministratorService administratorService;

    @Autowired
    AdministratorService adminService;

    @Test
    public void findAll() {
        List<Administrator> found = administratorService.findAll();

        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.size());
    }

    @Test
    public void findAllPageable() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
        Page<Administrator> found = administratorService.findAll(pageable);

        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.getNumberOfElements());
    }

    @Test
    public void findAllPageableInvalid() {
        Pageable pageable = PageRequest.of(2,PAGEABLE_SIZE);
        Page<Administrator> found = administratorService.findAll(pageable);

        assertEquals(FIND_ALL_NUMBER_OF_ITEMS_EMPTY_PAGE, found.getNumberOfElements());
    }

    @Test
    public void findOne() {
        Administrator found = administratorService.findOne(ADMIN_ID);

        assertEquals(ADMIN_ID, found.getId());
    }

    @Test
    public void findOneInvalid() {
        Administrator found = administratorService.findOne(ADMIN_ID_NONEXIST);

        assertNull(found);
    }

    @Test
    public void saveOne() {
        Administrator admin = new Administrator(ADMIN_ID, NEW_EMAIL, NEW_USERNAME, NEW_PASSWORD);
        Administrator saved = administratorService.saveOne(admin);

        assertEquals(NEW_EMAIL, saved.getEmail());
        assertEquals(NEW_USERNAME, saved.getUsername());
        assertEquals(NEW_PASSWORD, saved.getPassword());

        //returning db to original state
        administratorService.delete(saved.getId());

    }

    @Test
    public void saveOneInvalidEmail() {
        Administrator admin = new Administrator(ADMIN_ID, DB_EMAIL, NEW_USERNAME, NEW_PASSWORD);
        Administrator saved = administratorService.saveOne(admin);

        assertNull(saved);
    }

    @Test
    public void saveOneInvalidUsername() {
        Administrator admin = new Administrator(ADMIN_ID, NEW_EMAIL, DB_USERNAME, NEW_PASSWORD);
        Administrator saved = administratorService.saveOne(admin);

        assertNull(saved);
    }

    @Test
    @Transactional
    @Rollback
    public void delete() {
        boolean deleted = administratorService.delete(ADMIN_ID);

        assertTrue(deleted);
    }

    @Test
    public void deleteInvalid() {
        boolean deleted = administratorService.delete(ADMIN_ID_NONEXIST);

        assertFalse(deleted);
    }

    @Test
    public void update() {
        Administrator admin = new Administrator(ADMIN_ID, NEW_EMAIL, DB_USERNAME, NEW_PASSWORD);
        Administrator updated = administratorService.update(admin);

        assertEquals(NEW_EMAIL, updated.getEmail());
        assertEquals(NEW_PASSWORD, updated.getPassword());

        //returning db to original state
        Administrator admin_restore = new Administrator(ADMIN_ID, DB_EMAIL, DB_USERNAME, DB_PASSWORD);
        administratorService.update(admin_restore);
    }

    @Test
    public void updateNewEmail() {
        Administrator admin = new Administrator(ADMIN_ID, NEW_EMAIL, DB_USERNAME, DB_PASSWORD);
        Administrator updated = administratorService.update(admin);

        assertEquals(NEW_EMAIL, updated.getEmail());
        assertEquals(DB_PASSWORD, updated.getPassword());

        //returning db to original state
        Administrator admin_restore = new Administrator(ADMIN_ID, DB_EMAIL, DB_USERNAME, DB_PASSWORD);
        administratorService.update(admin_restore);
    }

    @Test
    public void updateNewPassword() {
        Administrator admin = new Administrator(ADMIN_ID, DB_EMAIL, DB_USERNAME, NEW_PASSWORD);
        Administrator updated = administratorService.update(admin);

        assertEquals(DB_EMAIL, updated.getEmail());
        assertEquals(NEW_PASSWORD, updated.getPassword());

        //returning db to original state
        Administrator admin_restore = new Administrator(ADMIN_ID, DB_EMAIL, DB_USERNAME, DB_PASSWORD);
        administratorService.update(admin_restore);
    }

    @Test
    public void updateExistAdminEmail() {
        Administrator admin = new Administrator(ADMIN_ID, NEW_EMAIL_EXIST, DB_USERNAME, DB_PASSWORD);
        Administrator updated = administratorService.update(admin);

        assertNull(updated);
    }

    @Test
    public void updateExistRegEmail() {
        Administrator admin = new Administrator(ADMIN_ID, NEW_EMAIL_EXIST_REG, DB_USERNAME, DB_PASSWORD);
        Administrator updated = administratorService.update(admin);

        assertNull(updated);
    }

    @Test
    public void updateInvalidUsername() {
        Administrator admin = new Administrator(ADMIN_ID, NEW_EMAIL, DB_USERNAME_NONEXIST, DB_PASSWORD);
        Administrator updated = administratorService.update(admin);

        assertNull(updated);
    }

    @Test
    public void countByEmailOrUsername(){
        long count = administratorService.countByEmailOrUsername(DB_EMAIL, DB_USERNAME_NONEXIST);

        assertEquals(DB_COUNT, count);
    }

    @Test
    public void countByEmailOrUsernameInvalid(){
        long count = administratorService.countByEmailOrUsername(DB_EMAIL_NONEXIST, DB_USERNAME_NONEXIST);

        assertEquals(DB_COUNT_INVALID, count);
    }

    @Test
    public void findByEmail(){
        Administrator found = administratorService.findByEmail(DB_EMAIL);

        assertEquals(DB_EMAIL, found.getEmail());
    }

    @Test
    public void findByEmailInvalid(){
        Administrator found = administratorService.findByEmail(DB_EMAIL_NONEXIST);

        assertNull(found);
    }

    @Test
    public void findByUsernameOrEmail(){
        Administrator found = administratorService.findByUsernameOrEmail(DB_USERNAME, DB_EMAIL_NONEXIST);

        assertEquals(DB_USERNAME, found.getUsername());
        assertNotEquals(DB_EMAIL_NONEXIST, found.getEmail());
    }

    @Test
    public void findByUsernameOrEmailInvalid(){
        Administrator found = administratorService.findByUsernameOrEmail(DB_USERNAME_NONEXIST, DB_EMAIL_NONEXIST);

        assertNull(found);
    }
}
