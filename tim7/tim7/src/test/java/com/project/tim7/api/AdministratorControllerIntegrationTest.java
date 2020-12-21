package com.project.tim7.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.tim7.dto.UserDTO;
import com.project.tim7.dto.UserLoginDTO;
import com.project.tim7.dto.UserTokenStateDTO;
import com.project.tim7.helper.RestResponsePage;
import com.project.tim7.model.Administrator;
import com.project.tim7.service.AdministratorService;
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


import static com.project.tim7.constants.AdministratorConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class AdministratorControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AdministratorService administratorService;

    private HttpHeaders headers;

    // JWT token za pristup REST servisima. Bice dobijen pri logovanju
    @Before
    public void login() {
        ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.postForEntity("/auth/log-in",
                new UserLoginDTO(DB_USERNAME,DB_PASSWORD_RAW), UserTokenStateDTO.class);

        String accessToken = "Bearer " + responseEntity.getBody().getAccessToken();

        headers = new HttpHeaders();
        headers.add("Authorization", accessToken);

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Content-Type", "application/json");
    }

    @Test
    public void testGetAllAdministrators() throws JsonProcessingException {
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ObjectMapper objectMapper = new ObjectMapper();

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/administrators/by-page?page=0&size=3", HttpMethod.GET, httpEntity,
                        String.class);

        Page<UserDTO> admins = objectMapper.readValue(responseEntity.getBody(), new TypeReference<RestResponsePage<UserDTO>>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, admins.getTotalElements());
    }

    @Test
    public void testGetAdministrator() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<UserDTO> responseEntity =
                restTemplate.exchange("/administrators/1", HttpMethod.GET, httpEntity,
                        UserDTO.class);

        UserDTO admin = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(admin);
    }

    @Test
    public void testGetAdministratorInvalid() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<UserDTO> responseEntity =
                restTemplate.exchange("/administrators/4", HttpMethod.GET, httpEntity,
                        UserDTO.class);

        UserDTO admin = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(admin);
    }

    @Test
    public void testGetAdministratorInvalidNonExist() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<UserDTO> responseEntity =
                restTemplate.exchange("/administrators/-1", HttpMethod.GET, httpEntity,
                        UserDTO.class);

        UserDTO admin = responseEntity.getBody();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(admin);
    }

    @Test
    public void testUpdateAdministrator(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(new UserDTO(ADMIN_ID, DB_USERNAME, NEW_EMAIL, NEW_PASSWORD), headers);

        ResponseEntity<UserDTO> responseEntity =
                restTemplate.exchange("/administrators", HttpMethod.PUT, httpEntity,
                        UserDTO.class);

        UserDTO admin = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(NEW_EMAIL, admin.getEmail());
        assertNull(admin.getPassword());

        //returning db to original state
        Administrator admin_restore = new Administrator(ADMIN_ID, DB_EMAIL, DB_USERNAME, DB_PASSWORD);
        administratorService.update(admin_restore);
    }

    @Test
    public void testUpdateAdministratorNewEmail(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(new UserDTO(ADMIN_ID, DB_USERNAME, NEW_EMAIL, DB_PASSWORD), headers);

        ResponseEntity<UserDTO> responseEntity =
                restTemplate.exchange("/administrators", HttpMethod.PUT, httpEntity,
                        UserDTO.class);

        UserDTO admin = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(NEW_EMAIL, admin.getEmail());
        assertNull(admin.getPassword());

        //returning db to original state
        Administrator admin_restore = new Administrator(ADMIN_ID, DB_EMAIL, DB_USERNAME, DB_PASSWORD);
        administratorService.update(admin_restore);
    }

    @Test
    public void testUpdateAdministratorNewPassword(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(new UserDTO(ADMIN_ID, DB_USERNAME, DB_EMAIL, NEW_PASSWORD_RAW), headers);

        ResponseEntity<UserDTO> responseEntity =
                restTemplate.exchange("/administrators", HttpMethod.PUT, httpEntity,
                        UserDTO.class);

        UserDTO admin = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(DB_EMAIL, admin.getEmail());
        assertNull(admin.getPassword());

        //returning db to original state
        Administrator admin_restore = new Administrator(ADMIN_ID, DB_EMAIL, DB_USERNAME, DB_PASSWORD);
        administratorService.update(admin_restore);
    }

    @Test
    public void testUpdateAdministratorExistAdminEmail(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(new UserDTO(ADMIN_ID, DB_USERNAME, NEW_EMAIL_EXIST, DB_PASSWORD), headers);

        ResponseEntity<UserDTO> responseEntity =
                restTemplate.exchange("/administrators", HttpMethod.PUT, httpEntity,
                        UserDTO.class);

        UserDTO admin = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(admin);
    }

    @Test
    public void testUpdateAdministratorExistRegEmail(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(new UserDTO(ADMIN_ID, DB_USERNAME, NEW_EMAIL_EXIST_REG, DB_PASSWORD), headers);

        ResponseEntity<UserDTO> responseEntity =
                restTemplate.exchange("/administrators", HttpMethod.PUT, httpEntity,
                        UserDTO.class);

        UserDTO admin = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(admin);
    }

    @Test
    public void testUpdateAdministratorInvalidUsername() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(new UserDTO(ADMIN_ID, DB_USERNAME_NONEXIST, DB_EMAIL, DB_PASSWORD), headers);

        ResponseEntity<UserDTO> responseEntity =
                restTemplate.exchange("/administrators", HttpMethod.PUT, httpEntity,
                        UserDTO.class);

        UserDTO admin = responseEntity.getBody();

        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertNull(admin);

    }

    @Test
    public void testUpdateAdministratorPassword() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(new UserDTO(ADMIN_ID, DB_USERNAME, DB_EMAIL, BAD_PASSWORD), headers);

        ResponseEntity<UserDTO> responseEntity =
                restTemplate.exchange("/administrators", HttpMethod.PUT, httpEntity,
                        UserDTO.class);

        UserDTO admin = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertThat(admin).hasAllNullFieldsOrPropertiesExcept("id","verified");
        assertThat(admin).hasFieldOrPropertyWithValue("id", 0);
        assertThat(admin).hasFieldOrPropertyWithValue("verified", false);

    }

    @Test
    public void testUpdateAdministratorInvalidEmail() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(new UserDTO(ADMIN_ID, DB_USERNAME, BAD_EMAIL, DB_PASSWORD), headers);

        ResponseEntity<UserDTO> responseEntity =
                restTemplate.exchange("/administrators", HttpMethod.PUT, httpEntity,
                        UserDTO.class);

        UserDTO admin = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertThat(admin).hasAllNullFieldsOrPropertiesExcept("id","verified");
        assertThat(admin).hasFieldOrPropertyWithValue("id", 0);
        assertThat(admin).hasFieldOrPropertyWithValue("verified", false);

    }

    @Test
    public void testUpdateAdministratorInvalidUsernameEmpty() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(new UserDTO(ADMIN_ID, "", DB_EMAIL, DB_PASSWORD), headers);

        ResponseEntity<UserDTO> responseEntity =
                restTemplate.exchange("/administrators", HttpMethod.PUT, httpEntity,
                        UserDTO.class);

        UserDTO admin = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertThat(admin).hasAllNullFieldsOrPropertiesExcept("id","verified");
        assertThat(admin).hasFieldOrPropertyWithValue("id", 0);
        assertThat(admin).hasFieldOrPropertyWithValue("verified", false);

    }

    @Test
    public void testUpdateAdministratorInvalidEmailEmpty() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(new UserDTO(ADMIN_ID, DB_USERNAME, "", DB_PASSWORD), headers);

        ResponseEntity<UserDTO> responseEntity =
                restTemplate.exchange("/administrators", HttpMethod.PUT, httpEntity,
                        UserDTO.class);

        UserDTO admin = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertThat(admin).hasAllNullFieldsOrPropertiesExcept("id","verified");
        assertThat(admin).hasFieldOrPropertyWithValue("id", 0);
        assertThat(admin).hasFieldOrPropertyWithValue("verified", false);

    }

    @Test
    public void testCreateAdministrator(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(new UserDTO(NEW_USERNAME, NEW_EMAIL, DB_PASSWORD), headers);

        ResponseEntity<UserDTO> responseEntity =
                restTemplate.exchange("/administrators", HttpMethod.POST, httpEntity,
                        UserDTO.class);

        UserDTO admin = responseEntity.getBody();

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(admin);

        //returning db to original state
        boolean isDeleted = administratorService.delete(admin.getId());
        assertTrue(isDeleted);
    }

    @Test
    public void testCreateAdministratorExistAdminEmail(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(new UserDTO(NEW_USERNAME, NEW_EMAIL_EXIST, DB_PASSWORD), headers);

        ResponseEntity<UserDTO> responseEntity =
                restTemplate.exchange("/administrators", HttpMethod.POST, httpEntity,
                        UserDTO.class);

        UserDTO admin = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(admin);
    }

    @Test
    public void testCreateAdministratorExistRegEmail(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(new UserDTO(NEW_USERNAME, NEW_EMAIL_EXIST_REG, DB_PASSWORD), headers);

        ResponseEntity<UserDTO> responseEntity =
                restTemplate.exchange("/administrators", HttpMethod.POST, httpEntity,
                        UserDTO.class);

        UserDTO admin = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(admin);
    }

    @Test
    public void testCreateAdministratorExistAdminUsername(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(new UserDTO(DB_USERNAME_EXIST, NEW_EMAIL, DB_PASSWORD), headers);

        ResponseEntity<UserDTO> responseEntity =
                restTemplate.exchange("/administrators", HttpMethod.POST, httpEntity,
                        UserDTO.class);

        UserDTO admin = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(admin);
    }

    @Test
    public void testCreateAdministratorExistRegUsername(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(new UserDTO("micoR", NEW_EMAIL, DB_PASSWORD), headers);

        ResponseEntity<UserDTO> responseEntity =
                restTemplate.exchange("/administrators", HttpMethod.POST, httpEntity,
                        UserDTO.class);

        UserDTO admin = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(admin);
    }

    @Test
    public void testCreateInvalidEmail() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(new UserDTO(NEW_USERNAME, BAD_EMAIL, DB_PASSWORD), headers);

        ResponseEntity<UserDTO> responseEntity =
                restTemplate.exchange("/administrators", HttpMethod.POST, httpEntity,
                        UserDTO.class);

        UserDTO admin = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertThat(admin).hasAllNullFieldsOrPropertiesExcept("id","verified");
        assertThat(admin).hasFieldOrPropertyWithValue("id", 0);
        assertThat(admin).hasFieldOrPropertyWithValue("verified", false);

    }

    @Test
    public void testCreateInvalidUsernameEmpty() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(new UserDTO("", DB_EMAIL, DB_PASSWORD), headers);

        ResponseEntity<UserDTO> responseEntity =
                restTemplate.exchange("/administrators", HttpMethod.POST, httpEntity,
                        UserDTO.class);

        UserDTO admin = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertThat(admin).hasAllNullFieldsOrPropertiesExcept("id","verified");
        assertThat(admin).hasFieldOrPropertyWithValue("id", 0);
        assertThat(admin).hasFieldOrPropertyWithValue("verified", false);

    }

    @Test
    public void testCreateInvalidEmailEmpty() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(new UserDTO(NEW_USERNAME, "", DB_PASSWORD), headers);

        ResponseEntity<UserDTO> responseEntity =
                restTemplate.exchange("/administrators", HttpMethod.POST, httpEntity,
                        UserDTO.class);

        UserDTO admin = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertThat(admin).hasAllNullFieldsOrPropertiesExcept("id","verified");
        assertThat(admin).hasFieldOrPropertyWithValue("id", 0);
        assertThat(admin).hasFieldOrPropertyWithValue("verified", false);

    }

    @Test
    public void testCreateInvalidPassword() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(new UserDTO(NEW_USERNAME, NEW_EMAIL, BAD_PASSWORD), headers);

        ResponseEntity<UserDTO> responseEntity =
                restTemplate.exchange("/administrators", HttpMethod.POST, httpEntity,
                        UserDTO.class);

        UserDTO admin = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertThat(admin).hasAllNullFieldsOrPropertiesExcept("id","verified");
        assertThat(admin).hasFieldOrPropertyWithValue("id", 0);
        assertThat(admin).hasFieldOrPropertyWithValue("verified", false);

    }

    @Test
    @Sql(scripts = "classpath:insert-data-h2.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testDeleteAdministrator(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/administrators/4", HttpMethod.DELETE, httpEntity,
                        String.class);

        String message = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertThat(message).isNotBlank();
    }

    @Test
    public void testDeleteAdministratorInvalid(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/administrators/-1", HttpMethod.DELETE, httpEntity,
                        String.class);

        String message = responseEntity.getBody();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertThat(message).isNotBlank();

    }

    @Test
    public void testDeleteAdministratorInvalidLoggedIn(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/administrators/1", HttpMethod.DELETE, httpEntity,
                        String.class);

        String message = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertThat(message).isNotBlank();

    }
}
