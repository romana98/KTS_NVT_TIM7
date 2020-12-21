package com.project.tim7.api;

import javax.validation.Valid;

import com.project.tim7.model.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.project.tim7.dto.RatingDTO;
import com.project.tim7.helper.RatingMapper;
import com.project.tim7.model.Person;
import com.project.tim7.service.RatingService;

@RestController
@RequestMapping(value="/ratings")
public class RatingController {
	
	@Autowired
	RatingService ratingService;
	
	private RatingMapper ratingMapper;
	
	public RatingController() {
		this.ratingMapper = new RatingMapper();
	}
	
	@PreAuthorize("hasRole('ROLE_REGISTERED')")
	@RequestMapping(method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createRating(@Valid @RequestBody RatingDTO ratingDTO){
        
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Person person = (Person) authentication.getPrincipal();
		if(person.getId() != ratingDTO.getRegisteredId()) {
			return new ResponseEntity<Object>("Authentication failed!",HttpStatus.BAD_REQUEST);
		}
		
		Rating newRating = ratingService.createRating(ratingMapper.toEntity(ratingDTO), ratingDTO.getCulturalOfferId());
		if(newRating != null) {
			ratingDTO.setId(newRating.getId());
			return new ResponseEntity<>(ratingDTO ,HttpStatus.CREATED);
		}else {
			return new ResponseEntity<Object>("Rating failed!",HttpStatus.BAD_REQUEST);
		}
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getRating/{culturalOfferId}")
	public ResponseEntity<RatingDTO> getRating(@PathVariable int culturalOfferId){

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Person person = (Person) authentication.getPrincipal();
		if(person == null){
			return new ResponseEntity<RatingDTO>(new RatingDTO(0), HttpStatus.OK);
		}else{
			int rating = ratingService.getRating(culturalOfferId, person.getId());
			return new ResponseEntity<RatingDTO>(new RatingDTO(rating), HttpStatus.OK);
		}

	}

}
