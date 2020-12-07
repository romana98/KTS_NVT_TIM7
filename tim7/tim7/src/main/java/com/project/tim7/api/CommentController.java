package com.project.tim7.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.tim7.dto.CommentDTO;
import com.project.tim7.dto.UserDTO;
import com.project.tim7.helper.AdministratorMapper;
import com.project.tim7.helper.CommentMapper;
import com.project.tim7.model.Person;
import com.project.tim7.model.Registered;
import com.project.tim7.repository.CommentRepository;
import com.project.tim7.service.CommentService;
import com.project.tim7.service.RegisteredService;

@RestController
@RequestMapping(value="/comments")
public class CommentController {
	
	@Autowired
	CommentService commentService;
	
	@Autowired
	RegisteredService registeredService;
	
	private CommentMapper commentMapper;
	
	public CommentController() {
		this.commentMapper = new CommentMapper();
	}
	
	@PreAuthorize("hasRole('ROLE_REGISTERED')")
	@RequestMapping(method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createComment(@Valid @RequestBody CommentDTO commentDTO){
        
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Person person = (Person) authentication.getPrincipal();
		if(person.getId() != commentDTO.getId()) {
			return new ResponseEntity<Object>("Authentication failed!", HttpStatus.BAD_REQUEST);
		}
		
		int newCommentId = commentService.createComment(commentMapper.toEntity(commentDTO), commentDTO.getRegisteredId(), commentDTO.getPicturesId(), commentDTO.getCulturalOfferId());
		if(newCommentId > 0) {
            commentDTO.setId(newCommentId);
			return new ResponseEntity<>(commentDTO, HttpStatus.CREATED);
		}else {
			return new ResponseEntity<Object>("Commenting failed!", HttpStatus.BAD_REQUEST);
		}
    }

}
