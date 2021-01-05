package com.project.tim7.api;
import com.project.tim7.dto.CulturalOfferDTO;
import com.project.tim7.dto.FilterDTO;
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

@CrossOrigin(origins = "https://localhost:4200")
@RestController
@RequestMapping(value="/cultural-offers")
public class CulturalOfferController {

    private final CulturalOfferMapper culturalOfferMapper = new CulturalOfferMapper();

    @Autowired
    private CulturalOfferService culturalOfferService;

    /**
     * Creating cultural offer with Data Transfer Object attributes passed on to the API.
     * @param culturalOfferDTO - CulturalOffer Data Transfer Object used for extraction of attributes needed for creation of Cultural Offer.
     * @return - Returning status code 200 or 400 and created Cultural Offer or NULL.
     */
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping(method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CulturalOfferDTO> createCulturalOffer(@Valid @RequestBody CulturalOfferDTO culturalOfferDTO){
        CulturalOffer savedCulturalOffer = culturalOfferService.saveOne(culturalOfferDTO);

        CulturalOfferDTO payload = savedCulturalOffer == null ? null : culturalOfferMapper.toDto(savedCulturalOffer);

        //Setting response to either OK if Cultural Offer is saved, otherwise BAD_REQUEST.
        return new ResponseEntity<>(payload, savedCulturalOffer == null ? HttpStatus.BAD_REQUEST: HttpStatus.OK);
    }

    /**
     * Deleting cultural offer according to given Identification number.
     * @param id - Identification number of cultural offer.
     * @return - Returning status code 200 or 400 with toast text.
     */
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping(value= "/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteCulturalOffer(@PathVariable("id") int id){

        boolean deletedSuccess = culturalOfferService.delete(id);
        String toastMessage = deletedSuccess ? "Successfully deleted cultural offer." : "Removal of cultural offer failed.";
        HttpStatus statusCode = deletedSuccess ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(toastMessage, statusCode);
    }

    /**
     * Returning one cultural offer with given Identification number.
     * @param id - Identification number of Cultural Offer.
     * @return - Returning Cultural Offer Data Transfer Object with necessary attributes.
     */
    @RequestMapping(value= "/{id}",method = RequestMethod.GET)
    public ResponseEntity<CulturalOfferDTO> getCulturalOffer(@PathVariable("id") int id){

        CulturalOffer culturalOffer = culturalOfferService.findOne(id);

        //Checking if cultural offer was found and returning status code 200 with DTO for success or 400 and NULL for fail.
        HttpStatus httpCode = culturalOffer == null ? HttpStatus.BAD_REQUEST : HttpStatus.OK;
        CulturalOfferDTO payload = culturalOffer == null ? null : culturalOfferMapper.toDto(culturalOffer);

        return new ResponseEntity<>(payload, httpCode);
    }

     /**
     * Updating cultural offer according to CulturalOfferDTO sent as RequestBody.
     * @param culturalOfferDTO - Data transfer object for CulturalOffer.
     * @return ResponseEntity<CulturalOfferDTO> - Returning status 200 or 400 along with updated CulturalOffer.
     */
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CulturalOfferDTO> updateCulturalOffer(@Valid @RequestBody CulturalOfferDTO culturalOfferDTO){

        CulturalOffer updatedCulturalOffer = culturalOfferService.update(culturalOfferDTO);

        return updatedCulturalOffer != null ?
                new ResponseEntity<>(culturalOfferMapper.toDto(updatedCulturalOffer), HttpStatus.OK) :
                new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

    }

    /**
     * Returning number of cultural offers according to page and page size.
     * @param pageable - Page object with number of elements to return and number of page.
     * @return - Returning desired number of objects according to page object.
     */
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping(value= "/by-page",method = RequestMethod.GET)
    public ResponseEntity<Page<CulturalOfferDTO>> getAllCulturalOffersPaged(Pageable pageable) {
        Page<CulturalOffer> page = culturalOfferService.findAll(pageable);
        List<CulturalOfferDTO> culturalOfferDTOS = culturalOfferMapper.toCulturalOfferDTOList(page.toList());
        Page<CulturalOfferDTO> culturalOfferDTOPage = new PageImpl<>(culturalOfferDTOS,page.getPageable(),page.getTotalElements());

        return new ResponseEntity<>(culturalOfferDTOPage, HttpStatus.OK);
    }

    /**
     * Returning filtered cultural offers filtered by certain parameter.
     * @param filterDTO - Filter name and filter value.
     * @param pageable - Page object with number of elements to return and number of page.
     * @return - Returning page of filtered cultural offer data transfer objects.
     */
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, value="/filter")
    public ResponseEntity<Page<CulturalOfferDTO>> filterCulturalOffers(@RequestBody FilterDTO filterDTO, Pageable pageable){
    	Page<CulturalOffer> page = culturalOfferService.filter(filterDTO, pageable);
    	List<CulturalOfferDTO> culturalOfferDTOs = culturalOfferMapper.toCulturalOfferDTOList(page.toList());
    	Page<CulturalOfferDTO> culturalOfferDTOPage = new PageImpl<>(culturalOfferDTOs, page.getPageable(), page.getTotalElements());

    	return new ResponseEntity<>(culturalOfferDTOPage, HttpStatus.OK);
    }
    
    /**
     * Get cultural offers of one subcategory
     * @param id - subcateory id
     * @return - Returning cultural offer of one subcategory
     */
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping(value= "/subcategory/{id}/by-page",method = RequestMethod.GET)
    public ResponseEntity<Page<CulturalOfferDTO>> getAllCulturalOffersBySubcategoryPaged(@PathVariable("id") int id, Pageable pageable) {
        Page<CulturalOffer> page = culturalOfferService.findBySubcategory(id, pageable);
        List<CulturalOfferDTO> culturalOfferDTOS = culturalOfferMapper.toCulturalOfferDTOList(page.toList());
        Page<CulturalOfferDTO> culturalOfferDTOPage = new PageImpl<>(culturalOfferDTOS,page.getPageable(),page.getTotalElements());

        return new ResponseEntity<>(culturalOfferDTOPage, HttpStatus.OK);
    }

    /**
     * Subscribing user to cultural offer.
     * @param subscribeData - id user and id cultural offer.
     * @return - Returning cultural offer on which user subscribed.
     */
    @PreAuthorize("hasRole('ROLE_REGISTERED')")
    @RequestMapping(value= "/subscribe", method = RequestMethod.POST)
	public ResponseEntity<String> subscribe(@RequestBody Subscribe subscribeData){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Person person = (Person) authentication.getPrincipal();

        HttpStatus httpCode;
        String toastMessage;

		if(person.getId() != subscribeData.idUser) {
		    httpCode = HttpStatus.BAD_REQUEST;
		    toastMessage = "Authentication failed!";
		    return new ResponseEntity<>(toastMessage, httpCode);
		}
		
		CulturalOffer subscribed = culturalOfferService.subscribe(subscribeData.idOffer, subscribeData.idUser);
        httpCode = subscribed == null ? HttpStatus.BAD_REQUEST : HttpStatus.OK;
        toastMessage = subscribed == null ? "Subscription failed!" : "Successfully subscribed!";

		return new ResponseEntity<>(toastMessage,httpCode);
    }
    
    /**
     * Unsubscribing user from cultural offer.
     * @param subscribeData - id user and id cultural offer.
     * @return - Returning cultural offer from which user unsubscribed.
     */
    @PreAuthorize("hasRole('ROLE_REGISTERED')")
    @RequestMapping(value= "/unsubscribe", method = RequestMethod.POST)
	public ResponseEntity<String> unsubscribe(@RequestBody Subscribe subscribeData){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Person person = (Person) authentication.getPrincipal();

        HttpStatus httpCode;
        String toastMessage;

		if(person.getId() != subscribeData.idUser) {
		    httpCode = HttpStatus.BAD_REQUEST;
		    toastMessage = "Authentication failed!";
		    return new ResponseEntity<>(toastMessage, httpCode);
		}
		
		CulturalOffer subscribed = culturalOfferService.unsubscribe(subscribeData.idOffer, subscribeData.idUser);
        httpCode = subscribed == null ? HttpStatus.BAD_REQUEST : HttpStatus.OK;
        toastMessage = subscribed == null ? "Unsubscription failed!" : "Successfully unsubscribed!";

		return new ResponseEntity<>(toastMessage,httpCode);
    }


    static class Subscribe {
        public int idOffer;
        public int idUser;
    }
}
