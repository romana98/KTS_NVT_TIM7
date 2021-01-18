package com.project.tim7.helper;

import com.project.tim7.dto.CommentDTO;
import com.project.tim7.model.Comment;

public class CommentMapper implements MapperInterface<Comment, CommentDTO> {

	@Override
	public Comment toEntity(CommentDTO dto) {
		return new Comment(dto.getId(), dto.getDescription(), dto.getPublishedDate());
	}

	@Override
	public CommentDTO toDto(Comment entity) {
		return new CommentDTO(entity.getId(), entity.getDescription(), entity.getPublishedDate(), entity.getRegistered().getId(), entity.getRegistered().getUsername(), entity.getPictures());
	}

}
