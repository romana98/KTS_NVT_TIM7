package com.project.tim7.api;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.tim7.dto.NewsletterDTO;
import com.project.tim7.dto.NewsletterDetailsDTO;
import com.project.tim7.helper.NewsletterMapper;
import com.project.tim7.model.Newsletter;
import com.project.tim7.model.Person;
import com.project.tim7.service.NewsletterService;

@CrossOrigin(origins = "https://localhost:4200")
@RestController
@RequestMapping(value="/newsletter", produces = MediaType.APPLICATION_JSON_VALUE)
public class NewsletterController {
	
	@Autowired
    NewsletterService newsletterService;

    private NewsletterMapper newsletterMapper;

    public NewsletterController(){
    	newsletterMapper = new NewsletterMapper();
    }

	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping(method= RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> createNewsletter(@Valid @RequestPart("newsletter") NewsletterDetailsDTO newsletterDTO, @RequestPart(name="image", required=false) MultipartFile multipartFile){
    	
    	Newsletter newNewsletter = newsletterMapper.toEntity(newsletterDTO);
        boolean saved = newsletterService.saveNewsletter(newNewsletter, newsletterDTO.getCulturalOfferId(), multipartFile);

        if(!saved){
            return new ResponseEntity<>("Cultural offer does not exist.",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Successfully added newsletter.", HttpStatus.CREATED);
    }
    
	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<NewsletterDTO>> getAllNewsletters(){
		List<Newsletter> newsletters = newsletterService.findAll();
		
        return new ResponseEntity<>(toNewsletterDTOList(newsletters), HttpStatus.OK);
	}
    
	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping(method = RequestMethod.PUT, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<String> updateNewsletter(@Valid @RequestPart("newsletter") NewsletterDetailsDTO newsletterDTO, @RequestPart(name="image", required=false) MultipartFile multipartFile){
		
		Newsletter updatedNewsletter = newsletterMapper.toEntity(newsletterDTO);
		
        boolean updated = newsletterService.updateNewsletter(updatedNewsletter, multipartFile);

		if(updated) {
			return new ResponseEntity<>("Successfully updated newsletter.", HttpStatus.OK);
		}else {
			return new ResponseEntity<>("Updating failed.", HttpStatus.BAD_REQUEST);
		}
	}
    
	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping(value= "/{id}",method = RequestMethod.DELETE)
   	public ResponseEntity<String> deleteNewsletter(@PathVariable("id") int id){
    	if (newsletterService.delete(id) == true)
            return new ResponseEntity<>("Successfully deleted.", HttpStatus.OK);
    	else
            return new ResponseEntity<>("Deleting failed.", HttpStatus.BAD_REQUEST);
   	}
    
	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping(value= "/by-page",method = RequestMethod.GET)
    public ResponseEntity<Page<NewsletterDTO>> getAllNewsletters(Pageable pageable) {
        Page<Newsletter> page = newsletterService.findAll(pageable);
        List<NewsletterDTO> dtos = toNewsletterDTOList(page.toList());
        Page<NewsletterDTO> pageNewsletterDTOS = new PageImpl<>(dtos,page.getPageable(),page.getTotalElements());

        return new ResponseEntity<>(pageNewsletterDTOS, HttpStatus.OK);
    }
    
	@PreAuthorize("hasRole('ROLE_REGISTERED')")
    @RequestMapping(value= "/subscribed/{id-user}", method = RequestMethod.GET)
	public ResponseEntity<?> findNewsletterForUser(@PathVariable("id-user") int idUser){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Person person = (Person) authentication.getPrincipal();
		if(person.getId() != idUser) {
			return new ResponseEntity<String>("Authentication failed!", HttpStatus.BAD_REQUEST);
		}
		
		List<Newsletter> newsletters = newsletterService.findNewsletterForUser(idUser);
		
        return new ResponseEntity<List<NewsletterDTO>>(toNewsletterDTOList(newsletters), HttpStatus.OK);
	}
    
	@PreAuthorize("hasRole('ROLE_REGISTERED')")
    @RequestMapping(value= "/subscribed/{id-user}/by-page",method = RequestMethod.GET)
    public ResponseEntity<?> findNewsletterForUser(@PathVariable("id-user") Integer idUser, Pageable pageable) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Person person = (Person) authentication.getPrincipal();
		if(person.getId() != idUser) {
			return new ResponseEntity<String>("Authentication failed!", HttpStatus.BAD_REQUEST);
		}
		
        Page<Newsletter> page = newsletterService.findNewsletterForUser(idUser, pageable);
        List<NewsletterDTO> dtos = toNewsletterDTOList(page.toList());
        Page<NewsletterDTO> pageNewsletterDTOS = new PageImpl<>(dtos,page.getPageable(),page.getTotalElements());

        return new ResponseEntity<Page<NewsletterDTO>>(pageNewsletterDTOS, HttpStatus.OK);
    }
	
	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR') || hasRole('ROLE_REGISTERED')")
    @RequestMapping(value= "/{id}",method = RequestMethod.GET)
    public ResponseEntity<NewsletterDetailsDTO> getNewsletter(@PathVariable("id") Integer id) {
        Newsletter newsletter = newsletterService.findOne(id);
        if (newsletter == null) {
        	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
        	NewsletterDetailsDTO dto = newsletterMapper.toNewsletterDetailsDto(newsletter);
            return new ResponseEntity<NewsletterDetailsDTO>(dto, HttpStatus.OK);
        }
    }
	
	private List<NewsletterDTO> toNewsletterDTOList(List<Newsletter> newsletters) {
		ArrayList<NewsletterDTO> dtos = new ArrayList<NewsletterDTO>();
		for(Newsletter newsletter : newsletters) {
			NewsletterDTO dto = newsletterMapper.toNewsletterDto(newsletter);
			dtos.add(dto);
		}
		return dtos;
	}

}
