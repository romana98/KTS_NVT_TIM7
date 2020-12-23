package com.project.tim7.service;

import com.project.tim7.model.Category;
import com.project.tim7.model.Subcategory;
import com.project.tim7.repository.SubcategoryRepository;
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

import java.util.List;

import static com.project.tim7.constants.SubcategoryConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class SubcategoryServiceIntegrationTest {

    @Autowired
    SubcategoryRepository subcategoryRepo;

    @Autowired
    CategoryService categoryService;

    @Autowired
    CulturalOfferService culturalOfferService;

    @Autowired
    SubcategoryService subcategoryService;

    @Test
    public void testFindAll(){

        List<Subcategory> found = subcategoryService.findAll();
        assertEquals(found.size(), SUBCATEGORIES_COUNT);

    }

    @Test
    public void testFindAllPageable(){
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        Page<Subcategory> found = subcategoryService.findAll(pageable);
        assertEquals(SUBCATEGORIES_COUNT, found.getTotalElements());
    }

    @Test
    public void testFindOneFound(){
        Subcategory found = subcategoryService.findOne(EXIST_SUBCATEGORY_ID);
        assertEquals(found.getId(), EXIST_SUBCATEGORY_ID);
    }

    @Test
    public void testFindOneNotFound(){
        Subcategory found = subcategoryService.findOne(NONEXIST_SUBCATEGORY_ID);
        assertNull(found);
    }

    @Test
    public void testSaveOneValid(){
        Subcategory subcategory = new Subcategory();
        subcategory.setName(NEW_VALID_SUBCATEGORY_NAME);
        subcategory.setCategory(new Category(EXIST_CATEGORY_ID));
        Subcategory saved = subcategoryService.saveOne(subcategory);
        assertEquals(NEW_VALID_SUBCATEGORY_NAME, saved.getName());

        subcategoryService.delete(saved.getId());
    }

    @Test
    public void testSaveOneInvalidName(){
        Subcategory subcategory = new Subcategory();
        subcategory.setName(NEW_INVALID_SUBCATEGORY_NAME);
        subcategory.setCategory(new Category(EXIST_CATEGORY_ID));
        Subcategory saved = subcategoryService.saveOne(subcategory);
        assertNull(saved);
    }

    @Test
    public void testSaveOneInvalidCategoryId(){
        Subcategory subcategory = new Subcategory();
        subcategory.setCategory(new Category(NONEXIST_CATEGORY_ID));
        subcategory.setName(NEW_VALID_SUBCATEGORY_NAME);
        Subcategory saved = subcategoryService.saveOne(subcategory);
        assertNull(saved);
    }

    @Test
    public void testSaveOneInvalidNameInvalidCategoryId(){
        Subcategory subcategory = new Subcategory();
        subcategory.setCategory(categoryService.findOne(NONEXIST_CATEGORY_ID));
        subcategory.setName(NEW_INVALID_SUBCATEGORY_NAME);
        Subcategory saved = subcategoryService.saveOne(subcategory);
        assertNull(saved);
    }

    @Test
    @Transactional
    @Rollback
    public void testDeleteValid(){

        boolean deleted = subcategoryService.delete(EXIST_SUBCATEGORY_ID);
        assertTrue(deleted);

    }

    @Test
    public void testDeleteInvalidId(){
        boolean deleted = subcategoryService.delete(NONEXIST_SUBCATEGORY_ID);
        assertFalse(deleted);
    }

    @Test
    public void testDeleteInvalidReferencing(){
        boolean deleted = subcategoryService.delete(REFERENCING_SUBCATEGORY_ID);
        assertFalse(deleted);
    }

    //update
    @Test
    public void testUpdateValid(){
        Subcategory subcategory = new Subcategory();
        subcategory.setName(NEW_VALID_SUBCATEGORY_NAME);
        subcategory.setCategory(new Category(EXIST_CATEGORY_ID));
        subcategory.setId(EXIST_SUBCATEGORY_ID);
        Subcategory updated = subcategoryService.update(subcategory);
        assertEquals(updated.getId(), EXIST_SUBCATEGORY_ID);
        assertEquals(NEW_VALID_SUBCATEGORY_NAME, updated.getName());

        Subcategory subcategoryOld = new Subcategory();
        subcategoryOld.setId(EXIST_SUBCATEGORY_ID);
        subcategoryOld.setName(OLD_SUBCATEGORY_NAME);
        subcategoryOld.setCategory(new Category(EXIST_CATEGORY_ID));
        subcategoryService.update(subcategoryOld);

    }

    @Test
    public void testUpdateInvalidId(){
        Subcategory subcategory = new Subcategory();
        subcategory.setName(NEW_VALID_SUBCATEGORY_NAME);
        subcategory.setCategory(new Category(EXIST_CATEGORY_ID));
        subcategory.setId(NONEXIST_SUBCATEGORY_ID);
        Subcategory updated = subcategoryService.update(subcategory);
        assertNull(updated);
    }

    @Test
    public void testUpdateInvalidCategory(){
        Subcategory subcategory = new Subcategory();
        subcategory.setName(NEW_VALID_SUBCATEGORY_NAME);
        subcategory.setCategory(new Category(NONEXIST_CATEGORY_ID));
        subcategory.setId(EXIST_SUBCATEGORY_ID);
        Subcategory updated = subcategoryService.update(subcategory);
        assertNull(updated);
    }

    @Test
    public void testUpdateInvalidName(){
        Subcategory subcategory = new Subcategory();
        subcategory.setName(NEW_INVALID_SUBCATEGORY_NAME);
        subcategory.setCategory(new Category(EXIST_CATEGORY_ID));
        subcategory.setId(EXIST_SUBCATEGORY_ID);
        Subcategory updated = subcategoryService.update(subcategory);
        assertNull(updated);
    }

    @Test
    public void testGetSubcategoriesReferencingCountNotEmpty(){
        long count = subcategoryService.getSubcategoriesReferencingCount(REFERENCING_CATEGORY_ID);
        assertEquals(count, COUNT_REFERENCING_CATEGORIES);
    }

    @Test
    public void testGetSubcategoriesReferencingCountEmpty(){
        long count = subcategoryService.getSubcategoriesReferencingCount(NOT_REFERENCING_CATEGORY_ID);
        assertEquals(count, 0);
    }

    @Test
    public void testFindSubcategoriesByCategoryNotNull(){
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        Page<Subcategory> found = subcategoryService.findSubcategoriesByCategory(REFERENCING_CATEGORY_ID, pageable);
        assertEquals(COUNT_REFERENCING_CATEGORIES, found.getTotalElements());
    }

    @Test
    public void testFindSubcategoriesByCategoryNull(){
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        Page<Subcategory> found = subcategoryService.findSubcategoriesByCategory(NONEXIST_CATEGORY_ID, pageable);
        assertEquals(0, found.getTotalElements());
    }

}
