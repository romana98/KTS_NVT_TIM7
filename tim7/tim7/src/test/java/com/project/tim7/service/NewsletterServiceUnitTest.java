package com.project.tim7.service;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.project.tim7.repository.NewsletterRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class NewsletterServiceUnitTest {
	
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
    public void setup() {
    	
    }
    

}
