package com.project.tim7.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.tim7.dto.CommentDTO;
import com.project.tim7.dto.RatingDTO;
import com.project.tim7.helper.RatingMapper;
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
	
	@RequestMapping(method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createRating(@Valid @RequestBody RatingDTO ratingDTO){
        
		if(ratingService.createRating(ratingMapper.toEntity(ratingDTO), ratingDTO.getCulturalOfferId(), ratingDTO.getRegisteredId())) {
            ratingDTO.setId(ratingService.getRatingIdByDTO(ratingDTO));
			return new ResponseEntity<>(ratingDTO ,HttpStatus.CREATED);
		}else {
			return new ResponseEntity<Object>("Rating failed!",HttpStatus.BAD_REQUEST);
		}
    }

}
