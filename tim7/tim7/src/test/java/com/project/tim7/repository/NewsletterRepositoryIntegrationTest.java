package com.project.tim7.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.project.tim7.model.Newsletter;
import static com.project.tim7.constants.NewsletterConstants.*;


import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class NewsletterRepositoryIntegrationTest {
	
	@Autowired
	private NewsletterRepository newsletterRepository;
	
	@Test
	public void findNewsletterForUser() {
        List<Newsletter> found = newsletterRepository.findNewsletterForUser(SUBSCRIBED_REGISTERED_ID);
        assertEquals(DB_NEWSLETTERS, found.size());
    }

	@Test
	public void findNewsletterForCulturalOffer() {
        List<Newsletter> found = newsletterRepository.findNewsletterForCulturalOffer(CULTURAL_OFFER_ID);
        assertEquals(DB_NEWSLETTERS, found.size());
    }
	
	@Test
	public void findNewsletterForUserNull() {
        List<Newsletter> found = newsletterRepository.findNewsletterForUser(SUBSCRIBED_REGISTERED_ID_NULL);
        assertEquals(DB_NEWSLETTERS_NULL, found.size());
    }

	@Test
	public void findNewsletterForCulturalOfferNull() {
        List<Newsletter> found = newsletterRepository.findNewsletterForCulturalOffer(CULTURAL_OFFER_ID_NULL);
        assertEquals(DB_NEWSLETTERS_NULL, found.size());
    }
	
	@Test
	public void findNewsletterForUserPageable() {
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
		Page<Newsletter> found = newsletterRepository.findNewsletterForUser(SUBSCRIBED_REGISTERED_ID, pageable);
        assertEquals(EXPECTED_PAGES, found.getNumberOfElements());
    }

	@Test
	public void findNewsletterForCulturalOfferPageable() {
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
		Page<Newsletter> found = newsletterRepository.findNewsletterForCulturalOffer(CULTURAL_OFFER_ID, pageable);
        assertEquals(EXPECTED_PAGES, found.getNumberOfElements());
    }
	
	@Test
	public void findNewsletterForUserNullPageable() {
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
		Page<Newsletter> found = newsletterRepository.findNewsletterForUser(SUBSCRIBED_REGISTERED_ID_NULL, pageable);
        assertEquals(EXPECTED_PAGES_NULL, found.getNumberOfElements());
    }

	@Test
	public void findNewsletterForCulturalOfferNullPageable() {
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
		Page<Newsletter> found = newsletterRepository.findNewsletterForCulturalOffer(CULTURAL_OFFER_ID_NULL, pageable);
        assertEquals(EXPECTED_PAGES_NULL, found.getNumberOfElements());
    }
	
	@Test
	public void testFindNewsletterForUserByCategory() {
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE_3);
        Page<Newsletter> found = newsletterRepository.findNewsletterForUserByCategory(SUBSCRIBED_USER, SUBSCRIBED_CATEGORY, pageable);
		assertEquals(EXPECTED_SUBSCRIBED_BY_CATEGORY, found.getNumberOfElements());
	}
}
