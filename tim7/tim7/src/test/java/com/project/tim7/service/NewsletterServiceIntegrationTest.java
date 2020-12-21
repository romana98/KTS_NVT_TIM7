package com.project.tim7.service;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.project.tim7.model.CulturalOffer;
import com.project.tim7.model.Newsletter;
import com.project.tim7.model.Picture;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static com.project.tim7.constants.NewsletterConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class NewsletterServiceIntegrationTest {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@Autowired
	private NewsletterService newsletterService;

	@Test
	public void testFindAll() {
		List<Newsletter> found = newsletterService.findAll();
		assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.size());
	}
	
	@Test
	public void testFindAllPageable() {
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
		Page<Newsletter> found = newsletterService.findAll(pageable);
		assertEquals(EXPECTED_PAGES, found.getNumberOfElements());
	}

	@Test
	public void testFindOne() {
		Newsletter found = newsletterService.findOne(NEWSLETTER_ID);
		assertEquals(NEWSLETTER_ID, found.getId());
	}
	
	@Test
	public void testFindOneNotExist() {
		Newsletter found = newsletterService.findOne(NEWSLETTER_ID_NOT_EXIST);
		assertNull(found);
	}
	
	@Test
	public void testSaveOne() throws ParseException{
		Newsletter newEntity = new Newsletter(NEWSLETTER_ID_CREATE, NEWSLETTER_NAME_CREATE, NEWSLETTER_DESCRIPTION_CREATE, sdf.parse(NEWSLETTER_PUBLISHED_DATE_CREATE), 
				new CulturalOffer(OFFER_ID_CREATE, OFFER_DESCRIPTION_CREATE, sdf.parse(OFFER_ENDDATE_CREATE), OFFER_NAME_CREATE, sdf.parse(OFFER_STARTDATE_CREATE)), 
				new Picture(PICTURE_ID_CREATE, PICTURE_PICTURE_CREATE));
		Newsletter created = newsletterService.saveOne(newEntity);
		assertEquals(NEWSLETTER_NAME_CREATE, created.getName());
		newsletterService.delete(created.getId());
	}

	@Test
	public void testUpdate() throws ParseException {
		Newsletter update = new Newsletter(NEWSLETTER_ID_UPDATE, NEWSLETTER_NAME_UPDATE, NEWSLETTER_DESCRIPTION_CREATE, sdf.parse(NEWSLETTER_PUBLISHED_DATE_CREATE));
		Newsletter updated = newsletterService.update(update, PICTURE_PICTURE_CREATE);
		assertEquals(NEWSLETTER_NAME_UPDATE, updated.getName());
		update.setName(NEWSLETTER_OLD_NAME);
		newsletterService.update(update, PICTURE_PICTURE_CREATE);
	}
	
	@Test
	public void testUpdateBadId() throws ParseException {
		Newsletter update = new Newsletter(NEWSLETTER_ID_NOT_EXIST, NEWSLETTER_NAME_UPDATE, NEWSLETTER_DESCRIPTION_CREATE, sdf.parse(NEWSLETTER_PUBLISHED_DATE_CREATE));
		Newsletter updated = newsletterService.update(update, PICTURE_PICTURE_CREATE);
		assertNull(updated);
	}

	@Test
	public void testDelete() throws ParseException {
		boolean deleted = newsletterService.delete(NEWSLETTER_ID_DELETE);
		assertTrue(deleted);
		assertEquals(FIND_ALL_NUMBER_OF_ITEMS-1, newsletterService.findAll().size());
		Newsletter newEntity = new Newsletter(NEWSLETTER_ID_CREATE, DELETED_NAME, NEWSLETTER_DESCRIPTION_CREATE, sdf.parse(NEWSLETTER_PUBLISHED_DATE_CREATE), 
				new CulturalOffer(OFFER_ID_CREATE, OFFER_DESCRIPTION_CREATE, sdf.parse(OFFER_ENDDATE_CREATE), OFFER_NAME_CREATE, sdf.parse(OFFER_STARTDATE_CREATE)), 
				new Picture(PICTURE_ID_CREATE, PICTURE_PICTURE_CREATE));
		newsletterService.saveOne(newEntity);
	}
	
	@Test
	public void testDeleteBadId() throws ParseException {
		boolean deleted = newsletterService.delete(NEWSLETTER_ID_NOT_EXIST);
		assertFalse(deleted);
		assertEquals(FIND_ALL_NUMBER_OF_ITEMS, newsletterService.findAll().size());
	}
	
	@Test
	public void testSave() throws ParseException {
		Newsletter create = new Newsletter(NEWSLETTER_ID_CREATE, NEWSLETTER_NAME_CREATE, NEWSLETTER_DESCRIPTION_CREATE, sdf.parse(NEWSLETTER_PUBLISHED_DATE_CREATE));
		Newsletter created = newsletterService.save(create, OFFER_ID_CREATE, PICTURE_PICTURE_CREATE);
		assertEquals(NEWSLETTER_NAME_UPDATE, created.getName());
		assertEquals(FIND_ALL_NUMBER_OF_ITEMS+1, newsletterService.findAll().size());
		newsletterService.delete(created.getId());
	}
	
	@Test
	public void testSaveBadOfferId() throws ParseException {
		Newsletter create = new Newsletter(NEWSLETTER_ID_CREATE, NEWSLETTER_NAME_CREATE, NEWSLETTER_DESCRIPTION_CREATE, sdf.parse(NEWSLETTER_PUBLISHED_DATE_CREATE));
		Newsletter created = newsletterService.save(create, OFFER_ID_NOT_EXIST, PICTURE_PICTURE_CREATE);
		assertNull(created);
	}
	
	@Test
	public void testFindNewsletterForUser() {
		List<Newsletter> found = newsletterService.findNewsletterForUser(SUBSCRIBED_REGISTERED_ID);
		assertEquals(DB_NEWSLETTERS, found.size());
	}
	
	@Test
	public void testFindNewsletterForUserPageable() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
        Page<Newsletter> found = newsletterService.findNewsletterForUser(SUBSCRIBED_REGISTERED_ID, pageable);
		assertEquals(EXPECTED_PAGES, found.getNumberOfElements());

	}
	
	@Test
	public void testFindNewsletterForCulturalOffer() {
		List<Newsletter> found = newsletterService.findNewsletterForCulturalOffer(CULTURAL_OFFER_ID);
		assertEquals(DB_NEWSLETTERS, found.size());
	}
	
	@Test
	public void testFindNewsletterForCulturalOfferPageable() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
        Page<Newsletter> found = newsletterService.findNewsletterForCulturalOffer(CULTURAL_OFFER_ID, pageable);
		assertEquals(EXPECTED_PAGES, found.getNumberOfElements());

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
