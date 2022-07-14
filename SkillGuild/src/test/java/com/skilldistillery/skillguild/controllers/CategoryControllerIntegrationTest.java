package com.skilldistillery.skillguild.controllers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.skilldistillery.skillguild.entities.Category;
import com.skilldistillery.skillguild.repositories.CategoryRepository;
import com.skilldistillery.skillguild.services.CategoryService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CategoryControllerIntegrationTest {

	@Mock
	CategoryRepository categoryRepository;

	@Mock
	CategoryService categoryService;

	@Mock
	MockHttpServletRequest req;

	@Mock
	MockHttpServletResponse res;

	@InjectMocks
	CategoryController categoryController;

	@Test
	public void testCreateCategory() throws Exception {

		Category category = new Category();
		category.setName("Software Engineering");
		category.setDescription("Activities and interests related to the Software Engineering discipline");
		category.setImgUrl("https://images.unsplash.com/photo-1573495627361-d9b87960b12d");

		when(this.categoryService.create(any(Category.class))).thenReturn(category);

		Category newCategory = categoryController.create(req, res, category);

		System.out.println(newCategory);
		assertNotNull(newCategory.getName());
	}

}
