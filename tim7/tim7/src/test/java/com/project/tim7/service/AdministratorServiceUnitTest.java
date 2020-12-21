package com.project.tim7.service;

import com.project.tim7.model.Administrator;
import com.project.tim7.model.Registered;
import com.project.tim7.repository.AdministratorRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static com.project.tim7.constants.AdministratorConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class AdministratorServiceUnitTest {

    @Autowired
    AdministratorService administratorService;

    @MockBean
    private AdministratorRepository administratorRepository;

    @MockBean
    private RegisteredService registeredService;

    @Before
    public void setup() {
        Administrator administrator = new Administrator(ADMIN_ID, DB_EMAIL, DB_USERNAME, DB_PASSWORD);

        given(administratorRepository.findByUsername(DB_USERNAME)).willReturn(administrator);
        given(administratorRepository.findByUsername(DB_USERNAME)).willReturn(administrator);
        given(administratorRepository.findByUsername(DB_USERNAME_NONEXIST)).willReturn(null);

        given(administratorRepository.findByEmail(DB_EMAIL)).willReturn(administrator);
        given(administratorRepository.findByEmail(NEW_EMAIL)).willReturn(null);


        Administrator administratorExist = new Administrator(ADMIN_ID_EXIST, NEW_EMAIL_EXIST, DB_USERNAME_EXIST, DB_PASSWORD);
        given(administratorRepository.findByEmail(NEW_EMAIL_EXIST)).willReturn(administratorExist);

        Administrator administratorUpdateEmail = new Administrator(ADMIN_ID, NEW_EMAIL, DB_USERNAME, DB_PASSWORD);
        given(administratorRepository.save(administratorUpdateEmail)).willReturn(administratorUpdateEmail);

        Administrator administratorUpdate = new Administrator(ADMIN_ID, NEW_EMAIL, DB_USERNAME, NEW_PASSWORD);
        given(administratorRepository.save(administratorUpdate)).willReturn(administratorUpdate);

        Administrator administratorUpdatePass = new Administrator(ADMIN_ID, DB_EMAIL, DB_USERNAME, NEW_PASSWORD);
        given(administratorRepository.save(administratorUpdatePass)).willReturn(administratorUpdatePass);





        Registered registered = new Registered(REG_ID_EXIST, NEW_EMAIL_EXIST_REG, REG_USERNAME_EXIST, DB_PASSWORD);

        given(registeredService.findByEmail(NEW_EMAIL)).willReturn(registered);
        given(registeredService.findByEmail(NEW_EMAIL)).willReturn(null);
    }

    @Test
    public void update(){
        Administrator admin = new Administrator(ADMIN_ID, NEW_EMAIL, DB_USERNAME, NEW_PASSWORD);
        Administrator updated = administratorService.update(admin);

        verify(administratorRepository, times(1)).findByUsername(DB_USERNAME);
        verify(administratorRepository, times(1)).findByEmail(NEW_EMAIL);
        verify(administratorRepository, times(1)).save(admin);
        verify(registeredService, times(1)).findByEmail(NEW_EMAIL);

        assertEquals(NEW_EMAIL, updated.getEmail());
        assertEquals(NEW_PASSWORD, updated.getPassword());
    }

    @Test
    public void updateNewEmail(){
        Administrator admin = new Administrator(ADMIN_ID, NEW_EMAIL, DB_USERNAME, DB_PASSWORD);
        Administrator updated = administratorService.update(admin);

        verify(administratorRepository, times(1)).findByUsername(DB_USERNAME);
        verify(administratorRepository, times(1)).findByEmail(NEW_EMAIL);
        verify(administratorRepository, times(1)).save(admin);
        verify(registeredService, times(1)).findByEmail(NEW_EMAIL);

        assertEquals(NEW_EMAIL, updated.getEmail());
        assertEquals(DB_PASSWORD, updated.getPassword());
    }

    @Test
    public void updateNewPassword(){
        Administrator admin = new Administrator(ADMIN_ID, DB_EMAIL, DB_USERNAME, NEW_PASSWORD);
        Administrator updated = administratorService.update(admin);

        verify(administratorRepository, times(1)).findByUsername(DB_USERNAME);
        verify(administratorRepository, times(1)).save(admin);

        assertEquals(DB_EMAIL, updated.getEmail());
        assertEquals(NEW_PASSWORD, updated.getPassword());
    }

    @Test
    public void updateExistAdminEmail(){
        Administrator admin = new Administrator(ADMIN_ID, NEW_EMAIL_EXIST, DB_USERNAME, DB_PASSWORD);
        Administrator updated = administratorService.update(admin);

        verify(administratorRepository, times(1)).findByUsername(DB_USERNAME);
        verify(administratorRepository, times(1)).findByEmail(NEW_EMAIL_EXIST);

        assertNull(updated);
    }

    @Test
    public void updateExistRegEmail(){
        Administrator admin = new Administrator(ADMIN_ID, NEW_EMAIL_EXIST_REG, DB_USERNAME, DB_PASSWORD);
        Administrator updated = administratorService.update(admin);

        verify(administratorRepository, times(1)).findByUsername(DB_USERNAME);
        verify(administratorRepository, times(1)).findByEmail(NEW_EMAIL_EXIST_REG);
        verify(registeredService, times(1)).findByEmail(NEW_EMAIL_EXIST_REG);

        assertNull(updated);
    }

    @Test
    public void updateInvalidUsername() {
        Administrator admin = new Administrator(ADMIN_ID, NEW_EMAIL, DB_USERNAME_NONEXIST, DB_PASSWORD);
        Administrator updated = administratorService.update(admin);

        verify(administratorRepository, times(1)).findByUsername(DB_USERNAME_NONEXIST);

        assertNull(updated);
    }
}
