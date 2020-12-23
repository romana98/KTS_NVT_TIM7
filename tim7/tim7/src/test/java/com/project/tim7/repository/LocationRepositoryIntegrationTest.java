package com.project.tim7.repository;

import com.project.tim7.model.Location;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import static com.project.tim7.constants.LocationConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class LocationRepositoryIntegrationTest {

    @Autowired
    LocationRepository locationRepository;

    @Test
    public void testFindByLatAndLong(){
        Location found = locationRepository.findByLatitudeAndLongitude(REPO_LOCATION_LAT_FOUND, REPO_LOCATION_LONG_FOUND);
        assertEquals(REPO_LOCATION_LAT_FOUND, found.getLatitude());
        assertEquals(REPO_LOCATION_LONG_FOUND, found.getLongitude());
    }

    @Test
    public void testFindByLatAndLongNotFound(){
        Location notFound = locationRepository.findByLatitudeAndLongitude(REPO_LOCATION_LAT_NOT_FOUND, REPO_LOCATION_LONG_NOT_FOUND);
        assertNull(notFound);
    }

}
