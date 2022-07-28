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
import java.security.Principal;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillguildapp.skillguild.entities.Question;
import com.skillguildapp.skillguild.services.QuestionService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class QuestionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private QuestionService questionService;

	@Mock
	private MockHttpServletRequest req;

	@Mock
	private MockHttpServletResponse res;

	@Mock
	private Question question;

	@Mock
	Principal principal = new PrincipalImpl();

	@DisplayName("When calling index, return list of Questions")
	@Test
	void getIndex_shouldReturn_ListQuestions() throws Exception {
		// given
		List<Question> questions = new ArrayList<>();
		questions.add(new Question());
		questions.add(new Question());
		questions.add(new Question());
		when(questionService.index()).thenReturn(questions);

		// when
		mockMvc.perform(get("/v1/questions")).andReturn();

		// then
		verify(questionService, times(1)).index();
		verifyNoMoreInteractions(questionService);
	}

	@DisplayName("When calling show, return Question by id")
	@Test
	void getShow_shouldReturn_QuestionById() throws Exception {
		// given
		Question question = new Question();
		question.setId(1);
		question.setQuestion("What is the value of pi?");
		question.setCorrectAnswer("3.14159");
		when(questionService.show(any(Integer.class))).thenReturn(question);

		// when
		mockMvc.perform(get("/v1/questions/1").contentType("text/plain")).andDo(print()).andReturn();

		// then
		verify(questionService, times(1)).show(any(Integer.class));
		verifyNoMoreInteractions(questionService);
	}

	@DisplayName("When post to questions, create and return Question")
	@Test
	void whenPostQuestion_thenCreateAndReturnQuestion() throws Exception {
		// given
		Question question = new Question();
		question.setId(1);
		question.setQuestion("What is the value of pi?");
		question.setCorrectAnswer("3.14159");
		when(questionService.create(any(Integer.class), any(Question.class))).thenReturn(question);

		// when
		MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json",
				java.nio.charset.Charset.forName("UTF-8"));
		mockMvc.perform(post("/v1/questions/1").accept(MEDIA_TYPE_JSON_UTF8).content(convertObjectToJsonBytes(question))
				.contentType(MEDIA_TYPE_JSON_UTF8)).andDo(print()).andReturn();

		// then
		verify(questionService, times(1)).create(any(Integer.class), any(Question.class));
		verifyNoMoreInteractions(questionService);
	}

	@DisplayName("When delete question by ID, delete and return boolean")
	@Test
	void wheDeleteQuestionById_thenDeleteAndReturnBoolean() throws Exception {
		// given
		when(questionService.delete(any(Integer.class))).thenReturn(true);

		// when
		mockMvc.perform(delete("/v1/questions/1")).andDo(print()).andReturn();

		// then
		verify(questionService, times(1)).delete(any(Integer.class));
		verifyNoMoreInteractions(questionService);
	}

	@DisplayName("When updating question by ID, update and return Question")
	@Test
	void whenPutQuestion_thenUpdateAndReturnQuestion() throws Exception {
		// given
		Question question = new Question();
		question.setId(1);
		question.setQuestion("What is the value of pi?");
		question.setCorrectAnswer("3.14159");
		when(questionService.update(any(Integer.class), any(Question.class))).thenReturn(question);

		// when
		MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json",
				java.nio.charset.Charset.forName("UTF-8"));
		mockMvc.perform(put("/v1/questions/1").accept(MEDIA_TYPE_JSON_UTF8).content(convertObjectToJsonBytes(question))
				.contentType(MEDIA_TYPE_JSON_UTF8)).andDo(print()).andReturn();

		// then
		verify(questionService, times(1)).update(any(Integer.class), any(Question.class));
		verifyNoMoreInteractions(questionService);
	}

	private static byte[] convertObjectToJsonBytes(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsBytes(object);
	}

	@DisplayName("Show questions by content ID")
	@Test
	@WithMockUser(username = "SkillGuildUser")
	void getShowContentQuestions_shouldReturn_ListQuestions() throws Exception {
		// given
		List<Question> questions = new ArrayList<>();
		questions.add(new Question());
		questions.add(new Question());
		questions.add(new Question());
		when(questionService.showContentQuestions(any(Integer.class))).thenReturn(questions);

		// when
		mockMvc.perform(get("/v1/questions/1/questions").principal(principal)).andReturn();

		// then
		verify(questionService, times(1)).showContentQuestions(any(Integer.class));
		verifyNoMoreInteractions(questionService);
	}

	class PrincipalImpl implements Principal {
		@Override
		public String getName() {
			return "SkillGuildUser";
		}
	}

}
