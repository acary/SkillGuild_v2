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
import com.skillguildapp.skillguild.entities.Status;
import com.skillguildapp.skillguild.services.StatusService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class StatusControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private StatusService statusService;

	@Mock
	private MockHttpServletRequest req;

	@Mock
	private MockHttpServletResponse res;

	@Mock
	private Status status;

	@DisplayName("When calling index, return list of Statuss")
	@Test
	void getIndex_shouldReturn_ListStatuss() throws Exception {
		// given
		List<Status> statuses = new ArrayList<>();
		statuses.add(new Status());
		statuses.add(new Status());
		statuses.add(new Status());
		when(statusService.index()).thenReturn(statuses);

		// when
		mockMvc.perform(get("/v1/statuses")).andReturn();

		// then
		verify(statusService, times(1)).index();
		verifyNoMoreInteractions(statusService);
	}

	@DisplayName("When calling show, return Status by id")
	@Test
	void getShow_shouldReturn_StatusById() throws Exception {
		// given
		Status status = new Status();
		status.setId(1);
		status.setName("Draft");
		when(statusService.show(any(Integer.class))).thenReturn(status);

		// when
		mockMvc.perform(get("/v1/statuses/1").contentType("text/plain")).andDo(print()).andReturn();

		// then
		verify(statusService, times(1)).show(any(Integer.class));
		verifyNoMoreInteractions(statusService);
	}

	@DisplayName("When post to statuses, create and return Status")
	@Test
	void whenPostStatus_thenCreateAndReturnStatus() throws Exception {
		// given
		Status status = new Status();
		status.setId(1);
		status.setName("Scheduled");
		when(statusService.create(any(Status.class))).thenReturn(status);

		// when
		MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json",
				java.nio.charset.Charset.forName("UTF-8"));
		mockMvc.perform(post("/v1/statuses").accept(MEDIA_TYPE_JSON_UTF8).content(convertObjectToJsonBytes(status))
				.contentType(MEDIA_TYPE_JSON_UTF8)).andDo(print()).andReturn();

		// then
		verify(statusService, times(1)).create(any(Status.class));
		verifyNoMoreInteractions(statusService);
	}

	@DisplayName("When delete status by ID, delete and return boolean")
	@Test
	void wheDeleteStatusById_thenDeleteAndReturnBoolean() throws Exception {
		// given
		when(statusService.delete(any(Integer.class))).thenReturn(true);

		// when
		mockMvc.perform(delete("/v1/statuses/1")).andDo(print()).andReturn();

		// then
		verify(statusService, times(1)).delete(any(Integer.class));
		verifyNoMoreInteractions(statusService);
	}

	@DisplayName("When updating status by ID, update and return Status")
	@Test
	void whenPutStatus_thenUpdateAndReturnStatus() throws Exception {
		// given
		Status status = new Status();
		status.setId(1);
		status.setName("Published");
		when(statusService.update(any(Integer.class), any(Status.class))).thenReturn(status);

		// when
		MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json",
				java.nio.charset.Charset.forName("UTF-8"));
		mockMvc.perform(put("/v1/statuses/1").accept(MEDIA_TYPE_JSON_UTF8).content(convertObjectToJsonBytes(status))
				.contentType(MEDIA_TYPE_JSON_UTF8)).andDo(print()).andReturn();

		// then
		verify(statusService, times(1)).update(any(Integer.class), any(Status.class));
		verifyNoMoreInteractions(statusService);
	}

	private static byte[] convertObjectToJsonBytes(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsBytes(object);
	}

}
