package com.skilldistillery.skillguild.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import com.skilldistillery.skillguild.entities.Category;
import com.skilldistillery.skillguild.services.CategoryService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CategoryControllerUnitTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CategoryService categoryService;

	@Mock
	MockHttpServletRequest req;

	@Mock
	MockHttpServletResponse res;

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

	@DisplayName("When calling show, return Category by id")
	@Test
	void getShow_shouldReturn_CategoryById() throws Exception {
		// given
		Category category = new Category();
		category.setId(1);
		category.setName("Software Engineering");
		category.setDescription("Activities and interests related to the Software Engineering discipline");
		category.setImgUrl("https://images.unsplash.com/photo-1573495627361-d9b87960b12d");
		when(categoryService.show(any(Integer.class))).thenReturn(category);

		// when
		mockMvc.perform(get("/v1/categories/1").contentType("text/plain")).andDo(print()).andReturn();

		// then
		verify(categoryService, times(1)).show(any(Integer.class));
	}
}
