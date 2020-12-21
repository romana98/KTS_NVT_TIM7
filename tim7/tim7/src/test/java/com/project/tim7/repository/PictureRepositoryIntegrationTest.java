package com.project.tim7.repository;

import static com.project.tim7.constants.NewsletterConstants.DB_NEWSLETTERS;
import static com.project.tim7.constants.NewsletterConstants.SUBSCRIBED_REGISTERED_ID;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import static com.project.tim7.constants.PictureConstants.*;

import com.project.tim7.model.Picture;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class PictureRepositoryIntegrationTest {
	
	@Autowired
	private PictureRepository pictureRepository;
	
	@Test
	public void testFindByPicture() {
        Picture found = pictureRepository.findByPicture(PICTURE_STRING);
        assertEquals(new Picture(PICTURE_ID, PICTURE_STRING), found);
    }
	
	@Test
	public void testFindByPictureNotFound() {
        Picture found = pictureRepository.findByPicture(PICTURE_STRING_FAIL);
        assertEquals(null, found);
    }
	
	@Test
	public void testCountPictureInNewsletters() {
		long found = pictureRepository.countPictureInNewsletters(PICTURE_ID);
		assertEquals(PICTURE_ID_EXPECTED_IN_NEWSLETTERS, found);
	}
	
	@Test
	public void testCountPictureInNewslettersNotFound() {
		long found = pictureRepository.countPictureInNewsletters(PICTURE_ID_OFFER);
		assertEquals(PICTURE_ID_OFFER_EXPECTED_IN_NEWSLETTERS, found);
	}


}
