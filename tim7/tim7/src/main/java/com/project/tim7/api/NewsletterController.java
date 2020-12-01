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

import com.project.tim7.dto.NewsletterDTO;
import com.project.tim7.helper.NewsletterMapper;
import com.project.tim7.model.Newsletter;
import com.project.tim7.service.NewsletterService;


@RestController
@RequestMapping(value="/newsletter", produces = MediaType.APPLICATION_JSON_VALUE)
public class NewsletterController {
	
	@Autowired
    NewsletterService newsletterService;

    private NewsletterMapper newsletterMapper;

    public NewsletterController(){
    	newsletterMapper = new NewsletterMapper();
    }

    @RequestMapping(method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createNewsletter(@Valid @RequestBody NewsletterDTO newsletterDTO){
    	
    	Newsletter newNewsletter = newsletterMapper.toEntity(newsletterDTO);
        boolean saved = newsletterService.saveNewsletter(newNewsletter, newsletterDTO.getCulturalOfferId(), newsletterDTO.getPicture());

        if(!saved){
            return new ResponseEntity<>("Cultural offer does not exist.",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Successfully added newsletter.", HttpStatus.CREATED);
    }

}
