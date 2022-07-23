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
import com.skilldistillery.skillguild.entities.Interaction;
import com.skilldistillery.skillguild.entities.Interaction;
import com.skilldistillery.skillguild.services.InteractionService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class InteractionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private InteractionService interactionService;

	@Mock
	private MockHttpServletRequest req;

	@Mock
	private MockHttpServletResponse res;

	@Mock
	private Interaction interaction;

	@DisplayName("When calling index, return list of Interactions")
	@Test
	void getIndex_shouldReturn_ListInteractions() throws Exception {
		// given
		List<Interaction> interactions = new ArrayList<>();
		interactions.add(new Interaction());
		interactions.add(new Interaction());
		interactions.add(new Interaction());
		when(interactionService.index()).thenReturn(interactions);

		// when
		mockMvc.perform(get("/v1/interactions")).andReturn();

		// then
		verify(interactionService, times(1)).index();
		verifyNoMoreInteractions(interactionService);
	}

	@DisplayName("When post to interactions, create and return Interaction")
	@Test
	void whenPostInteraction_thenCreateAndReturnInteraction() throws Exception {
		// given
		Interaction interaction = new Interaction();
		interaction.setId(1);
		when(interactionService.create(any(Integer.class), any(Integer.class), any(Interaction.class))).thenReturn(interaction);

		// when
		MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json",
				java.nio.charset.Charset.forName("UTF-8"));
		mockMvc.perform(post("/v1/users/1/content/1/interactions").accept(MEDIA_TYPE_JSON_UTF8)
				.content(convertObjectToJsonBytes(interaction)).contentType(MEDIA_TYPE_JSON_UTF8)).andDo(print())
				.andReturn();

		// then
		verify(interactionService, times(1)).create(any(Integer.class), any(Integer.class), any(Interaction.class));
		verifyNoMoreInteractions(interactionService);
	}
	
	@DisplayName("When updating interaction by ID, update and return Interaction")
	@Test
	void whenPutInteraction_thenUpdateAndReturnInteraction() throws Exception {
		// given
		Interaction interaction = new Interaction();
		interaction.setId(1);
		when(interactionService.update(any(Integer.class), any(Interaction.class))).thenReturn(interaction);

		// when
		MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json",
				java.nio.charset.Charset.forName("UTF-8"));
		mockMvc.perform(put("/v1/interactions/1").accept(MEDIA_TYPE_JSON_UTF8).content(convertObjectToJsonBytes(interaction))
				.contentType(MEDIA_TYPE_JSON_UTF8)).andDo(print()).andReturn();

		// then
		verify(interactionService, times(1)).update(any(Integer.class), any(Interaction.class));
		verifyNoMoreInteractions(interactionService);
	}

	@DisplayName("When delete interaction by ID, delete and return")
	@Test
	void whenDeleteInteractionById_thenDeleteAndReturn() throws Exception {
		// given
		when(interactionService.delete(any(Integer.class))).thenReturn(true);

		// when
		mockMvc.perform(delete("/v1/interactions/1")).andDo(print()).andReturn();

		// then
		verify(interactionService, times(1)).delete(any(Integer.class));
		verifyNoMoreInteractions(interactionService);
	}

	private static byte[] convertObjectToJsonBytes(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsBytes(object);
	}

}
