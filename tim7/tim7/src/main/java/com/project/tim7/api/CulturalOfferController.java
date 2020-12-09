package com.project.tim7.api;

import com.project.tim7.dto.CulturalOfferDTO;
import com.project.tim7.dto.NewsletterDetailsDTO;
import com.project.tim7.helper.CulturalOfferMapper;
import com.project.tim7.helper.NewsletterMapper;
import com.project.tim7.model.*;
import com.project.tim7.service.CulturalOfferService;
import com.project.tim7.service.LocationService;
import com.project.tim7.service.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
        boolean saved = culturalOfferService.saveOne(culturalOffer,culturalOfferDTO.getLocation(),culturalOfferDTO.getSubcategory(),culturalOfferDTO.getPictures());

        if(!saved){
            return new ResponseEntity<>("Cultural offer can't be added.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Successfully added cultural offer.", HttpStatus.CREATED);
    }

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

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateCulturalOffer(@Valid @RequestBody CulturalOfferDTO culturalOfferDTO){

        boolean updated = culturalOfferService.update(culturalOfferDTO);

        if(updated) {
            return new ResponseEntity<>("Successfully updated newsletter.", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Updating failed.", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value= "/by-page",method = RequestMethod.GET)
    public ResponseEntity<Page<CulturalOfferDTO>> getAllCulturalOffersPaged(Pageable pageable) {
        Page<CulturalOffer> page = culturalOfferService.findAll(pageable);

        List<CulturalOfferDTO> culturalOfferDTOS = culturalOfferMapper.toCulturalOfferDTOList(page.toList());

        Page<CulturalOfferDTO> culturalOfferDTOPage = new PageImpl<>(culturalOfferDTOS,page.getPageable(),page.getTotalElements());

        return new ResponseEntity<>(culturalOfferDTOPage, HttpStatus.OK);
    }



}
