package com.skillguildapp.skillguild.repositories;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.skillguildapp.skillguild.entities.Category;
import com.skillguildapp.skillguild.repositories.CategoryRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class CategoryRepositoryTest {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private TestEntityManager testEntityManager;

	private Category newCategory;

	@Test
	public void testGetCategoryByName_returnsCategoryDetails() {
		
		// given
		this.newCategory = new Category();
		this.newCategory.setName("Software Architecture");
		this.newCategory.setDescription("Activities and interests related to the Software Architecture discipline");
		this.newCategory.setImgUrl("https://images.unsplash.com/photo-1573495627361-d9b87960b12d");
		Category savedCategory = testEntityManager.persistFlushFind(newCategory);
		
		// when
		Category retrievedCategory = categoryRepository.getCategoryByName("Software Architecture");
		
		// then
		then(!retrievedCategory.getName().isEmpty());
		assertEquals(retrievedCategory.getName(), "Software Architecture");
		
	}
}
