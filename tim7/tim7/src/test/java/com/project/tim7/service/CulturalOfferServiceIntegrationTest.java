package com.project.tim7.service;

import com.project.tim7.dto.CulturalOfferDTO;
import com.project.tim7.dto.FilterDTO;
import com.project.tim7.model.CulturalOffer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.project.tim7.constants.CommentConstants.PAGEABLE_PAGE;
import static com.project.tim7.constants.CommentConstants.PAGEABLE_SIZE;
import static com.project.tim7.constants.CulturalOfferConstants.UPDATE_ONE_CULTURAL_OFFER_PICTURE2;
import static org.junit.jupiter.api.Assertions.*;
import static com.project.tim7.constants.CulturalOfferConstants.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CulturalOfferServiceIntegrationTest {

    @Autowired
    CulturalOfferService culturalOfferService;

    //Finding all cultural offers not paged.
    @Test
    public void testFindAll(){
        List<CulturalOffer> culturalOffers = culturalOfferService.findAll();
        assertEquals(FIND_ALL_CULTURAL_OFFERS_COUNT, culturalOffers.size());
    }

    //Finding first cultural offer.
    @Test
    public void testFindAllPageable(){
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGE_SIZE);
        Page<CulturalOffer> culturalOfferPage = culturalOfferService.findAll(pageable);
        assertEquals(FIND_ALL_CULTURAL_OFFERS_COUNT_PAGED, culturalOfferPage.getNumberOfElements());
    }

    //Finding cultural offer which exists;
    @Test
    public void testFindOneExists(){
        CulturalOffer culturalOffer = culturalOfferService.findOne(FIND_ONE_CULTURAL_OFFER_EXISTS);
        assertEquals(FIND_ONE_CULTURAL_OFFER_EXISTS, culturalOffer.getId());
    }

    //Finding cultural offer which does not exist;
    @Test
    public void testFindOneNotExist(){
        CulturalOffer culturalOffer = culturalOfferService.findOne(FIND_ONE_CULTURAL_OFFER_NOT_EXISTS);
        assertNull(culturalOffer);
    }

    @Test
    public void testSaveOneValid() throws ParseException {
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
        //Hardcore rollback :D
        culturalOfferService.delete(culturalOfferAdded.getId());
    }

    //Adding empty cultural offer.
    @Test
    public void testSaveOneInvalidCulturalOffer(){
        CulturalOfferDTO culturalOffer = new CulturalOfferDTO();

        CulturalOffer culturalOfferAdded = culturalOfferService.saveOne(culturalOffer);

        assertNull(culturalOfferAdded);
    }

    //Not valid Location added.
    @Test
    public void testSaveOneInvalidLocation() throws ParseException {
        CulturalOfferDTO culturalOffer = new CulturalOfferDTO();

        ArrayList<String> pictures = new ArrayList<>();
        pictures.add(SAVE_ONE_CULTURAL_OFFER_PICTURE1);
        pictures.add(SAVE_ONE_CULTURAL_OFFER_PICTURE2);

        culturalOffer.setDescription(SAVE_ONE_CULTURAL_OFFER_DESCRIPTION);
        culturalOffer.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(SAVE_ONE_CULTURAL_OFFER_ENDDATE));
        culturalOffer.setName(SAVE_ONE_CULTURAL_OFFER_NAME);
        culturalOffer.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(SAVE_ONE_CULTURAL_OFFER_STARTDATE));
        culturalOffer.setSubcategory(SAVE_ONE_CULTURAL_OFFER_SUBCATEGORY);
        culturalOffer.setLocation(SAVE_ONE_CULTURAL_OFFER_LOCATION_FAIL);
        culturalOffer.setPictures(pictures);

        CulturalOffer culturalOfferAdded = culturalOfferService.saveOne(culturalOffer);

        assertNull(culturalOfferAdded);
    }

    //Not valid subcategory,
    @Test
    public void testSaveOneInvalidSubcategory() throws ParseException {
        CulturalOfferDTO culturalOffer = new CulturalOfferDTO();

        ArrayList<String> pictures = new ArrayList<>();
        pictures.add(SAVE_ONE_CULTURAL_OFFER_PICTURE1);
        pictures.add(SAVE_ONE_CULTURAL_OFFER_PICTURE2);

        culturalOffer.setDescription(SAVE_ONE_CULTURAL_OFFER_DESCRIPTION);
        culturalOffer.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(SAVE_ONE_CULTURAL_OFFER_ENDDATE));
        culturalOffer.setName(SAVE_ONE_CULTURAL_OFFER_NAME);
        culturalOffer.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(SAVE_ONE_CULTURAL_OFFER_STARTDATE));
        culturalOffer.setSubcategory(SAVE_ONE_CULTURAL_OFFER_SUBCATEGORY_FAIL);
        culturalOffer.setLocation(SAVE_ONE_CULTURAL_OFFER_LOCATION);
        culturalOffer.setPictures(pictures);

        CulturalOffer culturalOfferAdded = culturalOfferService.saveOne(culturalOffer);

        assertNull(culturalOfferAdded);
    }

    //Trying to save already existing cultural offer.
    @Test
    public void testSaveOneInvalidAlreadyExist() throws ParseException {
        CulturalOfferDTO culturalOffer = new CulturalOfferDTO();

        ArrayList<String> pictures = new ArrayList<>();
        pictures.add(SAVE_ONE_CULTURAL_OFFER_PICTURE1);
        pictures.add(SAVE_ONE_CULTURAL_OFFER_PICTURE2);

        culturalOffer.setDescription(SAVE_ONE_CULTURAL_OFFER_DESCRIPTION);
        culturalOffer.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(SAVE_ONE_CULTURAL_OFFER_ENDDATE));
        culturalOffer.setName(SAVE_ONE_CULTURAL_OFFER_NAME_FAIL);
        culturalOffer.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(SAVE_ONE_CULTURAL_OFFER_STARTDATE));
        culturalOffer.setSubcategory(SAVE_ONE_CULTURAL_OFFER_SUBCATEGORY);
        culturalOffer.setLocation(SAVE_ONE_CULTURAL_OFFER_LOCATION);
        culturalOffer.setPictures(pictures);

        CulturalOffer culturalOfferAdded = culturalOfferService.saveOne(culturalOffer);

        assertNull(culturalOfferAdded);
    }

    @Test
    public void testUpdateValid() throws ParseException {
        CulturalOfferDTO culturalOffer = new CulturalOfferDTO();
        CulturalOffer oldCulturalOffer = culturalOfferService.findOne(UPDATE_ONE_CULTURAL_OFFER_ID);

        ArrayList<String> pictures = new ArrayList<>();
        pictures.add(UPDATE_ONE_CULTURAL_OFFER_PICTURE1);
        pictures.add(UPDATE_ONE_CULTURAL_OFFER_PICTURE2);

        culturalOffer.setId(UPDATE_ONE_CULTURAL_OFFER_ID);
        culturalOffer.setDescription(UPDATE_ONE_CULTURAL_OFFER_DESCRIPTION);
        culturalOffer.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(UPDATE_ONE_CULTURAL_OFFER_ENDDATE));
        culturalOffer.setName(UPDATE_ONE_CULTURAL_OFFER_NAME);
        culturalOffer.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(UPDATE_ONE_CULTURAL_OFFER_STARTDATE));
        culturalOffer.setLocation(UPDATE_ONE_CULTURAL_OFFER_LOCATION);
        culturalOffer.setSubcategory(UPDATE_ONE_CULTURAL_OFFER_SUBCATEGORY);
        culturalOffer.setPictures(pictures);

        CulturalOffer culturalOfferAdded = culturalOfferService.update(culturalOffer);

        assertEquals(UPDATE_ONE_CULTURAL_OFFER_NAME,culturalOfferAdded.getName());
        assertEquals(UPDATE_ONE_CULTURAL_OFFER_DESCRIPTION,culturalOfferAdded.getDescription());
        assertEquals(UPDATE_ONE_CULTURAL_OFFER_LOCATION,culturalOfferAdded.getLocation().getId());
        assertEquals(UPDATE_ONE_CULTURAL_OFFER_SUBCATEGORY,culturalOfferAdded.getSubcategory().getId());
        assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse(UPDATE_ONE_CULTURAL_OFFER_ENDDATE), culturalOffer.getEndDate());
        assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse(UPDATE_ONE_CULTURAL_OFFER_STARTDATE), culturalOffer.getStartDate());
        assertEquals(UPDATE_PICTURE_SIZE, culturalOffer.getPictures().size());

        culturalOfferService.update(oldCulturalOffer);
    }

    //Name for change already exists.
    @Test
    public void testUpdateInvalidNameAlreadyExists() throws ParseException {
        CulturalOfferDTO culturalOffer = new CulturalOfferDTO();

        ArrayList<String> pictures = new ArrayList<>();
        pictures.add(UPDATE_ONE_CULTURAL_OFFER_PICTURE1);
        pictures.add(UPDATE_ONE_CULTURAL_OFFER_PICTURE2);

        culturalOffer.setId(UPDATE_ONE_CULTURAL_OFFER_ID);
        culturalOffer.setDescription(UPDATE_ONE_CULTURAL_OFFER_DESCRIPTION);
        culturalOffer.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(UPDATE_ONE_CULTURAL_OFFER_ENDDATE));
        culturalOffer.setName(UPDATE_ONE_CULTURAL_OFFER_NAME_FAIL);
        culturalOffer.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(UPDATE_ONE_CULTURAL_OFFER_STARTDATE));
        culturalOffer.setLocation(UPDATE_ONE_CULTURAL_OFFER_LOCATION);
        culturalOffer.setSubcategory(UPDATE_ONE_CULTURAL_OFFER_SUBCATEGORY);
        culturalOffer.setPictures(pictures);

        CulturalOffer culturalOfferAdded = culturalOfferService.update(culturalOffer);

        assertNull(culturalOfferAdded);
    }

    //Cultural offer with id does not exist.
    @Test
    public void testUpdateCulturalOfferNotExists() throws ParseException {
        CulturalOfferDTO culturalOffer = new CulturalOfferDTO();

        ArrayList<String> pictures = new ArrayList<>();
        pictures.add(UPDATE_ONE_CULTURAL_OFFER_PICTURE1);
        pictures.add(UPDATE_ONE_CULTURAL_OFFER_PICTURE2);

        culturalOffer.setId(UPDATE_ONE_CULTURAL_OFFER_ID_FAIL);
        culturalOffer.setDescription(UPDATE_ONE_CULTURAL_OFFER_DESCRIPTION);
        culturalOffer.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(UPDATE_ONE_CULTURAL_OFFER_ENDDATE));
        culturalOffer.setName(UPDATE_ONE_CULTURAL_OFFER_NAME_FAIL);
        culturalOffer.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(UPDATE_ONE_CULTURAL_OFFER_STARTDATE));
        culturalOffer.setLocation(UPDATE_ONE_CULTURAL_OFFER_LOCATION);
        culturalOffer.setSubcategory(UPDATE_ONE_CULTURAL_OFFER_SUBCATEGORY);
        culturalOffer.setPictures(pictures);

        CulturalOffer culturalOfferAdded = culturalOfferService.update(culturalOffer);

        assertNull(culturalOfferAdded);
    }

    //Changing to now valid location.
    @Test
    public void testUpdateInvalidLocation() throws ParseException {
        CulturalOfferDTO culturalOffer = new CulturalOfferDTO();

        ArrayList<String> pictures = new ArrayList<>();
        pictures.add(UPDATE_ONE_CULTURAL_OFFER_PICTURE1);
        pictures.add(UPDATE_ONE_CULTURAL_OFFER_PICTURE2);

        culturalOffer.setId(UPDATE_ONE_CULTURAL_OFFER_ID_FAIL);
        culturalOffer.setDescription(UPDATE_ONE_CULTURAL_OFFER_DESCRIPTION);
        culturalOffer.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(UPDATE_ONE_CULTURAL_OFFER_ENDDATE));
        culturalOffer.setName(UPDATE_ONE_CULTURAL_OFFER_NAME_FAIL);
        culturalOffer.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(UPDATE_ONE_CULTURAL_OFFER_STARTDATE));
        culturalOffer.setLocation(SAVE_ONE_CULTURAL_OFFER_LOCATION_FAIL);
        culturalOffer.setSubcategory(UPDATE_ONE_CULTURAL_OFFER_SUBCATEGORY);
        culturalOffer.setPictures(pictures);

        CulturalOffer culturalOfferAdded = culturalOfferService.update(culturalOffer);

        assertNull(culturalOfferAdded);
    }

    //Changing to not valid subcategory.
    @Test
    public void testUpdateInvalidSubcategory() throws ParseException {
        CulturalOfferDTO culturalOffer = new CulturalOfferDTO();

        ArrayList<String> pictures = new ArrayList<>();
        pictures.add(UPDATE_ONE_CULTURAL_OFFER_PICTURE1);
        pictures.add(UPDATE_ONE_CULTURAL_OFFER_PICTURE2);

        culturalOffer.setId(UPDATE_ONE_CULTURAL_OFFER_ID_FAIL);
        culturalOffer.setDescription(UPDATE_ONE_CULTURAL_OFFER_DESCRIPTION);
        culturalOffer.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(UPDATE_ONE_CULTURAL_OFFER_ENDDATE));
        culturalOffer.setName(UPDATE_ONE_CULTURAL_OFFER_NAME_FAIL);
        culturalOffer.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(UPDATE_ONE_CULTURAL_OFFER_STARTDATE));
        culturalOffer.setLocation(SAVE_ONE_CULTURAL_OFFER_LOCATION_FAIL);
        culturalOffer.setSubcategory(UPDATE_ONE_CULTURAL_OFFER_SUBCATEGORY);
        culturalOffer.setPictures(pictures);

        CulturalOffer culturalOfferAdded = culturalOfferService.update(culturalOffer);

        assertNull(culturalOfferAdded);
    }

    //Not valid picture input, same picture twice.
    @Test
    public void testUpdateInvalidPictures() throws ParseException {
        CulturalOfferDTO culturalOffer = new CulturalOfferDTO();

        ArrayList<String> pictures = new ArrayList<>();
        pictures.add(UPDATE_ONE_CULTURAL_OFFER_PICTURE1);
        pictures.add(UPDATE_ONE_CULTURAL_OFFER_PICTURE1);

        culturalOffer.setId(UPDATE_ONE_CULTURAL_OFFER_ID_FAIL);
        culturalOffer.setDescription(UPDATE_ONE_CULTURAL_OFFER_DESCRIPTION);
        culturalOffer.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(UPDATE_ONE_CULTURAL_OFFER_ENDDATE));
        culturalOffer.setName(UPDATE_ONE_CULTURAL_OFFER_NAME_FAIL);
        culturalOffer.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(UPDATE_ONE_CULTURAL_OFFER_STARTDATE));
        culturalOffer.setLocation(SAVE_ONE_CULTURAL_OFFER_LOCATION_FAIL);
        culturalOffer.setSubcategory(UPDATE_ONE_CULTURAL_OFFER_SUBCATEGORY);
        culturalOffer.setPictures(pictures);

        CulturalOffer culturalOfferAdded = culturalOfferService.update(culturalOffer);

        assertNull(culturalOfferAdded);
    }

    //Deleting cultural offer which exists.
    @Transactional
    @Rollback
    @Test
    public void testDeleteValid(){
        int size = culturalOfferService.findAll().size();
        assertTrue(culturalOfferService.delete(DELETE_CULTURAL_OFFER_ID));
        assertEquals(DELETE_EXPECTED_SIZE, size-1);
    }

    //Cultural offer to delete does not exist;
    @Test
    public void testDeleteInvalid(){
        assertFalse(culturalOfferService.delete(DELETE_CULTURAL_OFFER_ID_FAIL));
    }

    @Test
    public void testReferenceCountBySubcategoryValid(){
        assertEquals(REFERENCE_COUNT_VALID, culturalOfferService.getCulturalOfferReferencingCount(REFERENCE_COUNT_ID_VALID));
    }

    //Subcategory doesn't belong to any cultural offer.
    @Test
    public void testReferenceCountBySubcategoryValidZeroOccurence(){
        assertEquals(0, culturalOfferService.getCulturalOfferReferencingCount(REFERENCE_COUNT_ID_VALID_ZERO));
    }

    //Subcategory doesn't exists.
    @Test
    public void testReferenceCountBySubcategoryInvalid(){
        assertEquals(0, culturalOfferService.getCulturalOfferReferencingCount(REFERENCE_COUNT_ID_INVALID));
    }

    //Filter by subcategory which exists.
    @Test
    public void testFilterSubcategoryValid(){
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        FilterDTO filter = new FilterDTO();
        filter.setParameter(FILTER_SUBCATEGORY);
        filter.setValue(FILTER_SUBCATEGORY_VALUE_FOUND);

        Page<CulturalOffer> page = culturalOfferService.filter(filter,pageable);
        assertEquals(FILTER_SUBCATEGORY_VALUE_FOUND_NUMBER, page.getNumberOfElements());
    }

    //Filter by subcategory which doesn't exist or is not in any cultural offer.
    @Test
    public void testFilterSubcategoryInvalid(){
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        FilterDTO filter = new FilterDTO();
        filter.setParameter(FILTER_SUBCATEGORY);
        filter.setValue(FILTER_CATEGORY_VALUE_NOT_FOUND);

        Page<CulturalOffer> page = culturalOfferService.filter(filter,pageable);
        assertEquals(0, page.getNumberOfElements());
    }

    //Filter by subcategory which exists.
    @Test
    public void testFilterCategoryValid(){
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        FilterDTO filter = new FilterDTO();
        filter.setParameter(FILTER_CATEGORY );
        filter.setValue(FILTER_CATEGORY_VALUE_FOUND );

        Page<CulturalOffer> page = culturalOfferService.filter(filter,pageable);
        assertEquals(FILTER_CATEGORY_VALUE_FOUND_NUMBER , page.getNumberOfElements());
    }

    //Filter by subcategory which doesn't exists.
    @Test
    public void testFilterCategoryInvalid(){
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        FilterDTO filter = new FilterDTO();
        filter.setParameter(FILTER_CATEGORY );
        filter.setValue(FILTER_CATEGORY_VALUE_NOT_FOUND   );

        Page<CulturalOffer> page = culturalOfferService.filter(filter,pageable);
        assertEquals(0 , page.getNumberOfElements());
    }

    //Filter by location which exists.
    @Test
    public void testFilterLocationValid(){
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        FilterDTO filter = new FilterDTO();
        filter.setParameter(FILTER_LOCATION);
        filter.setValue(FILTER_LOCATION_VALUE_FOUND);

        Page<CulturalOffer> page = culturalOfferService.filter(filter,pageable);
        assertEquals(FILTER_LOCATION_VALUE_FOUND_NUMBER , page.getNumberOfElements());
    }

    //Filter by location which doesn't exists.
    @Test
    public void testFilterLocationInvalid(){
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        FilterDTO filter = new FilterDTO();
        filter.setParameter(FILTER_LOCATION);
        filter.setValue(FILTER_LOCATION_VALUE_NOT_FOUND);

        Page<CulturalOffer> page = culturalOfferService.filter(filter,pageable);
        assertEquals(0 , page.getNumberOfElements());
    }

    //Filter by name which exists.
    @Test
    public void testFilterNameValid(){
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        FilterDTO filter = new FilterDTO();
        filter.setParameter(FILTER_NAME);
        filter.setValue(FILTER_NAME_VALUE_FOUND);

        Page<CulturalOffer> page = culturalOfferService.filter(filter,pageable);
        assertEquals(FILTER_NAME_VALUE_FOUND_NUMBER , page.getNumberOfElements());
    }

    //Filter by name which doesn't exists.
    @Test
    public void testFilterNameInvalid(){
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        FilterDTO filter = new FilterDTO();
        filter.setParameter(FILTER_NAME);
        filter.setValue(FILTER_NAME_VALUE_NOT_FOUND);

        Page<CulturalOffer> page = culturalOfferService.filter(filter,pageable);
        assertEquals(0 , page.getNumberOfElements());
    }

    //Filter by empty filter.
    @Test
    public void testFilterEmpty(){
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        FilterDTO filter = new FilterDTO();
        filter.setParameter(FILTER_EMPTY );
        filter.setValue(FILTER_EMPTY_FOUND);

        Page<CulturalOffer> page = culturalOfferService.filter(filter,pageable);
        assertEquals(FILTER_EMPTY_VALUE_FOUND, page.getNumberOfElements());
    }

    //Filter by invalid filter.
    @Test
    public void testFilterInvalidFilter(){
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        FilterDTO filter = new FilterDTO();
        filter.setParameter(FILTER_INVALID);
        filter.setValue(FILTER_INVALID_VALUE_FOUND);

        Page<CulturalOffer> page = culturalOfferService.filter(filter,pageable);
        assertEquals(FILTER_INVALID_VALUE_FOUND_NUMBER, page.getNumberOfElements());
    }

    //Filter by empty value any filter.
    @Test
    public void testFilterEmptyValue(){
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        FilterDTO filter = new FilterDTO();
        filter.setParameter(FILTER_CATEGORY);
        filter.setValue(FILTER_INVALID_VALUE_EMPTY);

        Page<CulturalOffer> page = culturalOfferService.filter(filter,pageable);
        assertEquals(FILTER_EMPTY_FOUND_NUMBER, page.getNumberOfElements());
    }

    //TODO: Vera test subscription here.

}
