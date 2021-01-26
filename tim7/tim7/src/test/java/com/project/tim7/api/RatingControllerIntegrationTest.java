package com.project.tim7.api;

import com.project.tim7.dto.RatingDTO;
import com.project.tim7.dto.SubcategoryDTO;
import com.project.tim7.dto.UserLoginDTO;
import com.project.tim7.dto.UserTokenStateDTO;
import com.project.tim7.service.RatingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static com.project.tim7.constants.RatingConstants.*;
import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class RatingControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers;

    @Autowired
    RatingController ratingController;

    @Autowired
    RatingService ratingService;

    public void loginAdmin() {
        ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.postForEntity("/auth/log-in",
                new UserLoginDTO(CONTROLLER_DB_USERNAME_ADMIN, CONTROLLER_DB_PASSWORD), UserTokenStateDTO.class);

        String accessToken = "Bearer " + responseEntity.getBody().getAccessToken();

        headers = new HttpHeaders();
        headers.add("Authorization", accessToken);

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Content-Type", "application/json");
    }

    public void loginReg() {
        ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.postForEntity("/auth/log-in",
                new UserLoginDTO(CONTROLLER_DB_USERNAME_REG, CONTROLLER_DB_PASSWORD), UserTokenStateDTO.class);

        String accessToken = "Bearer " + responseEntity.getBody().getAccessToken();

        headers = new HttpHeaders();
        headers.add("Authorization", accessToken);

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Content-Type", "application/json");

    }

    @Test
    public void testCreateRatingValid(){
        loginReg();
        RatingDTO ratingDTO = new RatingDTO();
        ratingDTO.setCulturalOfferId(CONTROLLER_NEW_RATING_CULTURAL_OFFER);
        ratingDTO.setRate(CONTROLLER_NEW_RATING_RATE);
        ratingDTO.setRegisteredId(CONTROLLER_NEW_RATING_REGISTERED);
        HttpEntity<Object> httpEntity = new HttpEntity<>(ratingDTO, headers);

        ResponseEntity<RatingDTO> responseEntity =
                restTemplate.exchange("/ratings", HttpMethod.POST, httpEntity, RatingDTO.class);
        RatingDTO created = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(CONTROLLER_NEW_RATING_REGISTERED, ratingDTO.getRegisteredId());
        assertEquals(CONTROLLER_NEW_RATING_CULTURAL_OFFER, ratingDTO.getCulturalOfferId());

        boolean isDeleted = ratingService.delete(created.getId());
        assertTrue(isDeleted);
    }

    @Test
    public void testCreateRatingInvalidRegisteredLoggedIn(){
        loginReg();
        RatingDTO ratingDTO = new RatingDTO();
        ratingDTO.setCulturalOfferId(CONTROLLER_NEW_RATING_CULTURAL_OFFER);
        ratingDTO.setRate(CONTROLLER_NEW_RATING_RATE);
        ratingDTO.setRegisteredId(CONTROLLER_NEW_RATING_REGISTERED_INVALID);
        HttpEntity<Object> httpEntity = new HttpEntity<>(ratingDTO, headers);

        ResponseEntity<RatingDTO> responseEntity =
                restTemplate.exchange("/ratings", HttpMethod.POST, httpEntity, RatingDTO.class);
        RatingDTO created = responseEntity.getBody();
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertNull(created);
    }

    @Test
    public void testCreateRatingInvalidCulturalOffer(){
        loginReg();
        RatingDTO ratingDTO = new RatingDTO();
        ratingDTO.setCulturalOfferId(CONTROLLER_NEW_RATING_CULTURAL_OFFER_INVALID);
        ratingDTO.setRate(CONTROLLER_NEW_RATING_RATE);
        ratingDTO.setRegisteredId(CONTROLLER_NEW_RATING_REGISTERED);
        HttpEntity<Object> httpEntity = new HttpEntity<>(ratingDTO, headers);

        ResponseEntity<RatingDTO> responseEntity =
                restTemplate.exchange("/ratings", HttpMethod.POST, httpEntity, RatingDTO.class);
        RatingDTO created = responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(created);
    }

    @Test
    public void testCreateRatingInvalidAlreadyRated(){
        loginReg();
        RatingDTO ratingDTO = new RatingDTO();
        ratingDTO.setCulturalOfferId(CONTROLLER_NEW_RATING_CULTURAL_OFFER_ALREADY_RATED);
        ratingDTO.setRate(CONTROLLER_NEW_RATING_RATE);
        ratingDTO.setRegisteredId(CONTROLLER_NEW_RATING_REGISTERED);
        HttpEntity<Object> httpEntity = new HttpEntity<>(ratingDTO, headers);

        ResponseEntity<RatingDTO> responseEntity =
                restTemplate.exchange("/ratings", HttpMethod.POST, httpEntity, RatingDTO.class);
        RatingDTO created = responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(created);
    }

    @Test
    public void testGetRatingRegistered(){
        loginReg();
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<RatingDTO> responseEntity =
                restTemplate.exchange("/ratings/getRating/2", HttpMethod.GET, httpEntity, RatingDTO.class);
        RatingDTO found = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(4.5, found.getRate());
    }

    @Test
    public void testGetRatingAdmin(){
        loginAdmin();
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<RatingDTO> responseEntity =
                restTemplate.exchange("/ratings/getRating/2", HttpMethod.GET, httpEntity, RatingDTO.class);
        RatingDTO found = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(0.0, found.getRate());
    }

    @Test
    public void testGetRatingUnauthorized(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<RatingDTO> responseEntity =
                restTemplate.exchange("/ratings/getRating/2", HttpMethod.GET, httpEntity, RatingDTO.class);
        RatingDTO found = responseEntity.getBody();
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertEquals(0.0, found.getRate());
    }

    @Test
    public void testGetRatingOfferDoesntExist(){
        loginReg();
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<RatingDTO> responseEntity =
                restTemplate.exchange("/ratings/getRating/200", HttpMethod.GET, httpEntity, RatingDTO.class);
        RatingDTO found = responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(0.0, found.getRate());
    }

    @Test
    public void testGetAverageRatingRegistered(){
        loginReg();
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<RatingDTO> responseEntity =
                restTemplate.exchange("/ratings/getRating/average/2", HttpMethod.GET, httpEntity, RatingDTO.class);
        RatingDTO found = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(3.4, found.getRate());
    }

    @Test
    public void testGetAverageRatingAdmin(){
        loginAdmin();
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<RatingDTO> responseEntity =
                restTemplate.exchange("/ratings/getRating/average/2", HttpMethod.GET, httpEntity, RatingDTO.class);
        RatingDTO found = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(3.4, found.getRate());
    }

    @Test
    public void testGetAverageRatingUnauthorized(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<RatingDTO> responseEntity =
                restTemplate.exchange("/ratings/getRating/average/2", HttpMethod.GET, httpEntity, RatingDTO.class);
        RatingDTO found = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(3.4, found.getRate());
    }

    @Test
    public void testGetAverageRatingInvalid(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<RatingDTO> responseEntity =
                restTemplate.exchange("/ratings/getRating/average/100", HttpMethod.GET, httpEntity, RatingDTO.class);
        RatingDTO found = responseEntity.getBody();
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(-1.0, found.getRate());
    }

}
