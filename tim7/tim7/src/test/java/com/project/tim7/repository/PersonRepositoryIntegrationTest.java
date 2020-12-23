package com.project.tim7.repository;

import com.project.tim7.constants.AdministratorConstants;
import com.project.tim7.constants.RegisteredConstants;
import com.project.tim7.model.Administrator;
import com.project.tim7.model.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class PersonRepositoryIntegrationTest {

    @Autowired
    PersonRepository personRepository;

    @Test
    public void testFindByUsernameAdmin(){
        Person found = personRepository.findByUsername(AdministratorConstants.DB_USERNAME);

        assertEquals(AdministratorConstants.DB_USERNAME, found.getUsername());
    }

    public void testFindByUsernameReg(){
        Person found = personRepository.findByUsername(RegisteredConstants.DB_USERNAME);

        assertEquals(RegisteredConstants.DB_USERNAME, found.getUsername());
    }

    @Test
    public void testFindByUsernameInvalid(){
        Person found = personRepository.findByUsername(AdministratorConstants.DB_USERNAME_NONEXIST);

        assertNull(found);
    }
}
