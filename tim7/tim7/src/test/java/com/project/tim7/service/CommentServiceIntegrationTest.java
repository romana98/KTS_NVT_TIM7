package com.project.tim7.service;

import com.project.tim7.model.Category;
import com.project.tim7.model.Comment;
import com.project.tim7.model.Picture;
import com.project.tim7.model.Registered;
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
        assertEquals(COUNT_COMMENTS, found.size());
    }

    @Test
    public void testFindAllPageable(){
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
        Page<Comment> found = commentService.findAll(pageable);

        assertEquals(COUNT_COMMENTS_PAGEABLE, found.getNumberOfElements());
    }

    @Test
    public void testFindOneValid(){
        Comment found = commentService.findOne(EXIST_COMMENT_ID);
        assertEquals(found.getId(), EXIST_COMMENT_ID);
    }

    @Test
    public void testFindOneNotFound(){
        Comment found = commentService.findOne(NONEXIST_COMMENT_ID);
        assertNull(found);
    }

    @Test
    public void testSaveOneValid() throws ParseException {
        Comment comment = new Comment();
        comment.setDescription(NEW_COMMENT_DESCRIPTION);
        comment.setPublishedDate(new SimpleDateFormat("yyyy-MM-dd").parse(DATE_STRING));
        ArrayList<String> pictures = new ArrayList<>();
        pictures.add("http://dummyimage.com/600x600.bmp");
        Comment saved = commentService.createComment(comment, EXIST_REGISTERED_ID, pictures, EXIST_CULTURAL_OFFER_ID);
        assertNotNull(saved);
        assertEquals(saved.getDescription(), NEW_COMMENT_DESCRIPTION);
    }

    @Test
    public void testSaveOneInvalidRegistered(){

    }

    //findCommentsByCulturalOffer
    @Test
    public void testFindCommentsByCulturalOffer(){

    }
}
