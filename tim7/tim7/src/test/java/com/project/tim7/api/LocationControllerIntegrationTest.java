package com.project.tim7.api;

import com.project.tim7.dto.LocationDTO;
import com.project.tim7.dto.SubcategoryDTO;
import com.project.tim7.dto.UserLoginDTO;
import com.project.tim7.dto.UserTokenStateDTO;
import com.project.tim7.service.LocationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static com.project.tim7.constants.SubcategoryConstants.CONTROLLER_NEW_VALID_SUBCATEGORY_NAME;
import static com.project.tim7.constants.SubcategoryConstants.CONTROLLER_VALID_CATEGORY_ID;
import static org.junit.jupiter.api.Assertions.*;
import static com.project.tim7.constants.LocationConstants.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class LocationControllerIntegrationTest {

    @Autowired
    LocationService locationService;

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers;

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
    public void testSaveLocationValid(){
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setLatitude(CONTROLLER_NEW_LOCATION_LAT);
        locationDTO.setLongitude(CONTROLLER_NEW_LOCATION_LONG);
        locationDTO.setName(CONTROLLER_NEW_LOCATION_NAME);
        HttpEntity<Object> httpEntity = new HttpEntity<>(locationDTO, headers);

        ResponseEntity<LocationDTO> responseEntity =
                restTemplate.exchange("/locations", HttpMethod.POST, httpEntity, LocationDTO.class);
        LocationDTO created = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(CONTROLLER_NEW_LOCATION_LAT, created.getLatitude());
        assertEquals(CONTROLLER_NEW_LOCATION_LONG, created.getLongitude());

        boolean isDeleted = locationService.delete(created.getId());
        assertTrue(isDeleted);
    }

    @Test
    public void testSaveLocationInvalid(){

        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setLatitude(CONTROLLER_NEW_LOCATION_LAT_INVALID);
        locationDTO.setLongitude(CONTROLLER_NEW_LOCATION_LONG_INVALID);
        locationDTO.setName(CONTROLLER_NEW_LOCATION_NAME);
        HttpEntity<Object> httpEntity = new HttpEntity<>(locationDTO, headers);

        ResponseEntity<LocationDTO> responseEntity =
                restTemplate.exchange("/locations", HttpMethod.POST, httpEntity, LocationDTO.class);
        LocationDTO created = responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(created);

    }

}
