package com.project.tim7.api;

import com.project.tim7.dto.CulturalOfferDTO;
import com.project.tim7.dto.NewsletterDTO;
import com.project.tim7.helper.CulturalOfferMapper;
import com.project.tim7.helper.NewsletterMapper;
import com.project.tim7.model.CulturalOffer;
import com.project.tim7.model.Location;
import com.project.tim7.model.Newsletter;
import com.project.tim7.model.Subcategory;
import com.project.tim7.service.CulturalOfferService;
import com.project.tim7.service.LocationService;
import com.project.tim7.service.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value="/cultural-offers")
public class CulturalOfferController {

    @Autowired
    private CulturalOfferService culturalOfferService;



    private CulturalOfferMapper culturalOfferMapper = new CulturalOfferMapper();

    @RequestMapping(method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createCulturalOffer(@Valid @RequestBody CulturalOfferDTO culturalOfferDTO){
        CulturalOffer culturalOffer = culturalOfferMapper.toEntity(culturalOfferDTO);
        boolean saved = culturalOfferService.saveOne(culturalOffer,culturalOfferDTO.getLocation(),culturalOfferDTO.getSubcategory());

        if(!saved){
            return new ResponseEntity<>("Cultural offer can't be added.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Successfully added cultural offer.", HttpStatus.CREATED);
    }

    @RequestMapping(value= "/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteNewsletter(@PathVariable("id") int id){
        if(culturalOfferService.delete(id)){
            return new ResponseEntity<>("Successfully deleted cultural offer.", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Removal of cultural offer failed.", HttpStatus.BAD_REQUEST);
        }
    }



}
