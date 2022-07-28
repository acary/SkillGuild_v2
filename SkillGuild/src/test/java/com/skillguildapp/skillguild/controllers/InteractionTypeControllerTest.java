package com.skillguildapp.skillguild.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import com.skillguildapp.skillguild.entities.InteractionType;
import com.skillguildapp.skillguild.services.InteractionTypeService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class InteractionTypeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private InteractionTypeService interactionTypeService;

	@Mock
	private MockHttpServletRequest req;

	@Mock
	private MockHttpServletResponse res;

	@Mock
	private InteractionType interactionType;

	@DisplayName("When calling index, return list of InteractionTypes")
	@Test
	void getIndex_shouldReturn_ListInteractionTypes() throws Exception {
		// given
		List<InteractionType> interactionTypes = new ArrayList<>();
		interactionTypes.add(new InteractionType());
		interactionTypes.add(new InteractionType());
		interactionTypes.add(new InteractionType());
		when(interactionTypeService.index()).thenReturn(interactionTypes);

		// when
		mockMvc.perform(get("/v1/interactiontypes")).andReturn();

		// then
		verify(interactionTypeService, times(1)).index();
		verifyNoMoreInteractions(interactionTypeService);
	}

	@DisplayName("When post to interactiontypes, create and return InteractionType")
	@Test
	void whenPostInteractionType_thenCreateAndReturnInteractionType() throws Exception {
		// given
		InteractionType interactionType = new InteractionType();
		interactionType.setId(1);
		interactionType.setName("Agree");
		when(interactionTypeService.create(any(InteractionType.class))).thenReturn(interactionType);

		// when
		MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json",
				java.nio.charset.Charset.forName("UTF-8"));
		mockMvc.perform(post("/v1/interactiontypes/1").accept(MEDIA_TYPE_JSON_UTF8)
				.content(convertObjectToJsonBytes(interactionType)).contentType(MEDIA_TYPE_JSON_UTF8)).andDo(print())
				.andReturn();

		// then
		verify(interactionTypeService, times(1)).create(any(InteractionType.class));
		verifyNoMoreInteractions(interactionTypeService);
	}

	@DisplayName("When delete interactiontype by ID, delete and return")
	@Test
	void whenDeleteInteractionTypeById_thenDeleteAndReturn() throws Exception {
		// given
		when(interactionTypeService.delete(any(Integer.class))).thenReturn(true);

		// when
		mockMvc.perform(delete("/v1/interactiontypes/1")).andDo(print()).andReturn();

		// then
		verify(interactionTypeService, times(1)).delete(any(Integer.class));
		verifyNoMoreInteractions(interactionTypeService);
	}

	private static byte[] convertObjectToJsonBytes(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsBytes(object);
	}

}
