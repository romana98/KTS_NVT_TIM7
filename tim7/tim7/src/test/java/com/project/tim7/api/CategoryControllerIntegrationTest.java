package com.project.tim7.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.tim7.dto.CategoryDTO;
import com.project.tim7.dto.NewsletterDTO;
import com.project.tim7.dto.UserLoginDTO;
import com.project.tim7.dto.UserTokenStateDTO;
import com.project.tim7.helper.RestResponsePage;
import com.project.tim7.model.Category;
import com.project.tim7.service.CategoryService;
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
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.util.List;

import static com.project.tim7.constants.CategoryConstants.*;
import static com.project.tim7.constants.NewsletterConstants.FIND_ALL_NUMBER_OF_ITEMS;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CategoryControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CategoryService categoryService;

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
    public void testGetCategory(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<CategoryDTO> responseEntity =
                restTemplate.exchange("/categories/by-id/1", HttpMethod.GET, httpEntity, CategoryDTO.class);

        CategoryDTO found = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, found.getId());
    }

    @Test
    public void testGetCategoryNotFound(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<CategoryDTO> responseEntity =
                restTemplate.exchange("/categories/by-id/1000", HttpMethod.GET, httpEntity, CategoryDTO.class);

        CategoryDTO found = responseEntity.getBody();
        assertNull(found);
    }

    @Test
    public void testGetAllCategories() throws JsonProcessingException {
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ObjectMapper objectMapper = new ObjectMapper();

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/categories", HttpMethod.GET, httpEntity,
                        String.class);

        List<CategoryDTO> categories = objectMapper.readValue(responseEntity.getBody(), new TypeReference<List<CategoryDTO>>() {});
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(CONTROLLER_COUNT_CATEGORIES_ALL, categories.size());

    }

    @Test
    public void testGetAllCategoriesPageable() throws JsonProcessingException{
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ObjectMapper objectMapper = new ObjectMapper();

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/categories/by-page?page=0&size=7", HttpMethod.GET, httpEntity,
                        String.class);

        Page<CategoryDTO> categories = objectMapper.readValue(responseEntity.getBody(), new TypeReference<RestResponsePage<CategoryDTO>>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(CONTROLLER_COUNT_CATEGORIES_ALL, categories.getTotalElements());
    }

    @Test
    public void testUpdateCategoryValid(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(new CategoryDTO(CONTROLLER_VALID_CATEGORY_ID, CONTROLLER_NEW_VALID_CATEGORY_NAME), headers);
        ResponseEntity<CategoryDTO> responseEntity =
                restTemplate.exchange("/categories", HttpMethod.PUT, httpEntity, CategoryDTO.class);
        CategoryDTO updatedCategory = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(CONTROLLER_NEW_VALID_CATEGORY_NAME, updatedCategory.getName());

        Category category_restore = new Category(CONTROLLER_VALID_CATEGORY_ID, CONTROLLER_OLD_CATEGORY_NAME);
        categoryService.update(category_restore);
    }

    @Test
    public void testUpdateCategoryValidIdInvalidName(){

        HttpEntity<Object> httpEntity = new HttpEntity<>(new CategoryDTO(CONTROLLER_VALID_CATEGORY_ID, CONTROLLER_NEW_INVALID_CATEGORY_NAME), headers);

        ResponseEntity<CategoryDTO> responseEntity =
                restTemplate.exchange("/categories", HttpMethod.PUT, httpEntity, CategoryDTO.class);

        CategoryDTO updatedCategory = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(updatedCategory);
    }

    @Test
    public void testUpdateCategoryInvalidIdValidName(){

        HttpEntity<Object> httpEntity = new HttpEntity<>(new CategoryDTO(CONTROLLER_INVALID_CATEGORY_ID, CONTROLLER_NEW_VALID_CATEGORY_NAME), headers);

        ResponseEntity<CategoryDTO> responseEntity =
                restTemplate.exchange("/categories", HttpMethod.PUT, httpEntity, CategoryDTO.class);

        CategoryDTO updatedCategory = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(updatedCategory);

    }

    @Test
    public void testUpdateCategoryInvalidIdInvalidName(){

        HttpEntity<Object> httpEntity = new HttpEntity<>(new CategoryDTO(CONTROLLER_INVALID_CATEGORY_ID, CONTROLLER_NEW_INVALID_CATEGORY_NAME), headers);

        ResponseEntity<CategoryDTO> responseEntity =
                restTemplate.exchange("/categories", HttpMethod.PUT, httpEntity, CategoryDTO.class);

        CategoryDTO updatedCategory = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(updatedCategory);

    }

    @Test
    public void testCreateCategoryValid(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(new CategoryDTO(CONTROLLER_NEW_VALID_CATEGORY_NAME), headers);

        ResponseEntity<CategoryDTO> responseEntity =
                restTemplate.exchange("/categories", HttpMethod.POST, httpEntity, CategoryDTO.class);
        CategoryDTO created = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(CONTROLLER_NEW_VALID_CATEGORY_NAME, created.getName());

        boolean isDeleted = categoryService.delete(created.getId());
        assertTrue(isDeleted);
    }

    @Test
    public void testCreateCategoryInvalid(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(new CategoryDTO(CONTROLLER_NEW_INVALID_CATEGORY_NAME), headers);

        ResponseEntity<CategoryDTO> responseEntity =
                restTemplate.exchange("/categories", HttpMethod.POST, httpEntity, CategoryDTO.class);
        CategoryDTO created = responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(created);
    }

    @Test
    @Sql(scripts = "classpath:insert-category-h2.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testDeleteCategoryValid(){

        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/categories/2", HttpMethod.DELETE, httpEntity, String.class);

        String message = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }

    @Test
    public void testDeleteCategoryInvalid(){

        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/categories/100", HttpMethod.DELETE, httpEntity, String.class);

        String message = responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertThat(message).isNotBlank();

    }

    @Test
    public void testDeleteCategoryInvalidReferencing(){

        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/categories/1", HttpMethod.DELETE, httpEntity, String.class);

        String message = responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertThat(message).isNotBlank();

    }
    
    public void loginReg() {
    	ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.postForEntity("/auth/log-in",
                new UserLoginDTO(USERNAME_SUBSCRIBED, CONTROLLER_DB_PASSWORD), UserTokenStateDTO.class);

        String accessToken = "Bearer " + responseEntity.getBody().getAccessToken();

        headers = new HttpHeaders();
        headers.add("Authorization", accessToken);

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Content-Type", "application/json");
    }
    
    @Test
    public void testGetSubscribedCategories() throws JsonProcessingException {
    	loginReg();
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ObjectMapper objectMapper = new ObjectMapper();

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/categories/subscribed/3", HttpMethod.GET, httpEntity, String.class);

        List<CategoryDTO> categories = objectMapper.readValue(responseEntity.getBody(), new TypeReference<List<CategoryDTO>>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(SUBSCRIBED_CATEGORIES, categories.size());
    }

}
