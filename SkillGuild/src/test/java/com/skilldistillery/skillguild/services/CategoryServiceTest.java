package com.skilldistillery.skillguild.services;


import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.skilldistillery.skillguild.entities.Category;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class CategoryServiceTest {

	@Autowired
	private CategoryService categoryService;
	
	@Test
	public void testCreateCategory() {
		
		Category category = new Category();
		category.setName("Software Engineering");
		category.setDescription("Activities and interests related to the Software Engineering discipline");
		
		Category newCategory = categoryService.create(category);
		
		assertNotNull(newCategory);
		assertNotNull(newCategory.getId());
		assertEquals("Software Engineering", newCategory.getName());
	}
}
