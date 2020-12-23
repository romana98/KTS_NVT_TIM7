package com.project.tim7.service;

import com.project.tim7.model.Authority;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.project.tim7.constants.AuthorityConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class AuthorityServiceIntegrationTest {

    @Autowired
    AuthorityService authorityService;

    @Test
    public void testFindById(){
        List<Authority> auths = authorityService.findById(AUTH);

        assertEquals(FIND_NUMBER_OF_ITEMS, auths.size());
    }

    @Test
    public void testFindByIdInvalid(){
        List<Authority> auths = authorityService.findById(AUTH_NONEXITST);

        assertEquals(FIND_NUMBER_OF_ITEMS_NONEXIST, auths.size());
    }



}
