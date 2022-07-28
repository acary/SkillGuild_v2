package com.skillguildapp.skillguild.controllers;

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
import com.skillguildapp.skillguild.entities.ResourceType;
import com.skillguildapp.skillguild.services.ResourceTypeService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ResourceTypeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ResourceTypeService resourceTypeService;

	@Mock
	private MockHttpServletRequest req;

	@Mock
	private MockHttpServletResponse res;

	@Mock
	private ResourceType resourceType;

	@DisplayName("When calling index, return list of ResourceTypes")
	@Test
	void getIndex_shouldReturn_ListResourceTypes() throws Exception {
		// given
		List<ResourceType> resourceTypes = new ArrayList<>();
		resourceTypes.add(new ResourceType());
		resourceTypes.add(new ResourceType());
		resourceTypes.add(new ResourceType());
		when(resourceTypeService.index()).thenReturn(resourceTypes);

		// when
		mockMvc.perform(get("/v1/resourcetypes")).andReturn();

		// then
		verify(resourceTypeService, times(1)).index();
		verifyNoMoreInteractions(resourceTypeService);
	}

	@DisplayName("When calling show, return ResourceType by id")
	@Test
	void getShow_shouldReturn_ResourceTypeById() throws Exception {
		// given
		ResourceType resourceType = new ResourceType();
		resourceType.setId(1);
		resourceType.setName("Video");
		resourceType.setDescription("Presentation");
		when(resourceTypeService.show(any(Integer.class))).thenReturn(resourceType);

		// when
		mockMvc.perform(get("/v1/resourcetypes/1").contentType("text/plain")).andDo(print()).andReturn();

		// then
		verify(resourceTypeService, times(1)).show(any(Integer.class));
		verifyNoMoreInteractions(resourceTypeService);
	}

	@DisplayName("When post to resourceTypes, create and return ResourceType")
	@Test
	void whenPostResourceType_thenCreateAndReturnResourceType() throws Exception {
		// given
		ResourceType resourceType = new ResourceType();
		resourceType.setId(1);
		resourceType.setName("Slides");
		resourceType.setDescription("Presentation");
		when(resourceTypeService.create(any(ResourceType.class))).thenReturn(resourceType);

		// when
		MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json",
				java.nio.charset.Charset.forName("UTF-8"));
		mockMvc.perform(post("/v1/resourcetypes").accept(MEDIA_TYPE_JSON_UTF8).content(convertObjectToJsonBytes(resourceType))
				.contentType(MEDIA_TYPE_JSON_UTF8)).andDo(print()).andReturn();

		// then
		verify(resourceTypeService, times(1)).create(any(ResourceType.class));
		verifyNoMoreInteractions(resourceTypeService);
	}

	@DisplayName("When delete resourceType by ID, delete and return boolean")
	@Test
	void wheDeleteResourceTypeById_thenDeleteAndReturnBoolean() throws Exception {
		// given
		when(resourceTypeService.delete(any(Integer.class))).thenReturn(true);

		// when
		mockMvc.perform(delete("/v1/resourcetypes/1")).andDo(print()).andReturn();

		// then
		verify(resourceTypeService, times(1)).delete(any(Integer.class));
		verifyNoMoreInteractions(resourceTypeService);
	}

	@DisplayName("When updating resourceType by ID, update and return ResourceType")
	@Test
	void whenPutResourceType_thenUpdateAndReturnResourceType() throws Exception {
		// given
		ResourceType resourceType = new ResourceType();
		resourceType.setId(1);
		resourceType.setName("Article");
		resourceType.setDescription("Guide");
		when(resourceTypeService.update(any(Integer.class), any(ResourceType.class))).thenReturn(resourceType);

		// when
		MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json",
				java.nio.charset.Charset.forName("UTF-8"));
		mockMvc.perform(put("/v1/resourcetypes/1").accept(MEDIA_TYPE_JSON_UTF8).content(convertObjectToJsonBytes(resourceType))
				.contentType(MEDIA_TYPE_JSON_UTF8)).andDo(print()).andReturn();

		// then
		verify(resourceTypeService, times(1)).update(any(Integer.class), any(ResourceType.class));
		verifyNoMoreInteractions(resourceTypeService);
	}

	private static byte[] convertObjectToJsonBytes(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsBytes(object);
	}

}
