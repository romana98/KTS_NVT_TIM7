package com.project.tim7.api;

import com.project.tim7.dto.CulturalOfferDTO;
import com.project.tim7.helper.CulturalOfferMapper;
import com.project.tim7.model.*;
import com.project.tim7.service.CulturalOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value="/cultural-offers")
public class CulturalOfferController {

    @Autowired
    private CulturalOfferService culturalOfferService;


    private CulturalOfferMapper culturalOfferMapper = new CulturalOfferMapper();

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping(method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createCulturalOffer(@Valid @RequestBody CulturalOfferDTO culturalOfferDTO){
        CulturalOffer culturalOffer = culturalOfferMapper.toEntity(culturalOfferDTO);
        boolean saved = culturalOfferService.saveOne(culturalOffer,culturalOfferDTO.getLocation(),culturalOfferDTO.getSubcategory(),culturalOfferDTO.getPictures());

        if(!saved){
            return new ResponseEntity<>("Cultural offer can't be added.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Successfully added cultural offer.", HttpStatus.CREATED);
    }


    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping(value= "/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteCulturalOffer(@PathVariable("id") int id){
        if(culturalOfferService.delete(id)){
            return new ResponseEntity<>("Successfully deleted cultural offer.", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Removal of cultural offer failed.", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value= "/{id}",method = RequestMethod.GET)
    public ResponseEntity<CulturalOfferDTO> getCulturalOffer(@PathVariable("id") int id){
        CulturalOffer culturalOffer = culturalOfferService.findOne(id);
        if(culturalOffer == null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        CulturalOfferDTO payload = culturalOfferMapper.toDto(culturalOffer);
        return new ResponseEntity<>(payload, HttpStatus.OK);


    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateCulturalOffer(@Valid @RequestBody CulturalOfferDTO culturalOfferDTO){

        boolean updated = culturalOfferService.update(culturalOfferDTO);

        if(updated) {
            return new ResponseEntity<>("Successfully updated newsletter.", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Updating failed.", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping(value= "/by-page",method = RequestMethod.GET)
    public ResponseEntity<Page<CulturalOfferDTO>> getAllCulturalOffersPaged(Pageable pageable) {
        Page<CulturalOffer> page = culturalOfferService.findAll(pageable);

        List<CulturalOfferDTO> culturalOfferDTOS = culturalOfferMapper.toCulturalOfferDTOList(page.toList());

        Page<CulturalOfferDTO> culturalOfferDTOPage = new PageImpl<>(culturalOfferDTOS,page.getPageable(),page.getTotalElements());

        return new ResponseEntity<>(culturalOfferDTOPage, HttpStatus.OK);
    }
    
    static class Subscribe {
    	public int idOffer;
    	public int idUser;
    }
    
    @PreAuthorize("hasRole('ROLE_REGISTERED')")
    @RequestMapping(value= "/subscribe", method = RequestMethod.POST)
	public ResponseEntity<?> subscribe(@RequestBody Subscribe subscribeData){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Person person = (Person) authentication.getPrincipal();
		if(person.getId() != subscribeData.idUser) {
			return new ResponseEntity<String>("Authentication failed!", HttpStatus.BAD_REQUEST);
		}
		
		boolean subscribed = culturalOfferService.subscribe(subscribeData.idOffer, subscribeData.idUser);
		if (subscribed)
	        return new ResponseEntity<String>("Successflly subscribed!", HttpStatus.OK);
		else
			return new ResponseEntity<String>("Subscription failed!", HttpStatus.BAD_REQUEST);
	}



}
