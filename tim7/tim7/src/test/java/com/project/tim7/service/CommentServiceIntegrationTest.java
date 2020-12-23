package com.project.tim7.service;

import com.project.tim7.model.*;
import com.project.tim7.repository.CommentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static com.project.tim7.constants.CommentConstants.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CommentServiceIntegrationTest {

    @Autowired
    CommentService commentService;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    RegisteredService registeredService;

    @Autowired
    PictureService pictureService;

    @Autowired
    CulturalOfferService culturalOfferService;

    @Test
    public void testFindAll(){
        List<Comment> found = commentService.findAll();
        assertEquals(SERVICE_COUNT_COMMENTS_ALL, found.size());
    }

    @Test
    public void testFindAllPageable(){
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
        Page<Comment> found = commentService.findAll(pageable);

        assertEquals(SERVICE_COUNT_COMMENTS_ALL, found.getNumberOfElements());
    }

    @Test
    public void testFindAllPageableNOTALL(){
        Pageable pageable = PageRequest.of(0,1);
        Page<Comment> found = commentService.findAll(pageable);

        assertEquals(1, found.getNumberOfElements());
    }

    @Test
    public void testFindOneValid(){
        Comment found = commentService.findOne(SERVICE_VALID_COMMENT_ID);
        assertEquals(SERVICE_VALID_COMMENT_ID, found.getId());
    }

    @Test
    public void testFindOneNotFound(){
        Comment found = commentService.findOne(SERVICE_INVALID_COMMENT_ID);
        assertNull(found);
    }

    @Test
    public void testSaveOneValid() throws ParseException {
        Comment comment = new Comment();
        comment.setDescription(SERVICE_NEW_COMMENT_DESCRIPTION);
        comment.setPublishedDate(new SimpleDateFormat("yyyy-MM-dd").parse(SERVICE_NEW_COMMENT_DATE));
        ArrayList<String> pictures = new ArrayList<>();
        pictures.add(SERVICE_NEW_COMMENT_PICTURE);
        Comment saved = commentService.createComment(comment, SERVICE_NEW_COMMENT_REGISTERED, pictures, SERVICE_NEW_COMMENT_CULTURAL_OFFER);
        assertNotNull(saved);
        assertEquals(SERVICE_NEW_COMMENT_DESCRIPTION, saved.getDescription());
        boolean deleted = commentService.delete(saved.getId());
        assertTrue(deleted);
    }

    @Test
    public void testSaveOneInvalidRegistered() throws ParseException {
        Comment comment = new Comment();
        comment.setDescription(SERVICE_NEW_COMMENT_DESCRIPTION);
        comment.setPublishedDate(new SimpleDateFormat("yyyy-MM-dd").parse(SERVICE_NEW_COMMENT_DATE));
        ArrayList<String> pictures = new ArrayList<>();
        pictures.add(SERVICE_NEW_COMMENT_PICTURE);
        Comment saved = commentService.createComment(comment, SERVICE_NEW_COMMENT_INVALID_REGISTERED, pictures, SERVICE_NEW_COMMENT_CULTURAL_OFFER);
        assertNull(saved);
    }

    @Test
    public void testFindCommentsByCulturalOffer(){
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        Page<Comment> found = commentService.findCommentsByCulturalOffer(SERVICE_NEW_COMMENT_CULTURAL_OFFER, pageable);
        assertEquals(SERVICE_COUNT_COMMENTS_BY_CULTURAL_OFFER, found.getTotalElements());
    }
}
