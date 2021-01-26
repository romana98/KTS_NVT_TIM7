package com.project.tim7;

import com.project.tim7.e2eTests.e2e.*;
import com.project.tim7.e2eTests.pages.CulturalOfferDashboardPage;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.test.context.TestPropertySource;

import com.project.tim7.api.AdministratorControllerIntegrationTest;
import com.project.tim7.api.AuthenticationControllerIntegrationTest;
import com.project.tim7.api.CategoryControllerIntegrationTest;
import com.project.tim7.api.CommentControllerIntegrationTest;
import com.project.tim7.api.CulturalOfferControllerIntegrationTest;
import com.project.tim7.api.LocationControllerIntegrationTest;
import com.project.tim7.api.NewsletterControllerIntegrationTest;
import com.project.tim7.api.RatingControllerIntegrationTest;
import com.project.tim7.api.RegisteredControllerIntegrationTest;
import com.project.tim7.api.SubcategoryControllerIntegrationTest;
import com.project.tim7.repository.AdministratorRepositoryIntegrationTest;
import com.project.tim7.repository.CategoryRepositoryIntegrationTest;
import com.project.tim7.repository.CommentRepositoryIntegrationTest;
import com.project.tim7.repository.CulturalOfferRepositoryIntegrationTest;
import com.project.tim7.repository.LocationRepositoryIntegrationTest;
import com.project.tim7.repository.NewsletterRepositoryIntegrationTest;
import com.project.tim7.repository.PersonRepositoryIntegrationTest;
import com.project.tim7.repository.PictureRepositoryIntegrationTest;
import com.project.tim7.repository.RatingRepositoryIntegrationTest;
import com.project.tim7.repository.RegisteredRepositoryIntegrationTest;
import com.project.tim7.repository.SubcategoryRepositoryIntegrationTest;
import com.project.tim7.service.AdministratorServiceIntegrationTest;
import com.project.tim7.service.AdministratorServiceUnitTest;
import com.project.tim7.service.AuthorityServiceIntegrationTest;
import com.project.tim7.service.CategoryServiceIntegrationTest;
import com.project.tim7.service.CommentServiceIntegrationTest;
import com.project.tim7.service.CulturalOfferServiceIntegrationTest;
import com.project.tim7.service.CustomUserDetailsServiceIntegrationService;
import com.project.tim7.service.EmailServiceTest;
import com.project.tim7.service.LocationServiceIntegrationTest;
import com.project.tim7.service.NewsletterServiceIntegrationTest;
import com.project.tim7.service.NewsletterServiceUnitTest;
import com.project.tim7.service.PictureServiceIntegrationTest;
import com.project.tim7.service.RatingServiceIntegrationTest;
import com.project.tim7.service.RegisteredServiceIntegrationTest;
import com.project.tim7.service.SubcategoryServiceIntegrationTest;

@RunWith(Suite.class)
@SuiteClasses({
        AdministratorRepositoryIntegrationTest.class, CategoryRepositoryIntegrationTest.class,
        CommentRepositoryIntegrationTest.class, CulturalOfferRepositoryIntegrationTest.class,
        LocationRepositoryIntegrationTest.class, NewsletterRepositoryIntegrationTest.class, PersonRepositoryIntegrationTest.class,
        PictureRepositoryIntegrationTest.class, RatingRepositoryIntegrationTest.class, RegisteredRepositoryIntegrationTest.class, 
        SubcategoryRepositoryIntegrationTest.class,
        AdministratorServiceIntegrationTest.class, AdministratorServiceUnitTest.class, AuthorityServiceIntegrationTest.class,
        CategoryServiceIntegrationTest.class, CommentServiceIntegrationTest.class, CulturalOfferServiceIntegrationTest.class,
        CustomUserDetailsServiceIntegrationService.class, EmailServiceTest.class, LocationServiceIntegrationTest.class, 
        NewsletterServiceIntegrationTest.class, NewsletterServiceUnitTest.class, PictureServiceIntegrationTest.class,
        RatingServiceIntegrationTest.class, RegisteredServiceIntegrationTest.class, 
        SubcategoryServiceIntegrationTest.class, CulturalOfferControllerIntegrationTest.class,
        AdministratorControllerIntegrationTest.class, AuthenticationControllerIntegrationTest.class,
        CategoryControllerIntegrationTest.class, CommentControllerIntegrationTest.class,
       LocationControllerIntegrationTest.class, NewsletterControllerIntegrationTest.class, RatingControllerIntegrationTest.class,
        RegisteredControllerIntegrationTest.class, SubcategoryControllerIntegrationTest.class})
@TestPropertySource("classpath:test.properties")
public class SuiteAll {


}
