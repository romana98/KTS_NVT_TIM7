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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.tim7.dto.CategoryDTO;
import com.project.tim7.dto.NewsletterDTO;
import com.project.tim7.helper.NewsletterMapper;
import com.project.tim7.model.Category;
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
    
    @RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<NewsletterDTO>> getAllNewsletters(){
		List<Newsletter> newsletters = newsletterService.findAll();
		
        return new ResponseEntity<>(toNewsletterDTOList(newsletters), HttpStatus.OK);
	}
    
    @RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Object> updateNewsletter(@Valid @RequestBody NewsletterDTO newsletterDTO){
		
		Newsletter updatedNewsletter = newsletterMapper.toEntity(newsletterDTO);

        boolean updated = newsletterService.updateNewsletter(updatedNewsletter, newsletterDTO.getPicture());

		if(updatedNewsletter != null) {
			return new ResponseEntity<>("Successfully updated newsletter.", HttpStatus.OK);
		}else {
			return new ResponseEntity<Object>("Updating failed.", HttpStatus.BAD_REQUEST);
		}
	}
    
    @RequestMapping(value= "/{id}",method = RequestMethod.DELETE)
   	public ResponseEntity<Object> updateNewsletter(@PathVariable("id") int id){
    	if (newsletterService.delete(id) == true)
            return new ResponseEntity<>("Successfully deleted.", HttpStatus.OK);
    	else
            return new ResponseEntity<>("Deleting failed.", HttpStatus.BAD_REQUEST);
   	}
    
    @RequestMapping(value= "/by-page",method = RequestMethod.GET)
    public ResponseEntity<Page<NewsletterDTO>> getAllNewsletters(Pageable pageable) {
        Page<Newsletter> page = newsletterService.findAll(pageable);
        List<NewsletterDTO> dtos = toNewsletterDTOList(page.toList());
        Page<NewsletterDTO> pageNewsletterDTOS = new PageImpl<>(dtos,page.getPageable(),page.getTotalElements());

        return new ResponseEntity<>(pageNewsletterDTOS, HttpStatus.OK);
    }
    
    @RequestMapping(value= "/subscribed/{id-user}", method = RequestMethod.GET)
	public ResponseEntity<List<NewsletterDTO>> findNewsletterForUser(@PathVariable("id-user") int idUser){
		List<Newsletter> newsletters = newsletterService.findNewsletterForUser(idUser);
		
        return new ResponseEntity<>(toNewsletterDTOList(newsletters), HttpStatus.OK);
	}
    
    @RequestMapping(value= "/subscribed/{id-user}/by-page",method = RequestMethod.GET)
    public ResponseEntity<Page<NewsletterDTO>> findNewsletterForUser(@PathVariable("id-user") Integer idUser, Pageable pageable) {
        Page<Newsletter> page = newsletterService.findNewsletterForUser(idUser, pageable);
        List<NewsletterDTO> dtos = toNewsletterDTOList(page.toList());
        Page<NewsletterDTO> pageNewsletterDTOS = new PageImpl<>(dtos,page.getPageable(),page.getTotalElements());

        return new ResponseEntity<>(pageNewsletterDTOS, HttpStatus.OK);
    }

	private List<NewsletterDTO> toNewsletterDTOList(List<Newsletter> newsletters) {
		ArrayList<NewsletterDTO> dtos = new ArrayList<NewsletterDTO>();
		for(Newsletter newsletter : newsletters) {
			NewsletterDTO dto = newsletterMapper.toDto(newsletter);
			dtos.add(dto);
		}
		return dtos;
	}

}
