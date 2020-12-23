package com.project.tim7.service;

import com.project.tim7.model.Location;
import com.project.tim7.repository.LocationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import static com.project.tim7.constants.LocationConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class LocationServiceIntegrationTest {

    @Autowired
    LocationService locationService;

    @Autowired
    LocationRepository locationRepository;

    @Test
    public void testFindOneValid(){
        Location found = locationService.findOne(SERVICE_LOCATION_ID_VALID);
        assertEquals(SERVICE_LOCATION_ID_VALID, found.getId());
    }

    @Test
    public void testFindOneInvalid(){
        Location notFound = locationService.findOne(SERVICE_LOCATION_ID_INVALID);
        assertNull(notFound);
    }

    @Test
    public void testSaveOneValid(){
        Location newLocation = new Location();
        newLocation.setLatitude(SERVICE_NEW_LOCATION_LAT);
        newLocation.setLongitude(SERVICE_NEW_LOCATION_LONG);
        newLocation.setName(SERVICE_NEW_LOCATION_NAME);
        Location created = locationService.saveOne(newLocation);
        assertEquals(SERVICE_NEW_LOCATION_LAT, created.getLatitude());
        assertEquals(SERVICE_NEW_LOCATION_LONG, created.getLongitude());
        assertEquals(SERVICE_NEW_LOCATION_NAME, created.getName());
        boolean deleted = locationService.delete(created.getId());
        assertTrue(deleted);
    }

    @Test
    public void testSaveOneInvalid(){
        Location newLocation = new Location();
        newLocation.setLatitude(SERVICE_NEW_LOCATION_LAT_INVALID);
        newLocation.setLongitude(SERVICE_NEW_LOCATION_LONG_INVALID);
        newLocation.setName(SERVICE_NEW_LOCATION_NAME);
        Location created = locationService.saveOne(newLocation);
        assertNull(created);
    }
}
