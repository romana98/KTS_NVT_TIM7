package com.project.tim7.service;

import com.project.tim7.model.Category;
import com.project.tim7.repository.CategoryRepository;
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
import static org.junit.jupiter.api.Assertions.*;
import static com.project.tim7.constants.CategoryConstants.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CategoryServiceIntegrationTest {

    @Autowired
    CategoryRepository categoryRepo;

    @Autowired
    SubcategoryService subcategoryService;

    @Autowired
    CategoryService categoryService;

    @Test
    public void testFindAll(){
        List<Category> found = categoryService.findAll();
        assertEquals(SERVICE_COUNT_CATEGORIES_ALL, found.size());
    }

    @Test
    public void testFindAllPageable(){
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
        Page<Category> found = categoryService.findAll(pageable);

        assertEquals(SERVICE_COUNT_CATEGORIES_ALL, found.getNumberOfElements());
    }

    @Test
    public void testFindOneValid(){
        Category found = categoryService.findOne(SERVICE_VALID_CATEGORY_ID);
        assertEquals(SERVICE_VALID_CATEGORY_ID, found.getId());
    }

    @Test
    public void testFindOneInvalid(){
        Category found = categoryService.findOne(SERVICE_INVALID_CATEGORY_ID);
        assertNull(found);
    }

    @Test
    public void testSaveOneValid(){
        Category newCategory = new Category();
        newCategory.setName(SERVICE_NEW_VALID_CATEGORY_NAME);
        Category saved = categoryService.saveOne(newCategory);
        assertEquals(SERVICE_NEW_VALID_CATEGORY_NAME, saved.getName());

        categoryService.delete(saved.getId());
    }

    @Test
    public void testSaveOneInvalid(){
        Category newCategory = new Category();
        newCategory.setName(SERVICE_NEW_INVALID_CATEGORY_NAME);
        Category saved = categoryService.saveOne(newCategory);
        assertNull(saved);
    }

    @Test
    @Transactional
    @Rollback
    public void testDeleteValid(){
        boolean deleted = categoryService.delete(SERVICE_VALID_CATEGORY_ID);

        assertTrue(deleted);
    }

    @Test
    public void testDeleteInvalidId(){
        boolean deleted = categoryService.delete(SERVICE_INVALID_CATEGORY_ID);

        assertFalse(deleted);
    }

    @Test
    public void testDeleteInvalidReferencing(){
        boolean deleted = categoryService.delete(SERVICE_INVALID_CATEGORY_ID_REFERENCING);

        assertFalse(deleted);
    }

    @Test
    public void testUpdateValid(){
        Category updatedCategory = new Category();
        updatedCategory.setId(SERVICE_VALID_CATEGORY_ID);
        updatedCategory.setName(SERVICE_NEW_VALID_CATEGORY_NAME);
        Category update = categoryService.update(updatedCategory);
        assertEquals(SERVICE_VALID_CATEGORY_ID, update.getId());
        assertEquals(SERVICE_NEW_VALID_CATEGORY_NAME, update.getName());

        Category categoryRestore = new Category();
        categoryRestore.setId(SERVICE_VALID_CATEGORY_ID);
        categoryRestore.setName(SERVICE_OLD_CATEGORY_NAME);
        categoryService.update(categoryRestore);

    }

    @Test
    public void testUpdateInvalidNameValidId(){
        Category updatedCategory = new Category();
        updatedCategory.setId(SERVICE_VALID_CATEGORY_ID);
        updatedCategory.setName(SERVICE_NEW_INVALID_CATEGORY_NAME);
        Category update = categoryService.update(updatedCategory);
        assertNull(update);
    }

    @Test
    public void testUpdateInvalidIdValidName(){
        Category updatedCategory = new Category();
        updatedCategory.setId(SERVICE_INVALID_CATEGORY_ID);
        updatedCategory.setName(SERVICE_NEW_VALID_CATEGORY_NAME);
        Category update = categoryService.update(updatedCategory);
        assertNull(update);
    }

    @Test
    public void testUpdateInvalidIdInvalidName(){
        Category updatedCategory = new Category();
        updatedCategory.setId(SERVICE_INVALID_CATEGORY_ID);
        updatedCategory.setName(SERVICE_NEW_INVALID_CATEGORY_NAME);
        Category update = categoryService.update(updatedCategory);
        assertNull(update);
    }

}
