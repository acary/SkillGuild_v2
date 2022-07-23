package com.skilldistillery.skillguild.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
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
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skilldistillery.skillguild.entities.Category;
import com.skilldistillery.skillguild.services.CategoryService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CategoryControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CategoryService categoryService;

	@Mock
	private MockHttpServletRequest req;

	@Mock
	private MockHttpServletResponse res;

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
		verifyNoMoreInteractions(categoryService);
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
		verifyNoMoreInteractions(categoryService);
	}

	@DisplayName("When post to categories, create and return Category")
	@Test
	void whenPostCategory_thenCreateAndReturnCategory() throws Exception {
		// given
		Category category = new Category();
		category.setId(1);
		category.setName("Software Architecture");
		category.setDescription("Activities and interests related to the Software Architecture discipline");
		category.setImgUrl("https://images.unsplash.com/photo-1573495627361-d9b87960b12d");
		when(categoryService.create(any(Category.class))).thenReturn(category);

		// when
		MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json",
				java.nio.charset.Charset.forName("UTF-8"));
		mockMvc.perform(post("/v1/categories").accept(MEDIA_TYPE_JSON_UTF8).content(convertObjectToJsonBytes(category))
				.contentType(MEDIA_TYPE_JSON_UTF8)).andExpect(status().isCreated()).andDo(print()).andReturn();

		// then
		verify(categoryService, times(1)).create(any(Category.class));
		verifyNoMoreInteractions(categoryService);
	}

	@DisplayName("When delete category by ID, delete and return boolean")
	@Test
	void wheDeleteCategoryById_thenDeleteAndReturnBoolean() throws Exception {
		// given
		when(categoryService.delete(any(Integer.class))).thenReturn(true);

		// when
		mockMvc.perform(delete("/v1/categories/1")).andDo(print()).andReturn();

		// then
		verify(categoryService, times(1)).delete(any(Integer.class));
		verifyNoMoreInteractions(categoryService);
	}

	@DisplayName("When updating category by ID, update and return Category")
	@Test
	void whenPutCategory_thenUpdateAndReturnCategory() throws Exception {
		// given
		Category category = new Category();
		category.setId(1);
		category.setName("Data Engineering");
		category.setDescription("Activities and interests related to the Data Engineering discipline");
		category.setImgUrl("https://images.unsplash.com/photo-1573495627361-d9b87960b12d");
		when(categoryService.update(any(Integer.class), any(Category.class))).thenReturn(category);

		// when
		MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json",
				java.nio.charset.Charset.forName("UTF-8"));
		mockMvc.perform(put("/v1/categories/1").accept(MEDIA_TYPE_JSON_UTF8).content(convertObjectToJsonBytes(category))
				.contentType(MEDIA_TYPE_JSON_UTF8)).andDo(print()).andReturn();

		// then
		verify(categoryService, times(1)).update(any(Integer.class), any(Category.class));
		verifyNoMoreInteractions(categoryService);
	}

	private static byte[] convertObjectToJsonBytes(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsBytes(object);
	}

}
