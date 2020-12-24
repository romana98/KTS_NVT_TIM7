package com.project.tim7.service;

import com.project.tim7.dto.CulturalOfferDTO;
import com.project.tim7.model.CulturalOffer;
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
import java.util.ArrayList;
import java.util.List;

import static com.project.tim7.constants.CommentConstants.PAGEABLE_PAGE;
import static com.project.tim7.constants.CommentConstants.SERVICE_NEW_COMMENT_DATE;
import static org.junit.jupiter.api.Assertions.*;
import static com.project.tim7.constants.CulturalOfferConstants.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CulturalOfferServiceIntegrationTest {

    @Autowired
    CulturalOfferService culturalOfferService;

    @Test
    public void testFindAll(){
        List<CulturalOffer> culturalOffers = culturalOfferService.findAll();
        assertEquals(FIND_ALL_CULTURAL_OFFERS_COUNT, culturalOffers.size());
    }

    @Test
    public void testFindAllPageable(){
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGE_SIZE);
        Page<CulturalOffer> culturalOfferPage = culturalOfferService.findAll(pageable);
        assertEquals(FIND_ALL_CULTURAL_OFFERS_COUNT_PAGED, culturalOfferPage.getNumberOfElements());
    }

    @Test
    public void testFindOneExists(){
        CulturalOffer culturalOffer = culturalOfferService.findOne(FIND_ONE_CULTURAL_OFFER_EXISTS);
        assertEquals(FIND_ONE_CULTURAL_OFFER_EXISTS, culturalOffer.getId());
    }

    @Test
    public void testFindOneNotExist(){
        CulturalOffer culturalOffer = culturalOfferService.findOne(FIND_ONE_CULTURAL_OFFER_NOT_EXISTS);
        assertNull(culturalOffer);
    }

    @Test
    public void testsSaveOneValid() throws ParseException {
        int size = culturalOfferService.findAll().size();
        CulturalOfferDTO culturalOffer = new CulturalOfferDTO();

        ArrayList<String> pictures = new ArrayList<>();
        pictures.add(SAVE_ONE_CULTURAL_OFFER_PICTURE1);
        pictures.add(SAVE_ONE_CULTURAL_OFFER_PICTURE2);

        culturalOffer.setDescription(SAVE_ONE_CULTURAL_OFFER_DESCRIPTION);
        culturalOffer.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(SAVE_ONE_CULTURAL_OFFER_ENDDATE));
        culturalOffer.setName(SAVE_ONE_CULTURAL_OFFER_NAME);
        culturalOffer.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(SAVE_ONE_CULTURAL_OFFER_STARTDATE));
        culturalOffer.setLocation(SAVE_ONE_CULTURAL_OFFER_LOCATION);
        culturalOffer.setSubcategory(SAVE_ONE_CULTURAL_OFFER_SUBCATEGORY);
        culturalOffer.setPictures(pictures);

        CulturalOffer culturalOfferAdded = culturalOfferService.saveOne(culturalOffer);

        assertEquals(SAVE_ONE_CULTURAL_OFFER_NAME,culturalOfferAdded.getName());
        assertEquals(SAVE_ONE_CULTURAL_OFFER_DESCRIPTION,culturalOfferAdded.getDescription());
        assertEquals(SAVE_ONE_CULTURAL_OFFER_LOCATION,culturalOfferAdded.getLocation().getId());
        assertEquals(SAVE_ONE_CULTURAL_OFFER_SUBCATEGORY,culturalOfferAdded.getSubcategory().getId());
        assertEquals(size+1, culturalOfferService.findAll().size());
    }

    @Test
    public void testsSaveOneInvalid(){
        CulturalOfferDTO culturalOffer = new CulturalOfferDTO();

        CulturalOffer culturalOfferAdded = culturalOfferService.saveOne(culturalOffer);

        assertNull(culturalOfferAdded);
    }
}
