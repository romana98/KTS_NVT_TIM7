package com.project.tim7.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Set;

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
public class PictureServiceIntegrationTest {
		
	@Autowired
	private PictureService pictureService;
	
	@Test
	public void testFindOne() {
		Picture found = pictureService.findOne(PICTURE_ID);
		assertEquals(PICTURE_ID, found.getId());
	}
	
	@Test
	public void testFindOneNotExist() {
		Picture found = pictureService.findOne(PICTURE_ID_NOT_EXIST);
		assertNull(found);
	}
	
	@Test
	public void testSaveOne() {
		Picture created = pictureService.saveOne(new Picture(PICTURE_ID_CREATE, PICTURE_STRING_CREATE));
		assertEquals(PICTURE_STRING_CREATE, created.getPicture());
		pictureService.delete(created.getId());
	}
	
	@Test
	public void testSaveOneAlreadyExists() {
		Picture created = pictureService.saveOne(new Picture(PICTURE_ID_CREATE, PICTURE_STRING));
		assertNull(created);
	}
	
	@Test
	public void testDelete() {
		boolean deleted = pictureService.delete(PICTURE_ID_DELETE);
		assertEquals(true, deleted);
		pictureService.saveOne(new Picture(PICTURE_STRING_DELETE));
	}
	
	@Test
	public void testDeleteNotExist() {
		boolean deleted = pictureService.delete(PICTURE_ID_NOT_EXIST);
		assertEquals(false, deleted);
	}
	
	@Test
	public void testFindByPicture() {
		Picture found = pictureService.findByPicture(PICTURE_STRING);
		assertEquals(PICTURE_STRING, found.getPicture());
	}
	
	@Test
	public void testFindByPictureNotExist() {
		Picture found = pictureService.findByPicture(PICTURE_STRING_NOT_EXIST);
		assertNull(found);
	}
	
	@Test
	public void testUpdate() {
		Picture updated = pictureService.update(new Picture(PICTURE_ID_UPDATE, PICTURE_STRING_UPDATE));
		assertEquals(PICTURE_STRING_UPDATE, updated.getPicture());
		pictureService.update(new Picture(PICTURE_ID_UPDATE, PICTURE_STRING_OLD));
	}
	
	@Test
	public void testCountPictureInNewsletter() {
		long found = pictureService.countPictureInNewsletters(PICTURE_ID);
		assertEquals(PICTURE_ID_EXPECTED_IN_NEWSLETTERS, found);
	}
	
	@Test
	public void testGetPictures() {
		ArrayList<String> pictureStrings = new ArrayList<>();
		pictureStrings.add(PICTURE_STRING_COMMENT_1);
		pictureStrings.add(PICTURE_STRING_COMMENT_2);
		Set<Picture> pictureObjects = pictureService.getPictures(pictureStrings);
		assertEquals(PICTURE_STRING_COMMENT_1, pictureObjects.iterator().next().getPicture());
		assertEquals(PICTURES_COMMENT_NUM, pictureObjects.size());
	}
	
	@Test
	public void testCheckDuplicatesFound() {
		ArrayList<String> pictureStrings = new ArrayList<>();
		pictureStrings.add(PICTURE_STRING_COMMENT_1);
		pictureStrings.add(PICTURE_STRING_COMMENT_1);
		boolean found = pictureService.checkDuplicates(pictureStrings);
		assertEquals(true, found);
	}
	
	@Test
	public void testCheckDuplicatesNotFound() {
		ArrayList<String> pictureStrings = new ArrayList<>();
		pictureStrings.add(PICTURE_STRING_COMMENT_1);
		pictureStrings.add(PICTURE_STRING_COMMENT_2);
		boolean found = pictureService.checkDuplicates(pictureStrings);
		assertEquals(false, found);
	}

}
