package com.project.tim7.api;

import com.project.tim7.dto.CulturalOfferDTO;
import com.project.tim7.dto.LocationDTO;
import com.project.tim7.helper.LocationMapper;
import com.project.tim7.model.CulturalOffer;
import com.project.tim7.model.Location;
import com.project.tim7.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value="/locations")
public class LocationController {

    @Autowired
    LocationService locationService;

    private LocationMapper locationMapper = new LocationMapper();

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping(method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createLocation(@Valid @RequestBody LocationDTO location){
        System.out.println(location);
        boolean saved = locationService.saveOne(locationMapper.toEntity(location));

        if(!saved){
            return new ResponseEntity<>("Location can't be added.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Successfully added location.", HttpStatus.CREATED);

    }

}
