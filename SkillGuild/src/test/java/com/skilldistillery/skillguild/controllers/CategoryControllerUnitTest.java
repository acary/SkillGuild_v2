package com.skilldistillery.skillguild.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.skilldistillery.skillguild.entities.Category;
import com.skilldistillery.skillguild.services.CategoryService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerUnitTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CategoryService categoryService;

	@DisplayName("When calling index, return list of Categories")
	@Test
	void getIndex_shouldReturn_ListCategories() throws Exception {
		// given
		List<Category> categories = new ArrayList<>();
		categories.add(new Category());
		categories.add(new Category());
		categories.add(new Category());
		when(categoryService.index()).thenReturn(categories);

		// when
		mockMvc.perform(get("/v1/categories")).andReturn();

		// then
		verify(categoryService, times(1)).index();
	}
}
