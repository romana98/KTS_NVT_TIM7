package com.project.tim7.service;

import com.project.tim7.constants.CategoryConstants;
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
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.size());
    }

    @Test
    public void testFindAllPageable(){
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
        Page<Category> found = categoryService.findAll(pageable);

        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.getNumberOfElements());
    }

    //findOne
    @Test
    public void testFindOneValid(){
        Category found = categoryService.findOne(VALID_CATEGORY_ID);
        assertEquals(VALID_CATEGORY_ID, found.getId());
    }

    @Test
    public void testFindOneInvalid(){
        Category found = categoryService.findOne(INVALID_CATEGORY_ID);
        assertNull(found);
    }

    //saveOne
    @Test
    public void testSaveOneValid(){
        Category newCategory = new Category();
        newCategory.setName(NEW_VALID_CATEGORY_NAME);
        Category saved = categoryService.saveOne(newCategory);
        assertEquals(saved.getName(), NEW_VALID_CATEGORY_NAME);

        categoryService.delete(saved.getId());
    }

    @Test
    public void testSaveOneInvalid(){
        Category newCategory = new Category();
        newCategory.setName(NEW_INVALID_CATEGORY_NAME);
        Category saved = categoryService.saveOne(newCategory);
        assertNull(saved);
    }

    //delete
    @Test
    @Transactional
    @Rollback
    public void testDeleteValid(){
        boolean deleted = categoryService.delete(VALID_CATEGORY_ID);

        assertTrue(deleted);
    }

    @Test
    public void testDeleteInvalidId(){
        boolean deleted = categoryService.delete(INVALID_CATEGORY_ID);

        assertFalse(deleted);
    }

    @Test
    public void testDeleteInvalidReferencing(){
        boolean deleted = categoryService.delete(INVALID_CATEGORY_REFERENCING);

        assertFalse(deleted);
    }

    //update
    @Test
    public void testUpdateValid(){
        Category updatedCategory = new Category();
        updatedCategory.setId(VALID_CATEGORY_ID);
        updatedCategory.setName(NEW_VALID_CATEGORY_NAME);
        Category update = categoryService.update(updatedCategory);
        assertEquals(update.getId(), VALID_CATEGORY_ID);
        assertEquals(update.getName(), NEW_VALID_CATEGORY_NAME);

        Category categoryRestore = new Category();
        categoryRestore.setId(VALID_CATEGORY_ID);
        categoryRestore.setName(OLD_CATEGORY_NAME);
        categoryService.update(categoryRestore);

    }

    @Test
    public void testUpdateInvalidNameValidId(){
        Category updatedCategory = new Category();
        updatedCategory.setId(VALID_CATEGORY_ID);
        updatedCategory.setName(NEW_INVALID_CATEGORY_NAME);
        Category update = categoryService.update(updatedCategory);
        assertNull(update);
    }

    @Test
    public void testUpdateInvalidIdValidName(){
        Category updatedCategory = new Category();
        updatedCategory.setId(INVALID_CATEGORY_ID);
        updatedCategory.setName(NEW_VALID_CATEGORY_NAME);
        Category update = categoryService.update(updatedCategory);
        assertNull(update);
    }

    @Test
    public void testUpdateInvalidIdInvalidName(){
        Category updatedCategory = new Category();
        updatedCategory.setId(INVALID_CATEGORY_ID);
        updatedCategory.setName(NEW_INVALID_CATEGORY_NAME);
        Category update = categoryService.update(updatedCategory);
        assertNull(update);
    }

}
