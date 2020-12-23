package com.project.tim7.repository;

import com.project.tim7.model.Comment;
import com.project.tim7.model.Subcategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.*;
import static com.project.tim7.constants.CommentConstants.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CommentRepositoryIntegrationTest {

    @Autowired
    CommentRepository commentRepository;

    @Test
    public void testFindAllCommentsByCulturalOffer(){
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        Page<Comment> found = commentRepository.findCommentsOfCulturalOffer(EXIST_CULTURAL_OFFER_ID, pageable);
        assertEquals(COUNT_COMMENTS, found.getTotalElements());
    }
}
