package com.skillguildapp.skillguild.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.skillguildapp.skillguild.entities.Category;
import com.skillguildapp.skillguild.repositories.CategoryRepository;
import com.skillguildapp.skillguild.services.CategoryServiceImpl;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class CategoryServiceUnitTest {

	@Mock
	private CategoryRepository categoryRepository;

	@InjectMocks
	private CategoryServiceImpl categoryService;

	private Category mockCategory;

	@BeforeEach
	public void setup() {
	}

	@DisplayName("Category service should create new category (unit test)")
	@Test
	public void testCreateCategory() {

		// Given
		this.mockCategory = new Category();
		this.mockCategory.setName("Software Engineering");
		this.mockCategory.setDescription("Activities and interests related to the Software Engineering discipline");
		this.mockCategory.setImgUrl("https://images.unsplash.com/photo-1573495627361-d9b87960b12d");

		when(this.categoryRepository.saveAndFlush(null)).thenReturn(mockCategory);

		// Test
		Category newCategory = categoryService.create(null);

		// Then
		assertNotNull(newCategory.getName());
		assertEquals("Software Engineering", newCategory.getName());

	}

}
