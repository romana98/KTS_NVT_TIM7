package com.project.tim7.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.tim7.dto.CategoryDTO;
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
                new UserLoginDTO(DB_USERNAME, DB_PASSWORD_RAW), UserTokenStateDTO.class);

        String accessToken = "Bearer " + responseEntity.getBody().getAccessToken();

        headers = new HttpHeaders();
        headers.add("Authorization", accessToken);

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Content-Type", "application/json");
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
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, categories.size());

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
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, categories.getTotalElements());
    }

    //updateCategory
    @Test
    public void testUpdateCategoryValid(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(new CategoryDTO(VALID_CATEGORY_ID, NEW_VALID_CATEGORY_NAME), headers);
        ResponseEntity<CategoryDTO> responseEntity =
                restTemplate.exchange("/categories", HttpMethod.PUT, httpEntity, CategoryDTO.class);
        CategoryDTO updatedCategory = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(NEW_VALID_CATEGORY_NAME, updatedCategory.getName());

        Category category_restore = new Category(VALID_CATEGORY_ID, OLD_CATEGORY_NAME);
        categoryService.update(category_restore);
    }

    @Test
    public void testUpdateCategoryValidIdInvalidName(){

        HttpEntity<Object> httpEntity = new HttpEntity<>(new CategoryDTO(VALID_CATEGORY_ID, NEW_INVALID_CATEGORY_NAME), headers);

        ResponseEntity<CategoryDTO> responseEntity =
                restTemplate.exchange("/categories", HttpMethod.PUT, httpEntity, CategoryDTO.class);

        CategoryDTO updatedCategory = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(updatedCategory);
    }

    @Test
    public void testUpdateCategoryInvalidIdValidName(){

        HttpEntity<Object> httpEntity = new HttpEntity<>(new CategoryDTO(INVALID_CATEGORY_ID, NEW_VALID_CATEGORY_NAME), headers);

        ResponseEntity<CategoryDTO> responseEntity =
                restTemplate.exchange("/categories", HttpMethod.PUT, httpEntity, CategoryDTO.class);

        CategoryDTO updatedCategory = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(updatedCategory);

    }

    @Test
    public void testUpdateCategoryInvalidIdInvalidName(){

        HttpEntity<Object> httpEntity = new HttpEntity<>(new CategoryDTO(INVALID_CATEGORY_ID, NEW_INVALID_CATEGORY_NAME), headers);

        ResponseEntity<CategoryDTO> responseEntity =
                restTemplate.exchange("/categories", HttpMethod.PUT, httpEntity, CategoryDTO.class);

        CategoryDTO updatedCategory = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(updatedCategory);

    }

    @Test
    public void testCreateCategoryValid(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(new CategoryDTO(NEW_VALID_CATEGORY_NAME), headers);

        ResponseEntity<CategoryDTO> responseEntity =
                restTemplate.exchange("/categories", HttpMethod.POST, httpEntity, CategoryDTO.class);
        CategoryDTO created = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(created.getName(), NEW_VALID_CATEGORY_NAME);

        boolean isDeleted = categoryService.delete(created.getId());
        assertTrue(isDeleted);
    }

    @Test
    public void testCreateCategoryInvalid(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(new CategoryDTO(NEW_INVALID_CATEGORY_NAME), headers);

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
        assertThat(message).isNotBlank();

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

}
