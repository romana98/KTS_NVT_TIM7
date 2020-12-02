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
import com.project.tim7.dto.UserDTO;
import com.project.tim7.helper.AdministratorMapper;
import com.project.tim7.helper.CommentMapper;
import com.project.tim7.repository.CommentRepository;
import com.project.tim7.service.CommentService;

@RestController
@RequestMapping(value="/comments")
public class CommentController {
	
	@Autowired
	CommentService commentService;
	
	private CommentMapper commentMapper;
	
	public CommentController() {
		this.commentMapper = new CommentMapper();
	}
	
	@RequestMapping(method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createComment(@Valid @RequestBody CommentDTO commentDTO){
        
		int newCommentId = commentService.createComment(commentMapper.toEntity(commentDTO), commentDTO.getRegisteredId(), commentDTO.getPicturesId(), commentDTO.getCulturalOfferId());
		if(newCommentId > 0) {
            commentDTO.setId(newCommentId);
			return new ResponseEntity<>(commentDTO, HttpStatus.CREATED);
		}else {
			return new ResponseEntity<Object>("Commenting failed!", HttpStatus.BAD_REQUEST);
		}
    }

}
