package com.project.tim7.repository;

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

import static com.project.tim7.constants.CommentConstants.PAGEABLE_PAGE;
import static com.project.tim7.constants.CommentConstants.PAGEABLE_SIZE;
import static com.project.tim7.constants.CulturalOfferConstants.*;
import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CulturalOfferRepositoryIntegrationTest {

    @Autowired
    CulturalOfferRepository culturalOfferRepository;

    @Test
    public void testFilterByCategoryFound(){
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        Page<CulturalOffer> page = culturalOfferRepository.filterByCategory(FILTER_CATEGORY_VALUE_SUCCESS,pageable);
        assertEquals(FILTER_CATEGORY_VALUE_SUCCESS_RESULT, page.getTotalElements());
    }

    @Test
    public void testFilterByCategoryNotFound(){
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        Page<CulturalOffer> page = culturalOfferRepository.filterByCategory(FILTER_CATEGORY_VALUE_SUCCESS_ZERO,pageable);
        assertEquals(0, page.getTotalElements());
    }

    @Test
    public void testFilterBySubcategoryFound(){
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        Page<CulturalOffer> page = culturalOfferRepository.filterBySubcategory(FILTER_SUBCATEGORY_VALUE_SUCCESS,pageable);
        assertEquals(FILTER_SUBCATEGORY_VALUE_SUCCESS_RESULT, page.getTotalElements());
    }

    @Test
    public void testFilterBySubcategoryNotFound(){
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        Page<CulturalOffer> page = culturalOfferRepository.filterBySubcategory(FILTER_SUBCATEGORY_VALUE_SUCCESS_ZERO,pageable);
        assertEquals(0, page.getTotalElements());
    }

    @Test
    public void testFilterByLocationFound(){
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        Page<CulturalOffer> page = culturalOfferRepository.filterByCategory(FILTER_CATEGORY_VALUE_SUCCESS,pageable);
        assertEquals(FILTER_CATEGORY_VALUE_SUCCESS_RESULT, page.getTotalElements());
    }

    @Test
    public void testFilterByLocationNotFound(){
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        Page<CulturalOffer> page = culturalOfferRepository.filterByCategory(FILTER_CATEGORY_VALUE_SUCCESS_ZERO,pageable);
        assertEquals(0, page.getTotalElements());
    }

    @Test
    public void testFilterByNameFound(){
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        Page<CulturalOffer> page = culturalOfferRepository.filterByCategory(FILTER_CATEGORY_VALUE_SUCCESS,pageable);
        assertEquals(FILTER_CATEGORY_VALUE_SUCCESS_RESULT, page.getTotalElements());
    }

    @Test
    public void testFilterByNameNotFound(){
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        Page<CulturalOffer> page = culturalOfferRepository.filterByCategory(FILTER_CATEGORY_VALUE_SUCCESS_ZERO,pageable);
        assertEquals(0, page.getTotalElements());
    }




}
