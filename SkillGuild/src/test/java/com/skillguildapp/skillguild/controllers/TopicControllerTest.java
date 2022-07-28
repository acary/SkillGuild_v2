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
import com.skillguildapp.skillguild.entities.Topic;
import com.skillguildapp.skillguild.services.TopicService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class TopicControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TopicService topicService;

	@Mock
	private MockHttpServletRequest req;

	@Mock
	private MockHttpServletResponse res;

	@Mock
	private Topic topic;

	@DisplayName("When calling index, return list of Topics")
	@Test
	void getIndex_shouldReturn_ListTopics() throws Exception {
		// given
		List<Topic> topics = new ArrayList<>();
		topics.add(new Topic());
		topics.add(new Topic());
		topics.add(new Topic());
		when(topicService.index()).thenReturn(topics);

		// when
		mockMvc.perform(get("/v1/topics")).andReturn();

		// then
		verify(topicService, times(1)).index();
		verifyNoMoreInteractions(topicService);
	}

	@DisplayName("When calling show, return Topic by id")
	@Test
	void getShow_shouldReturn_TopicById() throws Exception {
		// given
		Topic topic = new Topic();
		topic.setId(1);
		topic.setName("Software Engineering");
		topic.setDescription("Activities and interests related to the Software Engineering discipline");
		when(topicService.show(any(Integer.class))).thenReturn(topic);

		// when
		mockMvc.perform(get("/v1/topics/1").contentType("text/plain")).andDo(print()).andReturn();

		// then
		verify(topicService, times(1)).show(any(Integer.class));
		verifyNoMoreInteractions(topicService);
	}

	@DisplayName("When post to topics, create and return Topic")
	@Test
	void whenPostTopic_thenCreateAndReturnTopic() throws Exception {
		// given
		Topic topic = new Topic();
		topic.setId(1);
		topic.setName("Software Architecture");
		topic.setDescription("Activities and interests related to the Software Architecture discipline");
		when(topicService.create(any(Topic.class))).thenReturn(topic);

		// when
		MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json",
				java.nio.charset.Charset.forName("UTF-8"));
		mockMvc.perform(post("/v1/topics").accept(MEDIA_TYPE_JSON_UTF8).content(convertObjectToJsonBytes(topic))
				.contentType(MEDIA_TYPE_JSON_UTF8)).andDo(print()).andReturn();

		// then
		verify(topicService, times(1)).create(any(Topic.class));
		verifyNoMoreInteractions(topicService);
	}

	@DisplayName("When delete topic by ID, delete and return boolean")
	@Test
	void wheDeleteTopicById_thenDeleteAndReturnBoolean() throws Exception {
		// given
		when(topicService.delete(any(Integer.class))).thenReturn(true);

		// when
		mockMvc.perform(delete("/v1/topics/1")).andDo(print()).andReturn();

		// then
		verify(topicService, times(1)).delete(any(Integer.class));
		verifyNoMoreInteractions(topicService);
	}

	@DisplayName("When updating topic by ID, update and return Topic")
	@Test
	void whenPutTopic_thenUpdateAndReturnTopic() throws Exception {
		// given
		Topic topic = new Topic();
		topic.setId(1);
		topic.setName("Data Engineering");
		topic.setDescription("Activities and interests related to the Data Engineering discipline");
		when(topicService.update(any(Integer.class), any(Topic.class))).thenReturn(topic);

		// when
		MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json",
				java.nio.charset.Charset.forName("UTF-8"));
		mockMvc.perform(put("/v1/topics/1").accept(MEDIA_TYPE_JSON_UTF8).content(convertObjectToJsonBytes(topic))
				.contentType(MEDIA_TYPE_JSON_UTF8)).andDo(print()).andReturn();

		// then
		verify(topicService, times(1)).update(any(Integer.class), any(Topic.class));
		verifyNoMoreInteractions(topicService);
	}

	private static byte[] convertObjectToJsonBytes(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsBytes(object);
	}

}
