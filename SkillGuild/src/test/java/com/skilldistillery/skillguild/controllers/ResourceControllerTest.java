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
import com.skilldistillery.skillguild.entities.Resource;
import com.skilldistillery.skillguild.services.ResourceService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ResourceControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ResourceService resourceService;

	@Mock
	private MockHttpServletRequest req;

	@Mock
	private MockHttpServletResponse res;
	
	@Mock
	private Resource resource;

	@DisplayName("When calling index, return list of Resources")
	@Test
	void getIndex_shouldReturn_ListResources() throws Exception {
		// given
		List<Resource> resources = new ArrayList<>();
		resources.add(new Resource());
		resources.add(new Resource());
		resources.add(new Resource());
		when(resourceService.index()).thenReturn(resources);

		// when
		mockMvc.perform(get("/v1/resources")).andReturn();

		// then
		verify(resourceService, times(1)).index();
		verifyNoMoreInteractions(resourceService);
	}

	@DisplayName("When calling show, return Resource by id")
	@Test
	void getShow_shouldReturn_ResourceById() throws Exception {
		// given
		Resource resource = new Resource();
		resource.setId(1);
		resource.setTitle("Software Engineering");
		resource.setDescription("Activities and interests related to the Software Engineering discipline");
		resource.setResourceUrl("https://images.unsplash.com/photo-1573495627361-d9b87960b12d");
		when(resourceService.show(any(Integer.class))).thenReturn(resource);

		// when
		mockMvc.perform(get("/v1/resources/1").contentType("text/plain")).andDo(print()).andReturn();

		// then
		verify(resourceService, times(1)).show(any(Integer.class));
		verifyNoMoreInteractions(resourceService);
	}

	@DisplayName("When post to resources, create and return Resource")
	@Test
	void whenPostResource_thenCreateAndReturnResource() throws Exception {
		// given
		Resource resource = new Resource();
		resource.setId(1);
		resource.setTitle("Software Architecture");
		resource.setDescription("Activities and interests related to the Software Architecture discipline");
		resource.setResourceUrl("https://images.unsplash.com/photo-1573495627361-d9b87960b12d");
		when(resourceService.create(any(Integer.class), any(Resource.class))).thenReturn(resource);

		// when
		MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json",
				java.nio.charset.Charset.forName("UTF-8"));
		mockMvc.perform(post("/v1/resources/1").accept(MEDIA_TYPE_JSON_UTF8).content(convertObjectToJsonBytes(resource))
				.contentType(MEDIA_TYPE_JSON_UTF8)).andExpect(status().isCreated()).andDo(print()).andReturn();

		// then
		verify(resourceService, times(1)).create(any(Integer.class), any(Resource.class));
		verifyNoMoreInteractions(resourceService);
	}

	@DisplayName("When delete resource by ID, delete and return boolean")
	@Test
	void wheDeleteResourceById_thenDeleteAndReturnBoolean() throws Exception {
		// given
		when(resourceService.delete(any(Integer.class))).thenReturn(true);

		// when
		mockMvc.perform(delete("/v1/resources/1")).andDo(print()).andReturn();

		// then
		verify(resourceService, times(1)).delete(any(Integer.class));
		verifyNoMoreInteractions(resourceService);
	}

	@DisplayName("When updating resource by ID, update and return Resource")
	@Test
	void whenPutResource_thenUpdateAndReturnResource() throws Exception {
		// given
		Resource resource = new Resource();
		resource.setId(1);
		resource.setTitle("Data Engineering");
		resource.setDescription("Activities and interests related to the Data Engineering discipline");
		resource.setResourceUrl("https://images.unsplash.com/photo-1573495627361-d9b87960b12d");
		when(resourceService.update(any(Integer.class), any(Resource.class))).thenReturn(resource);

		// when
		MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json",
				java.nio.charset.Charset.forName("UTF-8"));
		mockMvc.perform(put("/v1/resources/1").accept(MEDIA_TYPE_JSON_UTF8).content(convertObjectToJsonBytes(resource))
				.contentType(MEDIA_TYPE_JSON_UTF8)).andDo(print()).andReturn();

		// then
		verify(resourceService, times(1)).update(any(Integer.class), any(Resource.class));
		verifyNoMoreInteractions(resourceService);
	}

	private static byte[] convertObjectToJsonBytes(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsBytes(object);
	}

}
