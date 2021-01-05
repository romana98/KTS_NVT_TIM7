package com.project.tim7.repository;

import com.project.tim7.model.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import static com.project.tim7.constants.CategoryConstants.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CategoryRepositoryIntegrationTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    public void testFindByNameFound(){
        Category found = categoryRepository.findByName(REPO_CATEGORY_NAME_FIND);
        assertEquals(REPO_CATEGORY_NAME_FIND, found.getName());
    }

    @Test
    public void testFindByNameNotFound(){
        Category found = categoryRepository.findByName(REPO_CATEGORY_NAME_NOT_FOUND);
        assertNull(found);
    }
    
    @Test
    public void testFindSubscribedCategories(){
        List<Category> found = categoryRepository.findSubscribedCategories(USER_ID_SUBSCRIBED);
        assertEquals(SUBSCRIBED_CATEGORIES, found.size());
    }

}
