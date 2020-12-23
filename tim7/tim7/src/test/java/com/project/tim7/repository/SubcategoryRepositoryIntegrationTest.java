package com.project.tim7.repository;

import com.project.tim7.model.Subcategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import static com.project.tim7.constants.SubcategoryConstants.*;
import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class SubcategoryRepositoryIntegrationTest {

    @Autowired
    SubcategoryRepository subcategoryRepository;

    @Test
    public void testFindByName(){
        Subcategory found = subcategoryRepository.findByName(REPO_SUBCATEGORY_NAME_FIND);
        assertEquals(REPO_SUBCATEGORY_NAME_FIND, found.getName());
    }

    @Test
    public void testFindByNameNotFound(){
        Subcategory found = subcategoryRepository.findByName(REPO_SUBCATEGORY_NAME_NOT_FOUND);
        assertNull(found);
    }

    @Test
    public void testCountByCategoryId(){
        long count = subcategoryRepository.countByCategoryId(REPO_REFERENCING_CATEGORY_ID);
        assertEquals(REPO_COUNT_REFERENCING_CATEGORIES, count);
    }

    @Test
    public void testCountByCategoryIdNone(){
        long count = subcategoryRepository.countByCategoryId(REPO_NOT_REFERENCING_CATEGORY_ID);
        assertEquals(0, count);
    }

    @Test
    public void testFindByCategoryId(){
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        Page<Subcategory> found = subcategoryRepository.findByCategoryId(REPO_REFERENCING_CATEGORY_ID, pageable);
        assertEquals(REPO_COUNT_REFERENCING_CATEGORIES, found.getTotalElements());
    }

    @Test
    public void testFindByCategoryIdEmpty(){
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        Page<Subcategory> found = subcategoryRepository.findByCategoryId(REPO_NOT_REFERENCING_CATEGORY_ID, pageable);
        assertEquals(0, found.getTotalElements());
    }
}
