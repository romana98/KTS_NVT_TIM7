package com.project.tim7.api;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.tim7.api.CulturalOfferController.Subscribe;
import com.project.tim7.dto.*;
import com.project.tim7.helper.CulturalOfferMapper;
import com.project.tim7.helper.RestResponsePage;
import com.project.tim7.model.CulturalOffer;
import com.project.tim7.service.CulturalOfferService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import static com.project.tim7.constants.AdministratorConstants.*;
import static com.project.tim7.constants.AdministratorConstants.DB_USERNAME;
import static com.project.tim7.constants.CommentConstants.PAGEABLE_PAGE;
import static com.project.tim7.constants.CommentConstants.PAGEABLE_SIZE;
import static com.project.tim7.constants.CulturalOfferConstants.*;
import static com.project.tim7.constants.NewsletterConstants.DB_PASSWORD_RAW;
import static com.project.tim7.constants.NewsletterConstants.DB_USERNAME_REG;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CulturalOfferControllerIntegrationTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    CulturalOfferService culturalOfferService;

    private CulturalOfferMapper culturalOfferMapper = new CulturalOfferMapper();

    private HttpHeaders headers;

    // JWT token za pristup REST servisima. Bice dobijen pri logovanju
    public void login() {
        ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.postForEntity("/auth/log-in",
                new UserLoginDTO(DB_USERNAME,DB_PASSWORD_RAW), UserTokenStateDTO.class);

        String accessToken = "Bearer " + responseEntity.getBody().getAccessToken();

        headers = new HttpHeaders();
        headers.add("Authorization", accessToken);

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Content-Type", "application/json");
    }

    //Successfully adding not existing cultural offer with valid location and subcategory.
    @Test
    public void testCreateCulturalOfferValid() throws ParseException {
    	login();
        ArrayList<String> pictures = new ArrayList<>();
        pictures.add(SAVE_ONE_CULTURAL_OFFER_PICTURE1);
        pictures.add(SAVE_ONE_CULTURAL_OFFER_PICTURE2);

        CulturalOfferDTO culturalOffer = new CulturalOfferDTO(SAVE_ONE_CULTURAL_OFFER_NAME,SAVE_ONE_CULTURAL_OFFER_DESCRIPTION,
                new SimpleDateFormat("yyyy-MM-dd").parse(SAVE_ONE_CULTURAL_OFFER_STARTDATE),new SimpleDateFormat("yyyy-MM-dd").parse(SAVE_ONE_CULTURAL_OFFER_ENDDATE),
                SAVE_ONE_CULTURAL_OFFER_LOCATION,SAVE_ONE_CULTURAL_OFFER_SUBCATEGORY, pictures );

        HttpEntity<Object> httpEntity = new HttpEntity<>(culturalOffer,headers);

        ResponseEntity<CulturalOfferDTO> responseEntity =
                restTemplate.exchange("/cultural-offers", HttpMethod.POST, httpEntity,
                        CulturalOfferDTO.class);

        CulturalOfferDTO culturalOfferAdded = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(culturalOfferAdded);

        //Returning database to previous state.
        boolean isDeleted = culturalOfferService.delete(culturalOfferAdded.getId());
        assertTrue(isDeleted);
    }

    //Creating cultural offer with name that already exists.
    @Test
    public void testCreateCulturalOfferInvalidName() throws ParseException {
    	login();
        //CulturalOfferDTO culturalOffer = new CulturalOfferDTO();

        ArrayList<String> pictures = new ArrayList<>();
        pictures.add(SAVE_ONE_CULTURAL_OFFER_PICTURE1);
        pictures.add(SAVE_ONE_CULTURAL_OFFER_PICTURE2);

        CulturalOfferDTO culturalOffer = new CulturalOfferDTO(SAVE_ONE_CULTURAL_OFFER_NAME_FAIL,SAVE_ONE_CULTURAL_OFFER_DESCRIPTION,
                new SimpleDateFormat("yyyy-MM-dd").parse(SAVE_ONE_CULTURAL_OFFER_STARTDATE),new SimpleDateFormat("yyyy-MM-dd").parse(SAVE_ONE_CULTURAL_OFFER_ENDDATE),
                SAVE_ONE_CULTURAL_OFFER_LOCATION,SAVE_ONE_CULTURAL_OFFER_SUBCATEGORY, pictures );

        HttpEntity<Object> httpEntity = new HttpEntity<>(culturalOffer,headers);

        ResponseEntity<CulturalOfferDTO> responseEntity =
                restTemplate.exchange("/cultural-offers", HttpMethod.POST, httpEntity,
                        CulturalOfferDTO.class);

        CulturalOfferDTO culturalOfferAdded = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(culturalOfferAdded);
    }

    //Creating cultural offer with location that doesn't exists.
    @Test
    public void testCreateCulturalOfferInvalidLocation() throws ParseException {
    	login();

        ArrayList<String> pictures = new ArrayList<>();
        pictures.add(SAVE_ONE_CULTURAL_OFFER_PICTURE1);
        pictures.add(SAVE_ONE_CULTURAL_OFFER_PICTURE2);

        CulturalOfferDTO culturalOffer = new CulturalOfferDTO(SAVE_ONE_CULTURAL_OFFER_NAME,SAVE_ONE_CULTURAL_OFFER_DESCRIPTION,
                new SimpleDateFormat("yyyy-MM-dd").parse(SAVE_ONE_CULTURAL_OFFER_STARTDATE),new SimpleDateFormat("yyyy-MM-dd").parse(SAVE_ONE_CULTURAL_OFFER_ENDDATE),
                SAVE_ONE_CULTURAL_OFFER_LOCATION_FAIL,SAVE_ONE_CULTURAL_OFFER_SUBCATEGORY, pictures );

        HttpEntity<Object> httpEntity = new HttpEntity<>(culturalOffer,headers);

        ResponseEntity<CulturalOfferDTO> responseEntity =
                restTemplate.exchange("/cultural-offers", HttpMethod.POST, httpEntity,
                        CulturalOfferDTO.class);

        CulturalOfferDTO culturalOfferAdded = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(culturalOfferAdded);
    }

    //Creating cultural offer with subcategory that doesn't exists.
    @Test
    public void testCreateCulturalOfferInvalidSubcategory() throws ParseException {
    	login();

        ArrayList<String> pictures = new ArrayList<>();
        pictures.add(SAVE_ONE_CULTURAL_OFFER_PICTURE1);
        pictures.add(SAVE_ONE_CULTURAL_OFFER_PICTURE2);

        CulturalOfferDTO culturalOffer = new CulturalOfferDTO(SAVE_ONE_CULTURAL_OFFER_NAME,SAVE_ONE_CULTURAL_OFFER_DESCRIPTION,
                new SimpleDateFormat("yyyy-MM-dd").parse(SAVE_ONE_CULTURAL_OFFER_STARTDATE),new SimpleDateFormat("yyyy-MM-dd").parse(SAVE_ONE_CULTURAL_OFFER_ENDDATE),
                SAVE_ONE_CULTURAL_OFFER_LOCATION,SAVE_ONE_CULTURAL_OFFER_SUBCATEGORY_FAIL, pictures );

        HttpEntity<Object> httpEntity = new HttpEntity<>(culturalOffer,headers);

        ResponseEntity<CulturalOfferDTO> responseEntity =
                restTemplate.exchange("/cultural-offers", HttpMethod.POST, httpEntity,
                        CulturalOfferDTO.class);

        CulturalOfferDTO culturalOfferAdded = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(culturalOfferAdded);
    }

    //Creating cultural offer with two same pictures.
    @Test
    public void testCreateCulturalOfferInvalidPictures() throws ParseException {
    	login();

        ArrayList<String> pictures = new ArrayList<>();
        pictures.add(SAVE_ONE_CULTURAL_OFFER_PICTURE1);
        pictures.add(SAVE_ONE_CULTURAL_OFFER_PICTURE2);

        CulturalOfferDTO culturalOffer = new CulturalOfferDTO(SAVE_ONE_CULTURAL_OFFER_NAME,SAVE_ONE_CULTURAL_OFFER_DESCRIPTION,
                new SimpleDateFormat("yyyy-MM-dd").parse(SAVE_ONE_CULTURAL_OFFER_STARTDATE),new SimpleDateFormat("yyyy-MM-dd").parse(SAVE_ONE_CULTURAL_OFFER_ENDDATE),
                SAVE_ONE_CULTURAL_OFFER_LOCATION,SAVE_ONE_CULTURAL_OFFER_SUBCATEGORY_FAIL, pictures );

        HttpEntity<Object> httpEntity = new HttpEntity<>(culturalOffer,headers);

        ResponseEntity<CulturalOfferDTO> responseEntity =
                restTemplate.exchange("/cultural-offers", HttpMethod.POST, httpEntity,
                        CulturalOfferDTO.class);

        CulturalOfferDTO culturalOfferAdded = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(culturalOfferAdded);
    }

    //Updating cultural offer with valid parameters.
    @Test
    public void testUpdateCulturalOfferValid() throws ParseException {
    	login();
        ArrayList<String> pictures = new ArrayList<>();
        pictures.add(UPDATE_ONE_CULTURAL_OFFER_PICTURE1);
        pictures.add(UPDATE_ONE_CULTURAL_OFFER_PICTURE2);

        CulturalOfferDTO culturalOffer = new CulturalOfferDTO(UPDATE_ONE_CULTURAL_OFFER_ID,UPDATE_ONE_CULTURAL_OFFER_NAME,UPDATE_ONE_CULTURAL_OFFER_DESCRIPTION,
                new SimpleDateFormat("yyyy-MM-dd").parse(UPDATE_ONE_CULTURAL_OFFER_STARTDATE),new SimpleDateFormat("yyyy-MM-dd").parse(UPDATE_ONE_CULTURAL_OFFER_ENDDATE),
                UPDATE_ONE_CULTURAL_OFFER_LOCATION,UPDATE_ONE_CULTURAL_OFFER_SUBCATEGORY, pictures );

        HttpEntity<Object> httpEntity = new HttpEntity<>(culturalOffer,headers);

        ResponseEntity<CulturalOfferDTO> responseEntity =
                restTemplate.exchange("/cultural-offers", HttpMethod.PUT, httpEntity,
                        CulturalOfferDTO.class);

        CulturalOfferDTO culturalOfferAdded = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(culturalOfferAdded);

        CulturalOfferDTO culturalOfferRestore = new CulturalOfferDTO(OLD_CULTURAL_OFFER_ID,OLD_CULTURAL_OFFER_NAME,OLD_CULTURAL_OFFER_DESCRIPTION,
                new SimpleDateFormat("yyyy-MM-dd").parse(OLD_CULTURAL_OFFER_STARTDATE),new SimpleDateFormat("yyyy-MM-dd").parse(OLD_CULTURAL_OFFER_ENDDATE),
                OLD_CULTURAL_OFFER_LOCATION,OLD_CULTURAL_OFFER_SUBCATEGORY, pictures );

        culturalOfferService.update(culturalOfferRestore);

        assertNotNull(culturalOfferRestore);
    }

    //Updating non existing cultural offer.
    @Test
    public void testUpdateCulturalOfferInvalidId() throws ParseException {
    	login();
        ArrayList<String> pictures = new ArrayList<>();
        pictures.add(UPDATE_ONE_CULTURAL_OFFER_PICTURE1);
        pictures.add(UPDATE_ONE_CULTURAL_OFFER_PICTURE2);

        CulturalOfferDTO culturalOffer = new CulturalOfferDTO(UPDATE_ONE_CULTURAL_OFFER_ID_FAIL,UPDATE_ONE_CULTURAL_OFFER_NAME,UPDATE_ONE_CULTURAL_OFFER_DESCRIPTION,
                new SimpleDateFormat("yyyy-MM-dd").parse(UPDATE_ONE_CULTURAL_OFFER_STARTDATE),new SimpleDateFormat("yyyy-MM-dd").parse(UPDATE_ONE_CULTURAL_OFFER_ENDDATE),
                UPDATE_ONE_CULTURAL_OFFER_LOCATION,UPDATE_ONE_CULTURAL_OFFER_SUBCATEGORY, pictures );

        HttpEntity<Object> httpEntity = new HttpEntity<>(culturalOffer,headers);

        ResponseEntity<CulturalOfferDTO> responseEntity =
                restTemplate.exchange("/cultural-offers", HttpMethod.PUT, httpEntity,
                        CulturalOfferDTO.class);

        CulturalOfferDTO culturalOfferAdded = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(culturalOfferAdded);
    }

    //Updating cultural offer with already existing name of cultural offer.
    @Test
    public void testUpdateCulturalOfferInvalidName() throws ParseException {
    	login();
        ArrayList<String> pictures = new ArrayList<>();
        pictures.add(UPDATE_ONE_CULTURAL_OFFER_PICTURE1);
        pictures.add(UPDATE_ONE_CULTURAL_OFFER_PICTURE2);

        CulturalOfferDTO culturalOffer = new CulturalOfferDTO(UPDATE_ONE_CULTURAL_OFFER_ID,UPDATE_ONE_CULTURAL_OFFER_NAME_FAIL,UPDATE_ONE_CULTURAL_OFFER_DESCRIPTION,
                new SimpleDateFormat("yyyy-MM-dd").parse(UPDATE_ONE_CULTURAL_OFFER_STARTDATE),new SimpleDateFormat("yyyy-MM-dd").parse(UPDATE_ONE_CULTURAL_OFFER_ENDDATE),
                UPDATE_ONE_CULTURAL_OFFER_LOCATION,UPDATE_ONE_CULTURAL_OFFER_SUBCATEGORY, pictures );

        HttpEntity<Object> httpEntity = new HttpEntity<>(culturalOffer,headers);

        ResponseEntity<CulturalOfferDTO> responseEntity =
                restTemplate.exchange("/cultural-offers", HttpMethod.PUT, httpEntity,
                        CulturalOfferDTO.class);

        CulturalOfferDTO culturalOfferAdded = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(culturalOfferAdded);
    }

    //Updating cultural offer with not existing location.
    @Test
    public void testUpdateCulturalOfferInvalidLocation() throws ParseException {
    	login();
        ArrayList<String> pictures = new ArrayList<>();
        pictures.add(UPDATE_ONE_CULTURAL_OFFER_PICTURE1);
        pictures.add(UPDATE_ONE_CULTURAL_OFFER_PICTURE2);

        CulturalOfferDTO culturalOffer = new CulturalOfferDTO(UPDATE_ONE_CULTURAL_OFFER_ID,UPDATE_ONE_CULTURAL_OFFER_NAME,UPDATE_ONE_CULTURAL_OFFER_DESCRIPTION,
                new SimpleDateFormat("yyyy-MM-dd").parse(UPDATE_ONE_CULTURAL_OFFER_STARTDATE),new SimpleDateFormat("yyyy-MM-dd").parse(UPDATE_ONE_CULTURAL_OFFER_ENDDATE),
                SAVE_ONE_CULTURAL_OFFER_LOCATION_FAIL,UPDATE_ONE_CULTURAL_OFFER_SUBCATEGORY, pictures );

        HttpEntity<Object> httpEntity = new HttpEntity<>(culturalOffer,headers);

        ResponseEntity<CulturalOfferDTO> responseEntity =
                restTemplate.exchange("/cultural-offers", HttpMethod.PUT, httpEntity,
                        CulturalOfferDTO.class);

        CulturalOfferDTO culturalOfferAdded = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(culturalOfferAdded);
    }

    //Updating cultural offer with not existing location.
    @Test
    public void testUpdateCulturalOfferInvalidSubcategory() throws ParseException {
    	login();
        ArrayList<String> pictures = new ArrayList<>();
        pictures.add(UPDATE_ONE_CULTURAL_OFFER_PICTURE1);
        pictures.add(UPDATE_ONE_CULTURAL_OFFER_PICTURE2);

        CulturalOfferDTO culturalOffer = new CulturalOfferDTO(UPDATE_ONE_CULTURAL_OFFER_ID,UPDATE_ONE_CULTURAL_OFFER_NAME,UPDATE_ONE_CULTURAL_OFFER_DESCRIPTION,
                new SimpleDateFormat("yyyy-MM-dd").parse(UPDATE_ONE_CULTURAL_OFFER_STARTDATE),new SimpleDateFormat("yyyy-MM-dd").parse(UPDATE_ONE_CULTURAL_OFFER_ENDDATE),
                UPDATE_ONE_CULTURAL_OFFER_LOCATION,SAVE_ONE_CULTURAL_OFFER_SUBCATEGORY_FAIL, pictures );

        HttpEntity<Object> httpEntity = new HttpEntity<>(culturalOffer,headers);

        ResponseEntity<CulturalOfferDTO> responseEntity =
                restTemplate.exchange("/cultural-offers", HttpMethod.PUT, httpEntity,
                        CulturalOfferDTO.class);

        CulturalOfferDTO culturalOfferAdded = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(culturalOfferAdded);
    }

    //Delete cultural offer with given id.
    @Test
    @Sql(scripts = "classpath:insert-cultural-offer-data-h2.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testDeleteCulturalOfferValid(){
    	login();
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/cultural-offers/" + DELETE_CULTURAL_OFFER_ID, HttpMethod.DELETE, httpEntity,
                        String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    //Delete non existing cultural offer.
    @Test
    public void testDeleteCulturalOfferInvalid(){
    	login();
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/cultural-offers/" + DELETE_CULTURAL_OFFER_ID_FAIL, HttpMethod.DELETE, httpEntity,
                        String.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    //Get cultural offer with given id
    @Test
    public void testGetCulturalOfferWithIDValid(){
    	login();
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<CulturalOfferDTO> responseEntity =
                restTemplate.exchange("/cultural-offers/" + FIND_ONE_CULTURAL_OFFER_EXISTS, HttpMethod.GET, httpEntity,
                        CulturalOfferDTO.class);

        CulturalOfferDTO culturalOffer = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(culturalOffer);
    }

    //Get non existing id cultural offer.
    @Test
    public void testGetCulturalOfferWithIDInvalid(){
    	login();
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<CulturalOfferDTO> responseEntity =
                restTemplate.exchange("/cultural-offers/" + FIND_ONE_CULTURAL_OFFER_NOT_EXISTS, HttpMethod.GET, httpEntity,
                        CulturalOfferDTO.class);

        CulturalOfferDTO culturalOffer = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(culturalOffer);
    }

    //Get all cultural offers paged.
    @Test
    public void testGetAllCulturalOffersPagedValid() throws JsonProcessingException {
    	login();
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ObjectMapper objectMapper = new ObjectMapper();

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/cultural-offers/by-page?page=0&size=" + FIND_ALL_CULTURAL_OFFERS_COUNT_PAGED, HttpMethod.GET, httpEntity,
                        String.class);

        Page<CulturalOfferDTO> culturalOffers = objectMapper.readValue(responseEntity.getBody(), new TypeReference<RestResponsePage<CulturalOfferDTO>>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(FIND_ALL_CULTURAL_OFFERS_COUNT_PAGED, culturalOffers.getNumberOfElements());
    }

    //Filter cultural offers by existing category.
    @Test
    public void testFilterCulturalByCategoryValid() throws JsonProcessingException {
    	login();
        FilterDTO filter = new FilterDTO(FILTER_CATEGORY_VALUE_FOUND,FILTER_CATEGORY);
        HttpEntity<Object> httpEntity = new HttpEntity<>(filter,headers);

        ObjectMapper objectMapper = new ObjectMapper();

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/cultural-offers/filter", HttpMethod.POST, httpEntity,
                        String.class);

        Page<CulturalOfferDTO> culturalOffers = objectMapper.readValue(responseEntity.getBody(), new TypeReference<RestResponsePage<CulturalOfferDTO>>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(FILTER_CATEGORY_VALUE_SUCCESS_RESULT, culturalOffers.getNumberOfElements());
    }

    //Filter cultural offers by not existing category.
    @Test
    public void testFilterCulturalByCategoryInvalid() throws JsonProcessingException {
    	login();
        FilterDTO filter = new FilterDTO(FILTER_CATEGORY_VALUE_NOT_FOUND,FILTER_CATEGORY);

        HttpEntity<Object> httpEntity = new HttpEntity<>(filter,headers);
        ObjectMapper objectMapper = new ObjectMapper();

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/cultural-offers/filter", HttpMethod.POST, httpEntity,
                        String.class);

        Page<CulturalOfferDTO> culturalOffers = objectMapper.readValue(responseEntity.getBody(), new TypeReference<RestResponsePage<CulturalOfferDTO>>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(0, culturalOffers.getNumberOfElements());
    }

    //Filter cultural offers by existing subcategory.
    @Test
    public void testFilterCulturalBySubcategoryValid() throws JsonProcessingException {
    	login();
        FilterDTO filter = new FilterDTO(FILTER_SUBCATEGORY_VALUE_FOUND,FILTER_SUBCATEGORY);
        HttpEntity<Object> httpEntity = new HttpEntity<>(filter,headers);

        ObjectMapper objectMapper = new ObjectMapper();

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/cultural-offers/filter", HttpMethod.POST, httpEntity,
                        String.class);

        Page<CulturalOfferDTO> culturalOffers = objectMapper.readValue(responseEntity.getBody(), new TypeReference<RestResponsePage<CulturalOfferDTO>>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(FILTER_SUBCATEGORY_VALUE_SUCCESS_RESULT, culturalOffers.getNumberOfElements());
    }

    //Filter cultural offers by not existing subcategory.
    @Test
    public void testFilterCulturalBySubcategoryInvalid() throws JsonProcessingException {
    	login();
        FilterDTO filter = new FilterDTO(FILTER_SUBCATEGORY_VALUE_SUCCESS_ZERO,FILTER_CATEGORY);

        HttpEntity<Object> httpEntity = new HttpEntity<>(filter,headers);
        ObjectMapper objectMapper = new ObjectMapper();

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/cultural-offers/filter", HttpMethod.POST, httpEntity,
                        String.class);

        Page<CulturalOfferDTO> culturalOffers = objectMapper.readValue(responseEntity.getBody(), new TypeReference<RestResponsePage<CulturalOfferDTO>>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(0, culturalOffers.getNumberOfElements());
    }

    //Filter cultural offers by existing name.
    @Test
    public void testFilterCulturalByNameValid() throws JsonProcessingException {
    	login();
        FilterDTO filter = new FilterDTO(FILTER_NAME_VALUE_FOUND,FILTER_NAME);
        HttpEntity<Object> httpEntity = new HttpEntity<>(filter,headers);

        ObjectMapper objectMapper = new ObjectMapper();

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/cultural-offers/filter", HttpMethod.POST, httpEntity,
                        String.class);

        Page<CulturalOfferDTO> culturalOffers = objectMapper.readValue(responseEntity.getBody(), new TypeReference<RestResponsePage<CulturalOfferDTO>>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(FILTER_NAME_VALUE_FOUND_NUMBER, culturalOffers.getNumberOfElements());
    }

    //Filter cultural offers by not existing name.
    @Test
    public void testFilterCulturalByNameInvalid() throws JsonProcessingException {
    	login();
        FilterDTO filter = new FilterDTO(FILTER_NAME_VALUE_NOT_FOUND,FILTER_NAME);

        HttpEntity<Object> httpEntity = new HttpEntity<>(filter,headers);
        ObjectMapper objectMapper = new ObjectMapper();

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/cultural-offers/filter", HttpMethod.POST, httpEntity,
                        String.class);

        Page<CulturalOfferDTO> culturalOffers = objectMapper.readValue(responseEntity.getBody(), new TypeReference<RestResponsePage<CulturalOfferDTO>>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(0, culturalOffers.getNumberOfElements());
    }

    //Filter cultural offers by existing location.
    @Test
    public void testFilterCulturalByLocationValid() throws JsonProcessingException {
    	login();
        FilterDTO filter = new FilterDTO(FILTER_LOCATION_VALUE_FOUND,FILTER_LOCATION);
        HttpEntity<Object> httpEntity = new HttpEntity<>(filter,headers);

        ObjectMapper objectMapper = new ObjectMapper();

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/cultural-offers/filter", HttpMethod.POST, httpEntity,
                        String.class);

        Page<CulturalOfferDTO> culturalOffers = objectMapper.readValue(responseEntity.getBody(), new TypeReference<RestResponsePage<CulturalOfferDTO>>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(FILTER_LOCATION_VALUE_FOUND_NUMBER, culturalOffers.getNumberOfElements());
    }

    //Filter cultural offers by not existing location.
    @Test
    public void testFilterCulturalByLocationInvalid() throws JsonProcessingException {
    	login();
        FilterDTO filter = new FilterDTO(FILTER_LOCATION_VALUE_NOT_FOUND,FILTER_LOCATION);

        HttpEntity<Object> httpEntity = new HttpEntity<>(filter,headers);
        ObjectMapper objectMapper = new ObjectMapper();

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/cultural-offers/filter", HttpMethod.POST, httpEntity,
                        String.class);

        Page<CulturalOfferDTO> culturalOffers = objectMapper.readValue(responseEntity.getBody(), new TypeReference<RestResponsePage<CulturalOfferDTO>>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(0, culturalOffers.getNumberOfElements());
    }

    //Filter cultural offers with empty value.
    @Test
    public void testFilterCulturalByValueInvalid() throws JsonProcessingException {
    	login();
        FilterDTO filter = new FilterDTO(FILTER_INVALID_VALUE_EMPTY,FILTER_LOCATION);

        HttpEntity<Object> httpEntity = new HttpEntity<>(filter,headers);
        ObjectMapper objectMapper = new ObjectMapper();

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/cultural-offers/filter", HttpMethod.POST, httpEntity,
                        String.class);

        Page<CulturalOfferDTO> culturalOffers = objectMapper.readValue(responseEntity.getBody(), new TypeReference<RestResponsePage<CulturalOfferDTO>>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(FILTER_EMPTY_FOUND_NUMBER, culturalOffers.getNumberOfElements());
    }

    //Filter with invalid filter.
    @Test
    public void testFilterCulturalByFilterInvalid() throws JsonProcessingException {
    	login();
        FilterDTO filter = new FilterDTO(FILTER_NAME_VALUE_FOUND,FILTER_INVALID);

        HttpEntity<Object> httpEntity = new HttpEntity<>(filter,headers);
        ObjectMapper objectMapper = new ObjectMapper();

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/cultural-offers/filter", HttpMethod.POST, httpEntity,
                        String.class);

        Page<CulturalOfferDTO> culturalOffers = objectMapper.readValue(responseEntity.getBody(), new TypeReference<RestResponsePage<CulturalOfferDTO>>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(FILTER_NAME_VALUE_FOUND_NUMBER, culturalOffers.getNumberOfElements());
    }




    public void loginRegSubscribe() {
    	ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.postForEntity("/auth/log-in",
                new UserLoginDTO(REGISTERED_ID_NOT_SUBSCRIBED_NAME, DB_PASSWORD_RAW), UserTokenStateDTO.class);

        String accessToken = "Bearer " + responseEntity.getBody().getAccessToken();

        headers = new HttpHeaders();
        headers.add("Authorization", accessToken);

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Content-Type", "application/json");
    }
    
    public void loginRegUnsubscribe() {
    	ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.postForEntity("/auth/log-in",
                new UserLoginDTO(REGISTERED_ID_ALREADY_SUBSCRIBED_NAME, DB_PASSWORD_RAW), UserTokenStateDTO.class);

        String accessToken = "Bearer " + responseEntity.getBody().getAccessToken();

        headers = new HttpHeaders();
        headers.add("Authorization", accessToken);

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Content-Type", "application/json");
    }
    
    //Subscribe success
    @Test
    @Transactional
    public void testSubscribeSuccess() throws JsonProcessingException {
    	loginRegSubscribe();
        Subscribe data = new Subscribe();
        data.idOffer = CULTURAL_OFFER_ID;
        data.idUser = REGISTERED_ID_NOT_SUBSCRIBED;

        HttpEntity<Object> httpEntity = new HttpEntity<>(data,headers);

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/cultural-offers/subscribe", HttpMethod.POST, httpEntity,
                        String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        culturalOfferService.unsubscribe(CULTURAL_OFFER_ID, REGISTERED_ID_NOT_SUBSCRIBED);
    }
    
    //Subscribe fail
    @Test
    @Transactional
    public void testSubscribeFail() throws JsonProcessingException {
    	loginRegUnsubscribe();
        Subscribe data = new Subscribe();
        data.idOffer = CULTURAL_OFFER_ID;
        data.idUser = REGISTERED_ID_NOT_SUBSCRIBED;

        HttpEntity<Object> httpEntity = new HttpEntity<>(data,headers);

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/cultural-offers/subscribe", HttpMethod.POST, httpEntity,
                        String.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    //Unsubscribe success
    @Test
    @Transactional
    public void testUnsubscribeSuccess() throws JsonProcessingException {
    	loginRegUnsubscribe();
        Subscribe data = new Subscribe();
        data.idOffer = CULTURAL_OFFER_ID;
        data.idUser = REGISTERED_ID_ALREADY_SUBSCRIBED;

        HttpEntity<Object> httpEntity = new HttpEntity<>(data,headers);

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/cultural-offers/unsubscribe", HttpMethod.POST, httpEntity,
                        String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        culturalOfferService.subscribe(CULTURAL_OFFER_ID, REGISTERED_ID_ALREADY_SUBSCRIBED);
    }

    //Unsubscribe fail
    @Test
    @Transactional
    public void testUnsubscribeFail() throws JsonProcessingException {
    	loginRegUnsubscribe();
        Subscribe data = new Subscribe();
        data.idOffer = CULTURAL_OFFER_ID;
        data.idUser = REGISTERED_ID_NOT_SUBSCRIBED;

        HttpEntity<Object> httpEntity = new HttpEntity<>(data,headers);

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/cultural-offers/unsubscribe", HttpMethod.POST, httpEntity,
                        String.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
    
    @Test
    public void testGetCulturalOfferBySubcategory() throws JsonProcessingException {
    	login();
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ObjectMapper objectMapper = new ObjectMapper();

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/cultural-offers/subcategory/1/by-page?page=0&size=2", HttpMethod.GET, httpEntity,
                        String.class);

        Page<CulturalOfferDTO> culturalOffers = objectMapper.readValue(responseEntity.getBody(), new TypeReference<RestResponsePage<CulturalOfferDTO>>() {});
         
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(EXPECTED_OFFERS_BY_SUBCATEGORY, culturalOffers.getNumberOfElements());
    }

}
