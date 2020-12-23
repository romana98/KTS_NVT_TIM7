package com.project.tim7.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.tim7.dto.SubcategoryDTO;
import com.project.tim7.dto.UserLoginDTO;
import com.project.tim7.dto.UserTokenStateDTO;
import com.project.tim7.helper.RestResponsePage;
import com.project.tim7.model.Subcategory;
import com.project.tim7.service.SubcategoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.project.tim7.constants.SubcategoryConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;



@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class SubcategoryControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    SubcategoryService subcategoryService;

    private HttpHeaders headers;

    // JWT token za pristup REST servisima. Bice dobijen pri logovanju
    @Before
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
    public void testGetAllSubcategories() throws JsonProcessingException {
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ObjectMapper objectMapper = new ObjectMapper();

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/subcategories", HttpMethod.GET, httpEntity,
                        String.class);

        List<SubcategoryDTO> subcategories = objectMapper.readValue(responseEntity.getBody(), new TypeReference<List<SubcategoryDTO>>() {});
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(CONTROLLER_COUNT_SUBCATEGORIES_ALL, subcategories.size());
    }

    @Test
    public void testGetAllSubcategoriesPageable() throws JsonProcessingException {
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ObjectMapper objectMapper = new ObjectMapper();

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/subcategories/by-page?page=0&size=7", HttpMethod.GET, httpEntity,
                        String.class);

        Page<SubcategoryDTO> subcategories = objectMapper.readValue(responseEntity.getBody(), new TypeReference<RestResponsePage<SubcategoryDTO>>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(CONTROLLER_COUNT_SUBCATEGORIES_ALL, subcategories.getTotalElements());
    }

    @Test
    public void testUpdateValid(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(new SubcategoryDTO(CONTROLLER_VALID_SUBCATEGORY_ID, CONTROLLER_NEW_VALID_SUBCATEGORY_NAME, CONTROLLER_VALID_CATEGORY_ID), headers);
        ResponseEntity<SubcategoryDTO> responseEntity =
                restTemplate.exchange("/subcategories", HttpMethod.PUT, httpEntity, SubcategoryDTO.class);
        SubcategoryDTO updated = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(CONTROLLER_NEW_VALID_SUBCATEGORY_NAME, updated.getName());

        Subcategory subcategory_restore = new Subcategory(CONTROLLER_VALID_SUBCATEGORY_ID, CONTROLLER_OLD_SUBCATEGORY_NAME, CONTROLLER_VALID_CATEGORY_ID);
        subcategoryService.update(subcategory_restore);
    }

    @Test
    public void testUpdateInvalidId(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(new SubcategoryDTO(CONTROLLER_INVALID_SUBCATEGORY_ID, CONTROLLER_NEW_VALID_SUBCATEGORY_NAME, CONTROLLER_VALID_CATEGORY_ID), headers);
        ResponseEntity<SubcategoryDTO> responseEntity =
                restTemplate.exchange("/subcategories", HttpMethod.PUT, httpEntity, SubcategoryDTO.class);
        SubcategoryDTO updated = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(updated);
    }

    @Test
    public void testUpdateInvalidCategory(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(new SubcategoryDTO(CONTROLLER_VALID_SUBCATEGORY_ID, CONTROLLER_NEW_VALID_SUBCATEGORY_NAME, CONTROLLER_INVALID_CATEGORY_ID), headers);
        ResponseEntity<SubcategoryDTO> responseEntity =
                restTemplate.exchange("/subcategories", HttpMethod.PUT, httpEntity, SubcategoryDTO.class);
        SubcategoryDTO updated = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(updated);
    }

    @Test
    public void testUpdateInvalidName(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(new SubcategoryDTO(CONTROLLER_VALID_SUBCATEGORY_ID, CONTROLLER_NEW_INVALID_SUBCATEGORY_NAME, CONTROLLER_VALID_CATEGORY_ID), headers);
        ResponseEntity<SubcategoryDTO> responseEntity =
                restTemplate.exchange("/subcategories", HttpMethod.PUT, httpEntity, SubcategoryDTO.class);
        SubcategoryDTO updated = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(updated);
    }

    @Test
    public void testCreateSubcategoryValid(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(new SubcategoryDTO(CONTROLLER_NEW_VALID_SUBCATEGORY_NAME, CONTROLLER_VALID_CATEGORY_ID), headers);

        ResponseEntity<SubcategoryDTO> responseEntity =
                restTemplate.exchange("/subcategories", HttpMethod.POST, httpEntity, SubcategoryDTO.class);
        SubcategoryDTO created = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(CONTROLLER_NEW_VALID_SUBCATEGORY_NAME, created.getName());

        boolean isDeleted = subcategoryService.delete(created.getId());
        assertTrue(isDeleted);
    }

    @Test
    public void testCreateSubcategoryInvalidName(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(new SubcategoryDTO(CONTROLLER_NEW_INVALID_SUBCATEGORY_NAME, CONTROLLER_VALID_CATEGORY_ID), headers);

        ResponseEntity<SubcategoryDTO> responseEntity =
                restTemplate.exchange("/subcategories", HttpMethod.POST, httpEntity, SubcategoryDTO.class);
        SubcategoryDTO created = responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(created);
    }

    @Test
    public void testCreateSubcategoryInvalidCategory(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(new SubcategoryDTO(CONTROLLER_NEW_VALID_SUBCATEGORY_NAME, CONTROLLER_INVALID_CATEGORY_ID), headers);

        ResponseEntity<SubcategoryDTO> responseEntity =
                restTemplate.exchange("/subcategories", HttpMethod.POST, httpEntity, SubcategoryDTO.class);
        SubcategoryDTO created = responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(created);
    }

    @Test
    @Sql(scripts = "classpath:insert-subcategory-h2.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testDeleteValid(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/subcategories/2", HttpMethod.DELETE, httpEntity, String.class);

        String message = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertThat(message).isNotBlank();
    }

    @Test
    public void testDeleteInvalid(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/subcategories/100", HttpMethod.DELETE, httpEntity, String.class);

        String message = responseEntity.getBody();
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertThat(message).isNotBlank();
    }

    @Test
    public void testGetAllSubcategoriesByCategory() throws JsonProcessingException {
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ObjectMapper objectMapper = new ObjectMapper();

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/subcategories/3/by-page?page=0&size=7", HttpMethod.GET, httpEntity,
                        String.class);

        Page<SubcategoryDTO> subcategories = objectMapper.readValue(responseEntity.getBody(), new TypeReference<RestResponsePage<SubcategoryDTO>>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(CONTROLLER_COUNT_SUBCATEGORIES_BY_CATEGORY, subcategories.getTotalElements());
    }

    @Test
    public void testGetAllSubcategoriesByCategoryEmpty() throws JsonProcessingException {
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ObjectMapper objectMapper = new ObjectMapper();

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/subcategories/2/by-page?page=0&size=7", HttpMethod.GET, httpEntity,
                        String.class);

        Page<SubcategoryDTO> subcategories = objectMapper.readValue(responseEntity.getBody(), new TypeReference<RestResponsePage<SubcategoryDTO>>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(0, subcategories.getTotalElements());
    }

}
