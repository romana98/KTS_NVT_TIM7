package com.project.tim7.api;

import com.project.tim7.dto.UserDTO;
import com.project.tim7.dto.UserLoginDTO;
import com.project.tim7.dto.UserTokenStateDTO;
import com.project.tim7.model.Administrator;
import com.project.tim7.model.Registered;
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

import static com.project.tim7.constants.RegisteredConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class RegisteredControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RegisteredService registeredService;

    private HttpHeaders headers;

    // JWT token za pristup REST servisima. Bice dobijen pri logovanju
    @Before
    public void login() {
        ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.postForEntity("/auth/log-in",
                new UserLoginDTO(DB_USERNAME_LOGIN, DB_PASSWORD_RAW), UserTokenStateDTO.class);

        String accessToken = "Bearer " + responseEntity.getBody().getAccessToken();

        headers = new HttpHeaders();
        headers.add("Authorization", accessToken);

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Content-Type", "application/json");
    }

    @Test
    public void testGetRegistered() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<UserDTO> responseEntity =
                restTemplate.exchange("/registered-users/6", HttpMethod.GET, httpEntity,
                        UserDTO.class);

        UserDTO reg = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(reg);
    }

    @Test
    public void testGetRegisteredInvalid() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<UserDTO> responseEntity =
                restTemplate.exchange("/registered-users/3", HttpMethod.GET, httpEntity,
                        UserDTO.class);

        UserDTO reg = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(reg);
    }

    @Test
    public void testGetRegisteredInvalidNonExist() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<UserDTO> responseEntity =
                restTemplate.exchange("/registered-users/-1", HttpMethod.GET, httpEntity,
                        UserDTO.class);

        UserDTO reg = responseEntity.getBody();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(reg);
    }

    @Test
    public void testUpdateRegistered() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(new UserDTO(REG_ID_LOGIN, DB_USERNAME_LOGIN, NEW_EMAIL, NEW_PASSWORD), headers);

        ResponseEntity<UserDTO> responseEntity =
                restTemplate.exchange("/registered-users", HttpMethod.PUT, httpEntity,
                        UserDTO.class);

        UserDTO reg = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(NEW_EMAIL, reg.getEmail());
        assertNull(reg.getPassword());

        //returning db to original state
        Registered reg_restore = new Registered(REG_ID_LOGIN, DB_EMAIL_LOGIN, DB_USERNAME_LOGIN, DB_PASSWORD);
        registeredService.update(reg_restore);
    }

    @Test
    public void testUpdateRegisteredNewEmail(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(new UserDTO(REG_ID_LOGIN, DB_USERNAME_LOGIN, NEW_EMAIL, DB_PASSWORD), headers);

        ResponseEntity<UserDTO> responseEntity =
                restTemplate.exchange("/registered-users", HttpMethod.PUT, httpEntity,
                        UserDTO.class);

        UserDTO reg = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(NEW_EMAIL, reg.getEmail());
        assertNull(reg.getPassword());

        //returning db to original state
        Registered reg_restore = new Registered(REG_ID_LOGIN, DB_EMAIL_LOGIN, DB_USERNAME_LOGIN, DB_PASSWORD);
        registeredService.update(reg_restore);
    }

    @Test
    public void testUpdateRegisteredNewPassword(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(new UserDTO(REG_ID_LOGIN, DB_USERNAME_LOGIN, DB_EMAIL_LOGIN, NEW_PASSWORD_RAW), headers);

        ResponseEntity<UserDTO> responseEntity =
                restTemplate.exchange("/registered-users", HttpMethod.PUT, httpEntity,
                        UserDTO.class);

        UserDTO reg = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(DB_EMAIL_LOGIN, reg.getEmail());
        assertNull(reg.getPassword());

        //returning db to original state
        Registered reg_restore = new Registered(REG_ID_LOGIN, DB_EMAIL_LOGIN, DB_USERNAME_LOGIN, DB_PASSWORD);
        registeredService.update(reg_restore);
    }

    @Test
    public void testUpdateRegisteredExistAdminEmail(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(new UserDTO(REG_ID_LOGIN, DB_USERNAME_LOGIN, NEW_EMAIL_EXIST_ADMIN, DB_PASSWORD), headers);

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/registered-users", HttpMethod.PUT, httpEntity,
                        String.class);

        String message = responseEntity.getBody();

        assertThat(message).isNotBlank();
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdateRegisteredExistRegEmail(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(new UserDTO(REG_ID_LOGIN, DB_USERNAME_LOGIN, NEW_EMAIL_EXIST, DB_PASSWORD), headers);

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/registered-users", HttpMethod.PUT, httpEntity,
                        String.class);

        String message = responseEntity.getBody();

        assertThat(message).isNotBlank();
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdateRegisteredInvalidUsername() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(new UserDTO(REG_ID_LOGIN, DB_USERNAME_NONEXIST, DB_EMAIL_LOGIN, DB_PASSWORD), headers);

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/registered-users", HttpMethod.PUT, httpEntity,
                        String.class);
        
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdateRegisteredInvalidPassword() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(new UserDTO(REG_ID_LOGIN, DB_USERNAME_LOGIN, DB_EMAIL_LOGIN, BAD_PASSWORD), headers);

        ResponseEntity<UserDTO> responseEntity =
                restTemplate.exchange("/registered-users", HttpMethod.PUT, httpEntity,
                        UserDTO.class);

        UserDTO reg = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertThat(reg).hasAllNullFieldsOrPropertiesExcept("id","verified");
        assertThat(reg).hasFieldOrPropertyWithValue("id", 0);
        assertThat(reg).hasFieldOrPropertyWithValue("verified", false);

    }

    @Test
    public void testUpdateRegisteredInvalidEmail() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(new UserDTO(REG_ID_LOGIN, DB_USERNAME_LOGIN, BAD_EMAIL, DB_PASSWORD), headers);

        ResponseEntity<UserDTO> responseEntity =
                restTemplate.exchange("/registered-users", HttpMethod.PUT, httpEntity,
                        UserDTO.class);

        UserDTO reg = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertThat(reg).hasAllNullFieldsOrPropertiesExcept("id","verified");
        assertThat(reg).hasFieldOrPropertyWithValue("id", 0);
        assertThat(reg).hasFieldOrPropertyWithValue("verified", false);

    }

    @Test
    public void testUpdateRegisteredInvalidUsernameEmpty() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(new UserDTO(REG_ID_LOGIN, "", DB_EMAIL_LOGIN, DB_PASSWORD), headers);

        ResponseEntity<UserDTO> responseEntity =
                restTemplate.exchange("/registered-users", HttpMethod.PUT, httpEntity,
                        UserDTO.class);

        UserDTO reg = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertThat(reg).hasAllNullFieldsOrPropertiesExcept("id","verified");
        assertThat(reg).hasFieldOrPropertyWithValue("id", 0);
        assertThat(reg).hasFieldOrPropertyWithValue("verified", false);

    }

    @Test
    public void testUpdateRegisteredInvalidEmailEmpty() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(new UserDTO(REG_ID_LOGIN, DB_USERNAME_LOGIN, "", DB_PASSWORD), headers);

        ResponseEntity<UserDTO> responseEntity =
                restTemplate.exchange("/registered-users", HttpMethod.PUT, httpEntity,
                        UserDTO.class);

        UserDTO reg = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertThat(reg).hasAllNullFieldsOrPropertiesExcept("id","verified");
        assertThat(reg).hasFieldOrPropertyWithValue("id", 0);
        assertThat(reg).hasFieldOrPropertyWithValue("verified", false);

    }
}
