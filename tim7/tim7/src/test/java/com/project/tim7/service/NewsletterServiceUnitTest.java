package com.project.tim7.service;

import static com.project.tim7.constants.NewsletterConstants.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.project.tim7.model.CulturalOffer;
import com.project.tim7.model.Newsletter;
import com.project.tim7.model.Picture;
import com.project.tim7.model.Registered;
import com.project.tim7.repository.NewsletterRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class NewsletterServiceUnitTest {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
    private NewsletterService newsletterService;

    @MockBean
    private NewsletterRepository newsletterRepository;
    
    @MockBean 
    private CulturalOfferService culturalOfferService;
	
    @MockBean
    private PictureService pictureService;

    @MockBean
    private EmailService emailService;

    @MockBean
    private RegisteredService regService;
    
    @Before
    public void setup() throws ParseException {
    	CulturalOffer offer = new CulturalOffer(OFFER_ID_CREATE, OFFER_DESCRIPTION_CREATE, sdf.parse(OFFER_ENDDATE_CREATE), OFFER_NAME_CREATE, sdf.parse(OFFER_STARTDATE_CREATE)); 
        Picture pic = new Picture(PICTURE_ID_CREATE, PICTURE_PICTURE_CREATE);
    	Newsletter savingOne = new Newsletter(NEWSLETTER_ID_CREATE, NEWSLETTER_NAME_CREATE, NEWSLETTER_DESCRIPTION_CREATE, sdf.parse(NEWSLETTER_PUBLISHED_DATE_CREATE));
        savingOne.setCulturalOffer(offer);
        savingOne.setPicture(pic);
        Newsletter savedOne = new Newsletter(NEWSLETTER_ID, NEWSLETTER_NAME_CREATE, NEWSLETTER_DESCRIPTION_CREATE, sdf.parse(NEWSLETTER_PUBLISHED_DATE_CREATE));
        savedOne.setCulturalOffer(offer);
        savedOne.setPicture(pic);
        
        //saveOne
        given(newsletterRepository.save(savingOne)).willReturn(savedOne);
        
        Registered reg = new Registered(REGISTERED_ID_CREATE, REGISTERED_EMAIL_CREATE, REGISTERED_USERNAME_CREATE, REGISTERED_PASSWORD_CREATE);
        reg.setVerified(true);
        List<String> subscribedRegs = new ArrayList<>();
        subscribedRegs.add(reg.getEmail());
        
        given(regService.findRegisteredForSubscribedCulturalOffers(OFFER_ID_CREATE)).willReturn(subscribedRegs);
        
        Newsletter updateOrDeleteBadId = new Newsletter(NEWSLETTER_ID_NOT_EXIST, NEWSLETTER_NAME_UPDATE, NEWSLETTER_DESCRIPTION_CREATE, sdf.parse(NEWSLETTER_PUBLISHED_DATE_CREATE));
      
        given(newsletterRepository.save(updateOrDeleteBadId)).willReturn(null);
        
        //save
        given(culturalOfferService.findOne(OFFER_ID_CREATE)).willReturn(offer);
        given(culturalOfferService.findOne(OFFER_ID_NOT_EXIST)).willReturn(null);
        given(pictureService.findByPicture(PICTURE_PICTURE_CREATE)).willReturn(pic);
        given(pictureService.findByPicture(PICTURE_PICTURE_CREATE_NEW)).willReturn(null);
        
        Picture picNew = new Picture(PICTURE_ID_CREATE_NEW, PICTURE_PICTURE_CREATE_NEW);
        given(pictureService.update(picNew)).willReturn(picNew);        
        
        CulturalOffer offerBad = new CulturalOffer(OFFER_ID_NOT_EXIST, OFFER_DESCRIPTION_CREATE, sdf.parse(OFFER_ENDDATE_CREATE), OFFER_NAME_CREATE, sdf.parse(OFFER_STARTDATE_CREATE)); 
        Newsletter newsletterBadOffer = new Newsletter(NEWSLETTER_ID_NOT_EXIST, NEWSLETTER_NAME_UPDATE, NEWSLETTER_DESCRIPTION_CREATE, sdf.parse(NEWSLETTER_PUBLISHED_DATE_CREATE));
        newsletterBadOffer.setCulturalOffer(offerBad);
        newsletterBadOffer.setPicture(pic);
        given(newsletterRepository.save(newsletterBadOffer)).willReturn(null);

        //delete
        given(pictureService.countPictureInNewsletters(PICTURE_ID_CREATE)).willReturn(0L);
        
        //update
        Newsletter updatingNewsletter = new Newsletter(NEWSLETTER_ID_UPDATE, NEWSLETTER_NAME_UPDATE, NEWSLETTER_DESCRIPTION_CREATE, sdf.parse(NEWSLETTER_PUBLISHED_DATE_CREATE));
        updatingNewsletter.setPicture(pic);
        updatingNewsletter.setCulturalOffer(offer);
        
        given(pictureService.update(pic)).willReturn(pic);
        given(newsletterRepository.save(updatingNewsletter)).willReturn(updatingNewsletter);

        given(newsletterRepository.findById(NEWSLETTER_ID_UPDATE)).willReturn(java.util.Optional.of(updatingNewsletter));
        given(newsletterRepository.findById(NEWSLETTER_ID_NOT_EXIST)).willReturn(null);
                    
    }
    
    @Test
    public void testSaveOne() throws Exception {
    	CulturalOffer offer = new CulturalOffer(OFFER_ID_CREATE, OFFER_NAME_CREATE, sdf.parse(OFFER_ENDDATE_CREATE), OFFER_DESCRIPTION_CREATE, sdf.parse(OFFER_STARTDATE_CREATE)); 
        Picture pic = new Picture(PICTURE_ID_CREATE, PICTURE_PICTURE_CREATE);
    	Newsletter savingOne = new Newsletter(NEWSLETTER_ID_CREATE, NEWSLETTER_NAME_CREATE, NEWSLETTER_DESCRIPTION_CREATE, sdf.parse(NEWSLETTER_PUBLISHED_DATE_CREATE));
        savingOne.setCulturalOffer(offer);
        savingOne.setPicture(pic);
        Registered reg = new Registered(REGISTERED_ID_CREATE, REGISTERED_EMAIL_CREATE, REGISTERED_USERNAME_CREATE, REGISTERED_PASSWORD_CREATE);
        reg.setVerified(true);
        List<String> subscribedRegs = new ArrayList<>();
        subscribedRegs.add(reg.getEmail());
        
        Newsletter created = newsletterService.saveOne(savingOne);
        
        verify(newsletterRepository, times(1)).save(savingOne);
        verify(regService, times(1)).findRegisteredForSubscribedCulturalOffers(OFFER_ID_CREATE);
        verify(emailService, times(1)).sendNewsletterToSubscribed(subscribedRegs, OFFER_NAME_CREATE, NEWSLETTER_NAME_CREATE, NEWSLETTER_DESCRIPTION_CREATE);

        assertEquals(NEWSLETTER_NAME_CREATE, created.getName());
    }
    
    @Test
    public void testSaveOneBadOfferId() throws Exception {
    	CulturalOffer offer = new CulturalOffer(OFFER_ID_NOT_EXIST, OFFER_NAME_CREATE, sdf.parse(OFFER_ENDDATE_CREATE), OFFER_DESCRIPTION_CREATE, sdf.parse(OFFER_STARTDATE_CREATE)); 
        Picture pic = new Picture(PICTURE_ID_CREATE, PICTURE_PICTURE_CREATE);
        Newsletter newsletterBadOffer = new Newsletter(NEWSLETTER_ID_NOT_EXIST, NEWSLETTER_NAME_UPDATE, NEWSLETTER_DESCRIPTION_CREATE, sdf.parse(NEWSLETTER_PUBLISHED_DATE_CREATE));
        newsletterBadOffer.setCulturalOffer(offer);
        newsletterBadOffer.setPicture(pic);
        Registered reg = new Registered(REGISTERED_ID_CREATE, REGISTERED_EMAIL_CREATE, REGISTERED_USERNAME_CREATE, REGISTERED_PASSWORD_CREATE);
        reg.setVerified(true);
        List<String> subscribedRegs = new ArrayList<>();
        
        Newsletter created = newsletterService.saveOne(newsletterBadOffer);
        
        verify(newsletterRepository, times(1)).save(newsletterBadOffer);
        verify(regService, times(1)).findRegisteredForSubscribedCulturalOffers(OFFER_ID_NOT_EXIST);
        verify(emailService, times(1)).sendNewsletterToSubscribed(subscribedRegs, OFFER_NAME_CREATE, NEWSLETTER_NAME_CREATE, NEWSLETTER_DESCRIPTION_CREATE);

        assertEquals(null, created);
    }
    
    @Test
    public void testSave() throws Exception {
    	Newsletter saving = new Newsletter(NEWSLETTER_ID_CREATE, NEWSLETTER_NAME_CREATE, NEWSLETTER_DESCRIPTION_CREATE, sdf.parse(NEWSLETTER_PUBLISHED_DATE_CREATE));
        
    	Newsletter created = newsletterService.save(saving, OFFER_ID_CREATE, PICTURE_PICTURE_CREATE);
        
        verify(culturalOfferService, times(1)).findOne(OFFER_ID_CREATE);
        verify(pictureService, times(1)).findByPicture(PICTURE_PICTURE_CREATE);
        //posto je pronasao sliku, ne ulazi u metodu ispod
        verify(pictureService, times(0)).update(new Picture(PICTURE_PICTURE_CREATE));

        assertEquals(NEWSLETTER_NAME_CREATE, created.getName());
    }
    
    @Test
    public void testSavePictureNotFound() throws Exception {
    	Newsletter saving = new Newsletter(NEWSLETTER_ID_CREATE, NEWSLETTER_NAME_CREATE, NEWSLETTER_DESCRIPTION_CREATE, sdf.parse(NEWSLETTER_PUBLISHED_DATE_CREATE));
        
    	Newsletter created = newsletterService.save(saving, OFFER_ID_CREATE, PICTURE_PICTURE_CREATE_NEW);
        
        verify(culturalOfferService, times(1)).findOne(OFFER_ID_CREATE);
        verify(pictureService, times(1)).findByPicture(PICTURE_PICTURE_CREATE_NEW);
        verify(pictureService, times(1)).update(new Picture(PICTURE_PICTURE_CREATE_NEW));

        assertEquals(NEWSLETTER_NAME_CREATE, created.getName());
    }
    
    
    @Test
    public void testUpdate() throws Exception {
    	CulturalOffer offer = new CulturalOffer(OFFER_ID_CREATE, OFFER_DESCRIPTION_CREATE, sdf.parse(OFFER_ENDDATE_CREATE), OFFER_NAME_CREATE, sdf.parse(OFFER_STARTDATE_CREATE)); 
        Picture pic = new Picture(PICTURE_ID_CREATE, PICTURE_PICTURE_CREATE);
    	Newsletter updatingNewsletter = new Newsletter(NEWSLETTER_ID_UPDATE, NEWSLETTER_NAME_UPDATE, NEWSLETTER_DESCRIPTION_CREATE, sdf.parse(NEWSLETTER_PUBLISHED_DATE_CREATE));
        updatingNewsletter.setPicture(pic);
        updatingNewsletter.setCulturalOffer(offer);

        Newsletter updated = newsletterService.update(updatingNewsletter, PICTURE_PICTURE_CREATE);
        
        verify(newsletterRepository, times(1)).findById(NEWSLETTER_ID_UPDATE);
        verify(pictureService, times(1)).findByPicture(PICTURE_PICTURE_CREATE);
        verify(pictureService, times(0)).update(new Picture(PICTURE_PICTURE_CREATE));

        assertEquals(NEWSLETTER_NAME_UPDATE, updated.getName());
    }
    
    @Test
    public void testUpdateBadId() throws Exception {
    	CulturalOffer offer = new CulturalOffer(OFFER_ID_CREATE, OFFER_DESCRIPTION_CREATE, sdf.parse(OFFER_ENDDATE_CREATE), OFFER_NAME_CREATE, sdf.parse(OFFER_STARTDATE_CREATE)); 
        Picture pic = new Picture(PICTURE_ID_CREATE, PICTURE_PICTURE_CREATE);
    	Newsletter updatingNewsletter = new Newsletter(NEWSLETTER_ID_NOT_EXIST, NEWSLETTER_NAME_UPDATE, NEWSLETTER_DESCRIPTION_CREATE, sdf.parse(NEWSLETTER_PUBLISHED_DATE_CREATE));
        updatingNewsletter.setPicture(pic);
        updatingNewsletter.setCulturalOffer(offer);

        Newsletter updated = newsletterService.update(updatingNewsletter, PICTURE_PICTURE_CREATE);
        
        verify(newsletterRepository, times(1)).findById(NEWSLETTER_ID_NOT_EXIST);
        verify(pictureService, times(0)).findByPicture(PICTURE_PICTURE_CREATE);
        verify(pictureService, times(0)).update(new Picture(PICTURE_PICTURE_CREATE));

        assertEquals(null, updated);
    }
    
    @Test
    public void testDelete() throws Exception {
        boolean deleted = newsletterService.delete(NEWSLETTER_ID_UPDATE);
        
        verify(newsletterRepository, times(1)).findById(NEWSLETTER_ID_UPDATE);
        verify(pictureService, times(1)).countPictureInNewsletters(PICTURE_ID_CREATE);

        assertEquals(true, deleted);
    }
    
    @Test
    public void testDeleteBadId() throws Exception {
        boolean deleted = newsletterService.delete(NEWSLETTER_ID_NOT_EXIST);
        
        verify(newsletterRepository, times(1)).findById(NEWSLETTER_ID_NOT_EXIST);
        verify(pictureService, times(0)).countPictureInNewsletters(PICTURE_ID_CREATE);

        assertEquals(false, deleted);
    }
    

}
