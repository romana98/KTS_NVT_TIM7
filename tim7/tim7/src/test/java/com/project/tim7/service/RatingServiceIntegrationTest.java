package com.project.tim7.service;

import com.project.tim7.model.CulturalOffer;
import com.project.tim7.model.Rating;
import com.project.tim7.model.Registered;
import com.project.tim7.repository.RatingRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import static com.project.tim7.constants.RatingConstants.*;
import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class RatingServiceIntegrationTest {

    @Autowired
    RatingService ratingService;

    @Autowired
    RatingRepository ratingRepository;

    @Autowired
    RegisteredService registeredService;

    @Autowired
    CulturalOfferService culturalOfferService;

    @Autowired
    AdministratorService administratorService;

    @Test
    public void testFindOneValid(){
        Rating rating = ratingService.findOne(SERVICE_VALID_RATING_ID);
        assertEquals(SERVICE_VALID_RATING_ID, rating.getId());
    }

    @Test
    public void testFindOneInvalid(){
        Rating rating = ratingService.findOne(SERVICE_INVALID_RATING_ID);
        assertNull(rating);
    }

    @Test
    public void testCreateNewRatingValid(){
        Rating newRating = new Rating();
        newRating.setRate(SERVICE_NEW_RATING_RATE);
        newRating.setRegistered(new Registered(SERVICE_NEW_RATING_REGISTERED));
        Rating created = ratingService.createRating(newRating, SERVICE_NEW_RATING_CULTURAL_OFFER);
        assertEquals(SERVICE_NEW_RATING_REGISTERED, created.getRegistered().getId());
        assertEquals(SERVICE_NEW_RATING_RATE, created.getRate());
        assertNotNull(created);
        boolean deleted = ratingService.delete(created.getId());
        assertTrue(deleted);
    }

    @Test
    public void testCreateNewRatingInvalidRegistered(){
        Rating newRating = new Rating();
        newRating.setRate(SERVICE_NEW_RATING_RATE);
        newRating.setRegistered(new Registered(SERVICE_NEW_RATING_REGISTERED_INVALID));
        Rating created = ratingService.createRating(newRating, SERVICE_NEW_RATING_CULTURAL_OFFER);
        assertNull(created);
    }

    @Test
    public void testCreateNewRatingInvalidCulturalOffer(){
        Rating newRating = new Rating();
        newRating.setRate(SERVICE_NEW_RATING_RATE);
        newRating.setRegistered(new Registered(SERVICE_NEW_RATING_REGISTERED));
        Rating created = ratingService.createRating(newRating, SERVICE_NEW_RATING_CULTURAL_OFFER_INVALID);
        assertNull(created);
    }

    @Test
    public void testCreateNewRatingAlreadyRated(){
        Rating newRating = new Rating();
        newRating.setRate(SERVICE_NEW_RATING_RATE);
        newRating.setRegistered(new Registered(SERVICE_NEW_RATING_REGISTERED_ALREADY_RATED));
        Rating created = ratingService.createRating(newRating, SERVICE_NEW_RATING_CULTURAL_OFFER_ALREADY_RATED);
        assertNull(created);
    }

    @Test
    public void testAlreadyRated(){
        long count = ratingService.alreadyRated(new CulturalOffer(SERVICE_NEW_RATING_CULTURAL_OFFER_ALREADY_RATED), new Registered(SERVICE_NEW_RATING_REGISTERED_ALREADY_RATED));
        assertEquals(1, count);
    }

    @Test
    public void testAlreadyRatedNOT(){
        long count = ratingService.alreadyRated(new CulturalOffer(SERVICE_NEW_RATING_CULTURAL_OFFER), new Registered(SERVICE_NEW_RATING_REGISTERED));
        assertEquals(0, count);
    }

    @Test
    public void testGetAverageRatingValid(){
        double avg = ratingService.getAverageRating(SERVICE_NEW_RATING_CULTURAL_OFFER_ALREADY_RATED);
        assertEquals(3.4, avg);
    }

    @Test
    public void testGetAverageRatingInvalid(){
        double avg = ratingService.getAverageRating(SERVICE_NEW_RATING_CULTURAL_OFFER_INVALID);
        assertEquals(-1.0, avg);
    }

    @Test
    public void testGetRatingValid(){
        double rating = ratingService.getRating(SERVICE_NEW_RATING_CULTURAL_OFFER_ALREADY_RATED, SERVICE_NEW_RATING_REGISTERED);
        assertEquals(4.5, rating);
    }

    @Test
    public void testGetRatingInvalidCulturalOffer(){
        double rating = ratingService.getRating(SERVICE_NEW_RATING_CULTURAL_OFFER_INVALID, SERVICE_NEW_RATING_REGISTERED_ALREADY_RATED);
        assertEquals(-1.0, rating);
    }

    @Test
    public void testGetRatingInvalidUser(){
        double rating = ratingService.getRating(SERVICE_NEW_RATING_CULTURAL_OFFER_ALREADY_RATED, SERVICE_NEW_RATING_REGISTERED_INVALID);
        assertEquals(-2.0, rating);
    }

    @Test
    public void testGetRatingInvalidAdmin(){
        double rating = ratingService.getRating(SERVICE_NEW_RATING_CULTURAL_OFFER_ALREADY_RATED, SERVICE_RATING_ADMIN);
        assertEquals(-3.0, rating);
    }

    @Test
    public void testGetRatingInvalidNoRate(){
        double rating = ratingService.getRating(SERVICE_NEW_RATING_CULTURAL_OFFER, SERVICE_NEW_RATING_REGISTERED);
        assertEquals(-4.0, rating);
    }
}
