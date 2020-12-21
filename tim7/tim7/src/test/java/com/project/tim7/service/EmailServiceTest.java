package com.project.tim7.service;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static com.project.tim7.constants.EmailConstants.*;

import com.project.tim7.helper.SmtpServerRule;

import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Rule
    public SmtpServerRule smtpServerRule = new SmtpServerRule(2525);

    @Test
    public void testSendVerificationMail() throws MessagingException, IOException {
        emailService.sendVerificationMail(RECIPIENT1, RECIPIENT1_ID);

        MimeMessage[] receivedMessages = smtpServerRule.getMessages();
        assertEquals(1, receivedMessages.length);

        MimeMessage current = receivedMessages[0];

        assertEquals(VERIFY_SUBJECT, current.getSubject());
        assertEquals(RECIPIENT1, current.getAllRecipients()[0].toString());
        assertTrue(String.valueOf(current.getContent()).contains(VERIFY_TEXT));
    }
    
    @Test
    public void sendNewsletterToSubscribed() throws MessagingException, IOException {
    	List<String> emails = new ArrayList<>(); 
    	emails.add(RECIPIENT2);
    	emails.add(RECIPIENT3);
        emailService.sendNewsletterToSubscribed(emails, OFFER_NAME, NEWSLETTER_NAME, NEWSLETTER_DESCRIPTION);

        MimeMessage[] receivedMessages = smtpServerRule.getMessages();
        assertEquals(2, receivedMessages.length);

        MimeMessage current1 = receivedMessages[0];
        MimeMessage current2 = receivedMessages[1];

        assertEquals(NEWSLETTER_SUBJECT, current1.getSubject());
        assertEquals(NEWSLETTER_SUBJECT, current2.getSubject());
        assertEquals(RECIPIENT2, current1.getAllRecipients()[0].toString());
        assertEquals(RECIPIENT3, current2.getAllRecipients()[0].toString());
        assertTrue(String.valueOf(current1.getContent()).contains(NEWSLETTER_DESCRIPTION));
        assertTrue(String.valueOf(current2.getContent()).contains(NEWSLETTER_DESCRIPTION));

    }

}
