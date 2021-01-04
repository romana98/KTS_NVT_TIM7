package com.project.tim7.api;

import javax.validation.Valid;

import com.project.tim7.model.Comment;
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

import com.project.tim7.dto.CommentDTO;
import com.project.tim7.dto.UserDTO;
import com.project.tim7.helper.AdministratorMapper;
import com.project.tim7.helper.CommentMapper;
import com.project.tim7.model.Person;
import com.project.tim7.model.Registered;
import com.project.tim7.repository.CommentRepository;
import com.project.tim7.service.CommentService;
import com.project.tim7.service.RegisteredService;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "https://localhost:4200")
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
		if(person.getId() != commentDTO.getRegisteredId()) {
			return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
		}
		
		Comment newComment = commentService.createComment(commentMapper.toEntity(commentDTO), commentDTO.getRegisteredId(), commentDTO.getPicturesId(), commentDTO.getCulturalOfferId());
		if(newComment != null) {
            commentDTO.setId(newComment.getId());
			return new ResponseEntity<>(commentDTO, HttpStatus.CREATED);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
    }

    @RequestMapping(value = "/{culturalOfferId}/by-page", method = RequestMethod.GET)
	public ResponseEntity<Page<CommentDTO>> getComments(@PathVariable int culturalOfferId, Pageable pageable){
		Page<Comment> page = commentService.findCommentsByCulturalOffer(culturalOfferId, pageable);
		List<CommentDTO> dtos = toCommentDTOList(page.toList());
		Page<CommentDTO> pageDTOS = new PageImpl<>(dtos, page.getPageable(), page.getTotalElements());
		return new ResponseEntity<>(pageDTOS, HttpStatus.OK);
	}

	private List<CommentDTO> toCommentDTOList(List<Comment> toList) {

		ArrayList<CommentDTO> comments = new ArrayList<CommentDTO>();
		for(Comment comment : toList){
			CommentDTO dto = commentMapper.toDto(comment);
			comments.add(dto);
		}
		return comments;

	}


}
