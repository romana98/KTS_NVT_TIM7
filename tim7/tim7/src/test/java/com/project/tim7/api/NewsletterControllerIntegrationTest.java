package com.project.tim7.api;

import static com.project.tim7.constants.NewsletterConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.tim7.dto.NewsletterDTO;
import com.project.tim7.dto.NewsletterDetailsDTO;
import com.project.tim7.dto.UserLoginDTO;
import com.project.tim7.dto.UserTokenStateDTO;
import com.project.tim7.helper.RestResponsePage;
import com.project.tim7.model.Newsletter;
import com.project.tim7.service.NewsletterService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class NewsletterControllerIntegrationTest {
	
	@Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private NewsletterService newsletterService;

    private HttpHeaders headers;
    
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    // JWT token za pristup REST servisima. Bice dobijen pri logovanju

    public void loginAdmin() {
        ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.postForEntity("/auth/log-in",
                new UserLoginDTO(DB_USERNAME_ADMIN, DB_PASSWORD_RAW), UserTokenStateDTO.class);

        String accessToken = "Bearer " + responseEntity.getBody().getAccessToken();

        headers = new HttpHeaders();
        headers.add("Authorization", accessToken);

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Content-Type", "application/json");
    }
    
    public void loginReg() {
        ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.postForEntity("/auth/log-in",
                new UserLoginDTO(DB_USERNAME_REG, DB_PASSWORD_RAW), UserTokenStateDTO.class);

        String accessToken = "Bearer " + responseEntity.getBody().getAccessToken();

        headers = new HttpHeaders();
        headers.add("Authorization", accessToken);

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Content-Type", "application/json");
    }
    
    @Test
    public void testGetAllNewsletters() throws JsonProcessingException {
    	loginAdmin();
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ObjectMapper objectMapper = new ObjectMapper();

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/newsletter", HttpMethod.GET, httpEntity, String.class);

        List<NewsletterDTO> newsletters = objectMapper.readValue(responseEntity.getBody(), new TypeReference<List<NewsletterDTO>>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, newsletters.size());
    }
    
    @Test
    public void testGetAllNewslettersPageable() throws JsonProcessingException {
    	loginAdmin();
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ObjectMapper objectMapper = new ObjectMapper();

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/newsletter/by-page?page=0&size=3", HttpMethod.GET, httpEntity,
                        String.class);

        Page<NewsletterDTO> admins = objectMapper.readValue(responseEntity.getBody(), new TypeReference<RestResponsePage<NewsletterDTO>>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, admins.getTotalElements());
    }
    
    @Test
    public void testFindNewsletterForUser() throws JsonProcessingException {
    	loginReg();
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ObjectMapper objectMapper = new ObjectMapper();

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/newsletter/subscribed/3", HttpMethod.GET, httpEntity, String.class);

        List<NewsletterDetailsDTO> newsletters = objectMapper.readValue(responseEntity.getBody(), new TypeReference<List<NewsletterDetailsDTO>>() {});
        
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, newsletters.size());
    }
    
    @Test
    public void testFindNewsletterForUserInvalidUser() throws JsonProcessingException {
    	loginReg();
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ObjectMapper objectMapper = new ObjectMapper();

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/newsletter/subscribed/777", HttpMethod.GET, httpEntity, String.class);
        
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
    
    @Test
    public void testFindNewsletterForUserInvalidAuthority() throws JsonProcessingException {
    	loginAdmin();
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ObjectMapper objectMapper = new ObjectMapper();

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/newsletter/subscribed/1", HttpMethod.GET, httpEntity, String.class);
        
        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }
    
    @Test
    public void testFindNewsletterForUserPageable() throws JsonProcessingException {
    	loginReg();
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ObjectMapper objectMapper = new ObjectMapper();

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/newsletter/subscribed/3/by-page?page=0&size=3", HttpMethod.GET, httpEntity, String.class);

        Page<NewsletterDetailsDTO> newsletters = objectMapper.readValue(responseEntity.getBody(), new TypeReference<RestResponsePage<NewsletterDetailsDTO>>() {});
                
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, newsletters.getTotalElements());
    }
    
    @Test
    public void testFindNewsletterForUserPageableInvalidUser() throws JsonProcessingException {
    	loginReg();
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ObjectMapper objectMapper = new ObjectMapper();

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/newsletter/subscribed/777/by-page?page=0&size=3", HttpMethod.GET, httpEntity, String.class);
                
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
    
    @Test
    public void testFindNewsletterForUserPageableInvalidAuthority() throws JsonProcessingException {
    	loginAdmin();
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ObjectMapper objectMapper = new ObjectMapper();

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/newsletter/subscribed/1/by-page?page=0&size=3", HttpMethod.GET, httpEntity, String.class);
                
        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }
    
    @Test
    public void testFindNewsletterForCulturalOffer() throws JsonProcessingException {
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ObjectMapper objectMapper = new ObjectMapper();

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/newsletter/cultural-offer/1", HttpMethod.GET, httpEntity, String.class);

        List<NewsletterDTO> newsletters = objectMapper.readValue(responseEntity.getBody(), new TypeReference<List<NewsletterDTO>>() {});
        
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, newsletters.size());
    }
    
    @Test
    public void testFindNewsletterForCulturalOfferNoNewsletter() throws JsonProcessingException {
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ObjectMapper objectMapper = new ObjectMapper();

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/newsletter/cultural-offer/333", HttpMethod.GET, httpEntity, String.class);

        List<NewsletterDTO> newsletters = objectMapper.readValue(responseEntity.getBody(), new TypeReference<List<NewsletterDTO>>() {});
        
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(CULTURAL_OFFER_NO_NEWSLETTER, newsletters.size());
    }
    
    @Test
    public void testFindNewsletterForCulturalOfferPageable() throws JsonProcessingException {
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ObjectMapper objectMapper = new ObjectMapper();

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/newsletter/cultural-offer/1/by-page?page=0&size=3", HttpMethod.GET, httpEntity, String.class);

        Page<NewsletterDTO> newsletters = objectMapper.readValue(responseEntity.getBody(), new TypeReference<RestResponsePage<NewsletterDTO>>() {});
        
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, newsletters.getTotalElements());
    }
    
    @Test
    public void testGetNewsletterAsRegistered() throws JsonProcessingException {
    	loginReg();
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<NewsletterDetailsDTO> responseEntity =
                restTemplate.exchange("/newsletter/1", HttpMethod.GET, httpEntity, NewsletterDetailsDTO.class);

        NewsletterDetailsDTO newsletter = responseEntity.getBody();
        
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertThat(newsletter).hasFieldOrPropertyWithValue("id", 1);
    }
    
    @Test
    public void testGetNewsletterAsAdmin() throws JsonProcessingException {
    	loginAdmin();
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<NewsletterDetailsDTO> responseEntity =
                restTemplate.exchange("/newsletter/1", HttpMethod.GET, httpEntity, NewsletterDetailsDTO.class);

        NewsletterDetailsDTO newsletter = responseEntity.getBody();
        
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertThat(newsletter).hasFieldOrPropertyWithValue("id", 1);
    }
    
    @Test
    public void testGetNewsletterInvalid() throws JsonProcessingException {
    	loginReg();
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<NewsletterDetailsDTO> responseEntity =
                restTemplate.exchange("/newsletter/777", HttpMethod.GET, httpEntity, NewsletterDetailsDTO.class);

        NewsletterDetailsDTO newsletter = responseEntity.getBody();
        
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(newsletter);
    }
    
    @Test
    public void testCreateNewsletter() throws ParseException{
    	loginAdmin();
        HttpEntity<Object> httpEntity = new HttpEntity<>(new NewsletterDetailsDTO(NEW_ID, NEW_NAME, NEW_DESCRIPTION_C, sdf.parse(NEW_DATE), NEW_OFFER, NEW_PIC), headers);

        ResponseEntity<NewsletterDetailsDTO> responseEntity =
                restTemplate.exchange("/newsletter", HttpMethod.POST, httpEntity,
                		NewsletterDetailsDTO.class);

        NewsletterDetailsDTO newsletter = responseEntity.getBody();

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(NEW_NAME, newsletter.getName());

        //returning db to original state
        boolean isDeleted = newsletterService.delete(newsletter.getId());
        assertTrue(isDeleted);
    }
    
    @Test
    public void testCreateNewsletterNoName() throws ParseException{
    	loginAdmin();
        HttpEntity<Object> httpEntity = new HttpEntity<>(new NewsletterDetailsDTO(NEW_ID, BLANK, NEW_DESCRIPTION_C, sdf.parse(NEW_DATE), NEW_OFFER, NEW_PIC), headers);

        ResponseEntity<NewsletterDetailsDTO> responseEntity =
                restTemplate.exchange("/newsletter", HttpMethod.POST, httpEntity,
                		NewsletterDetailsDTO.class);

        NewsletterDetailsDTO newsletter = responseEntity.getBody();
        
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
    
    @Test
    public void testCreateNewsletterWrongOffer() throws ParseException{
    	loginAdmin();
        HttpEntity<Object> httpEntity = new HttpEntity<>(new NewsletterDetailsDTO(NEW_ID, NEW_NAME, NEW_DESCRIPTION_C, sdf.parse(NEW_DATE), OFFER_ID_NOT_EXIST, NEW_PIC), headers);

        ResponseEntity<NewsletterDetailsDTO> responseEntity =
                restTemplate.exchange("/newsletter", HttpMethod.POST, httpEntity,
                		NewsletterDetailsDTO.class);

        NewsletterDetailsDTO newsletter = responseEntity.getBody();
        
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
    
    @Test
    public void testCreateNewsletterWrongDate() throws ParseException{
    	loginAdmin();
        HttpEntity<Object> httpEntity = new HttpEntity<>(new NewsletterDetailsDTO(NEW_ID, NEW_NAME, NEW_DESCRIPTION_C, sdf.parse(WRONG_DATE), NEW_OFFER, NEW_PIC), headers);

        ResponseEntity<NewsletterDetailsDTO> responseEntity =
                restTemplate.exchange("/newsletter", HttpMethod.POST, httpEntity,
                		NewsletterDetailsDTO.class);

        NewsletterDetailsDTO newsletter = responseEntity.getBody();
        
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
    
    @Test
    public void testUpdateNewsletter() throws ParseException{
    	loginAdmin();
        HttpEntity<Object> httpEntity = new HttpEntity<>(new NewsletterDetailsDTO(UPDATE_ID, NEW_NAME, RESTORE_DESCRIPTION_UPDATE, sdf.parse(RESTORE_DATE_UPDATE), RESTORE_OFFER_UPDATE, RESTORE_PICTURE_UPDATE), headers);

        ResponseEntity<NewsletterDetailsDTO> responseEntity =
                restTemplate.exchange("/newsletter", HttpMethod.PUT, httpEntity,
                		NewsletterDetailsDTO.class);

        NewsletterDetailsDTO newsletter = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(NEW_NAME, newsletter.getName());

        //returning db to original state
        Newsletter updated = newsletterService.update(new Newsletter(UPDATE_ID, RESTORE_NAME_UPDATE, RESTORE_DESCRIPTION_UPDATE, sdf.parse(RESTORE_DATE_UPDATE)), RESTORE_PICTURE_UPDATE);
        assertEquals(RESTORE_NAME_UPDATE, updated.getName());
    }
    
    @Test
    public void testUpdateNewsletterWrongId() throws ParseException{
    	loginAdmin();
        HttpEntity<Object> httpEntity = new HttpEntity<>(new NewsletterDetailsDTO(NEWSLETTER_ID_NOT_EXIST, NEW_NAME, RESTORE_DESCRIPTION, sdf.parse(RESTORE_DATE), RESTORE_OFFER, RESTORE_PICTURE), headers);

        ResponseEntity<NewsletterDetailsDTO> responseEntity =
                restTemplate.exchange("/newsletter", HttpMethod.PUT, httpEntity,
                		NewsletterDetailsDTO.class);

        NewsletterDetailsDTO newsletter = responseEntity.getBody();
        
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
    
    @Test
    @Sql(scripts = "classpath:insert-newsletter-data-h2.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testDeleteNewsletter() throws ParseException{
    	loginAdmin();
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/newsletter/3", HttpMethod.DELETE, httpEntity,
                        String.class);

        String message = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertThat(message).isNotBlank();
    }
    
    @Test
    public void testDeleteNewsletterWrongId() throws ParseException{
    	loginAdmin();
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/newsletter/777", HttpMethod.DELETE, httpEntity,
                        String.class);

        String message = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertThat(message).isNotBlank();
    }

}
