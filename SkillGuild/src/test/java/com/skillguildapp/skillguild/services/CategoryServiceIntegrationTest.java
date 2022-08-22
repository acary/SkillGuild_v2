package com.skillguildapp.skillguild.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.skillguildapp.skillguild.entities.Category;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class CategoryServiceIntegrationTest {

	@Autowired
	private CategoryService categoryService;

	@DisplayName("Category service should create new category (integration test)")
	@Test
	public void testCreateCategory() {

		Category category = new Category();
		category.setName("Software Engineering");
		category.setDescription("Activities and interests related to the Software Engineering discipline");
		category.setImgUrl("https://images.unsplash.com/photo-1573495627361-d9b87960b12d");

		Category newCategory = categoryService.create(category);

		assertNotNull(newCategory);
		assertEquals(true, newCategory.getId() > 0);
		assertEquals("Software Engineering", newCategory.getName());
	}
}
