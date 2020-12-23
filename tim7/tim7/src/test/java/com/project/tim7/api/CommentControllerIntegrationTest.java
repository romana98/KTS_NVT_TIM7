package com.project.tim7.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.tim7.dto.CommentDTO;
import com.project.tim7.dto.SubcategoryDTO;
import com.project.tim7.dto.UserLoginDTO;
import com.project.tim7.dto.UserTokenStateDTO;
import com.project.tim7.helper.RestResponsePage;
import com.project.tim7.service.CommentService;
import com.project.tim7.service.RegisteredService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static com.project.tim7.constants.CommentConstants.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CommentControllerIntegrationTest {

    @Autowired
    CommentService commentService;

    @Autowired
    RegisteredService registeredService;

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers;

    // JWT token za pristup REST servisima. Bice dobijen pri logovanju

    public void login() {
        ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.postForEntity("/auth/log-in",
                new UserLoginDTO(CONTROLLER_DB_USERNAME, CONTROLLER_DB_PASSWORD), UserTokenStateDTO.class);

        String accessToken = "Bearer " + responseEntity.getBody().getAccessToken();

        headers = new HttpHeaders();
        headers.add("Authorization", accessToken);

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Content-Type", "application/json");
    }

    @Test
    public void testCreateNewComment() throws ParseException {
        login();
        CommentDTO dto = new CommentDTO();
        dto.setCulturalOfferId(CONTROLLER_NEW_COMMENT_CULTURAL_OFFER);
        dto.setDescription(CONTROLLER_NEW_COMMENT_DESCRIPTION);
        dto.setPublishedDate(new SimpleDateFormat("yyyy-MM-dd").parse(CONTROLLER_NEW_COMMENT_DATE));
        dto.setPictures(new ArrayList<String>());
        dto.getPicturesId().add(CONTROLLER_NEW_COMMENT_PICTURE);
        dto.setRegisteredId(CONTROLLER_NEW_COMMENT_REGISTERED);
        HttpEntity<Object> httpEntity = new HttpEntity<>(dto, headers);

        ResponseEntity<CommentDTO> responseEntity =
                restTemplate.exchange("/comments", HttpMethod.POST, httpEntity, CommentDTO.class);
        CommentDTO created = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(created);

        boolean isDeleted = commentService.delete(created.getId());
        assertTrue(isDeleted);
    }

    @Test
    public void testCreateNewCommentPreconditionFailed() throws ParseException {
        login();
        CommentDTO dto = new CommentDTO();
        dto.setCulturalOfferId(CONTROLLER_NEW_COMMENT_CULTURAL_OFFER);
        dto.setDescription(CONTROLLER_NEW_COMMENT_DESCRIPTION);
        dto.setPublishedDate(new SimpleDateFormat("yyyy-MM-dd").parse(CONTROLLER_NEW_COMMENT_DATE));
        dto.setPictures(new ArrayList<String>());
        dto.getPicturesId().add(CONTROLLER_NEW_COMMENT_PICTURE);
        dto.setRegisteredId(CONTROLLER_NEW_COMMENT_REGISTERED_NOT_LOGGED_IN);
        HttpEntity<Object> httpEntity = new HttpEntity<>(dto, headers);

        ResponseEntity<CommentDTO> responseEntity =
                restTemplate.exchange("/comments", HttpMethod.POST, httpEntity, CommentDTO.class);
        CommentDTO created = responseEntity.getBody();
        assertEquals(HttpStatus.PRECONDITION_FAILED, responseEntity.getStatusCode());
        assertNull(created);
    }

    @Test
    public void testCreateNewCommentDuplicatePictures() throws ParseException {
        login();
        CommentDTO dto = new CommentDTO();
        dto.setCulturalOfferId(CONTROLLER_NEW_COMMENT_CULTURAL_OFFER);
        dto.setDescription(CONTROLLER_NEW_COMMENT_DESCRIPTION);
        dto.setPublishedDate(new SimpleDateFormat("yyyy-MM-dd").parse(CONTROLLER_NEW_COMMENT_DATE));
        dto.setPictures(new ArrayList<String>());
        dto.getPicturesId().add(CONTROLLER_NEW_COMMENT_PICTURE);
        dto.getPicturesId().add(CONTROLLER_NEW_COMMENT_PICTURE);
        dto.setRegisteredId(CONTROLLER_NEW_COMMENT_REGISTERED);
        HttpEntity<Object> httpEntity = new HttpEntity<>(dto, headers);

        ResponseEntity<CommentDTO> responseEntity =
                restTemplate.exchange("/comments", HttpMethod.POST, httpEntity, CommentDTO.class);
        CommentDTO created = responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(created);
    }

    @Test
    public void testCreateNewCommentInvalidCulturalOffer() throws ParseException {
        login();
        CommentDTO dto = new CommentDTO();
        dto.setCulturalOfferId(CONTROLLER_NEW_COMMENT_CULTURAL_OFFER_INVALID);
        dto.setDescription(CONTROLLER_NEW_COMMENT_DESCRIPTION);
        dto.setPublishedDate(new SimpleDateFormat("yyyy-MM-dd").parse(CONTROLLER_NEW_COMMENT_DATE));
        dto.setPictures(new ArrayList<String>());
        dto.getPicturesId().add(CONTROLLER_NEW_COMMENT_PICTURE);
        dto.setRegisteredId(CONTROLLER_NEW_COMMENT_REGISTERED);
        HttpEntity<Object> httpEntity = new HttpEntity<>(dto, headers);

        ResponseEntity<CommentDTO> responseEntity =
                restTemplate.exchange("/comments", HttpMethod.POST, httpEntity, CommentDTO.class);
        CommentDTO created = responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(created);
    }

    @Test
    public void testGetCommentsByCulturalOffer() throws JsonProcessingException {
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ObjectMapper objectMapper = new ObjectMapper();

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/comments/1/by-page?page=0&size=7", HttpMethod.GET, httpEntity,
                        String.class);

        Page<CommentDTO> comments = objectMapper.readValue(responseEntity.getBody(), new TypeReference<RestResponsePage<CommentDTO>>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(CONTROLLER_COUNT_COMMENTS_BY_CULTURAL_OFFER, comments.getTotalElements());
    }

    @Test
    public void testGetCommentsByCulturalOfferEmpty() throws JsonProcessingException {
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ObjectMapper objectMapper = new ObjectMapper();

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/comments/2/by-page?page=0&size=7", HttpMethod.GET, httpEntity,
                        String.class);

        Page<CommentDTO> comments = objectMapper.readValue(responseEntity.getBody(), new TypeReference<RestResponsePage<CommentDTO>>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(0, comments.getTotalElements());
    }

}
