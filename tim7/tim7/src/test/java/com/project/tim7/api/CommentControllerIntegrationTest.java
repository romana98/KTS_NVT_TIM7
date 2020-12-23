package com.project.tim7.api;

import com.project.tim7.dto.CommentDTO;
import com.project.tim7.dto.SubcategoryDTO;
import com.project.tim7.dto.UserLoginDTO;
import com.project.tim7.dto.UserTokenStateDTO;
import com.project.tim7.service.CommentService;
import com.project.tim7.service.RegisteredService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static com.project.tim7.constants.SubcategoryConstants.EXIST_CATEGORY_ID;
import static com.project.tim7.constants.SubcategoryConstants.NEW_VALID_SUBCATEGORY_NAME;
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
    @Before
    public void login() {
        ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.postForEntity("/auth/log-in",
                new UserLoginDTO(DB_USERNAME, DB_PASSWORD_RAW), UserTokenStateDTO.class);

        String accessToken = "Bearer " + responseEntity.getBody().getAccessToken();

        headers = new HttpHeaders();
        headers.add("Authorization", accessToken);

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Content-Type", "application/json");
    }

    @Test
    public void testCreateNewComment() throws ParseException {
        CommentDTO dto = new CommentDTO();
        dto.setCulturalOfferId(1);
        dto.setDescription(NEW_COMMENT_DESCRIPTION);
        dto.setPublishedDate(new SimpleDateFormat("yyyy-MM-dd").parse(DATE_STRING));
        dto.setPictures(new ArrayList<String>());
        dto.getPicturesId().add("http://dummyimage.com/600x600.bmp");
        dto.setRegisteredId(3);
        HttpEntity<Object> httpEntity = new HttpEntity<>(dto, headers);

        ResponseEntity<CommentDTO> responseEntity =
                restTemplate.exchange("/comments", HttpMethod.POST, httpEntity, CommentDTO.class);
        CommentDTO created = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(created);

        //boolean isDeleted = commentService.delete(created.getId());
        //assertTrue(isDeleted);
    }

}
