package com.project.tim7.service;

import com.project.tim7.constants.AdministratorConstants;
import com.project.tim7.constants.RegisteredConstants;
import com.project.tim7.model.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CustomUserDetailsServiceIntegrationService {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Test
    public void testLoadUserByUsernameAdmin(){
        Person person  = (Person) customUserDetailsService.loadUserByUsername(AdministratorConstants.DB_USERNAME);

        assertEquals(AdministratorConstants.DB_USERNAME, person.getUsername());
    }

    @Test
    public void testLoadUserByUsernameReg(){
        Person person  = (Person) customUserDetailsService.loadUserByUsername(RegisteredConstants.DB_USERNAME);

        assertEquals(RegisteredConstants.DB_USERNAME, person.getUsername());
    }

    @Test
    public void testLoadUserByUsernameInvalid(){
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername(AdministratorConstants.DB_USERNAME_NONEXIST);
        });

        String expectedMessage = String.format("No user found with username '%s'.", AdministratorConstants.DB_USERNAME_NONEXIST);
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
