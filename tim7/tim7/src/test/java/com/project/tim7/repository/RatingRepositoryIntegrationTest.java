package com.project.tim7.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import static com.project.tim7.constants.RatingConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class RatingRepositoryIntegrationTest {

    @Autowired
    RatingRepository ratingRepository;

    @Test
    public void testFindAverageRate(){
        double average = ratingRepository.findAverageRate(REPO_VALID_CULTURAL_OFFER);
        assertEquals(3.4, average);
    }

    @Test
    public void testFindRate(){
        double rate = ratingRepository.findRateRegistered(REPO_VALID_CULTURAL_OFFER, REPO_VALID_REGISTERED);
        assertEquals(4.5, rate);
    }

    @Test
    public void testAlreadyRated(){
        long rated = ratingRepository.alreadyRated(REPO_VALID_CULTURAL_OFFER, REPO_REGISTERED_ALREADY_RATED);
        assertEquals(1, rated);
    }

    @Test
    public void testAlreadyRatedNot(){
        long rated = ratingRepository.alreadyRated(REPO_OFFER_NOT_RATED, REPO_VALID_REGISTERED);
        assertEquals(0, rated);
    }
}
