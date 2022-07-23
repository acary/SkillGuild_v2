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
import com.skilldistillery.skillguild.entities.Comment;
import com.skilldistillery.skillguild.services.CommentService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CommentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CommentService commentService;

	@Mock
	private MockHttpServletRequest req;

	@Mock
	private MockHttpServletResponse res;

	@Mock
	private Comment comment;
	
	@Mock
	Principal principal = new PrincipalImpl();

	@DisplayName("When calling index, return list of Comments")
	@Test
	void getIndex_shouldReturn_ListComments() throws Exception {
		// given
		List<Comment> comments = new ArrayList<>();
		comments.add(new Comment());
		comments.add(new Comment());
		comments.add(new Comment());
		when(commentService.index()).thenReturn(comments);

		// when
		mockMvc.perform(get("/v1/comments")).andReturn();

		// then
		verify(commentService, times(1)).index();
		verifyNoMoreInteractions(commentService);
	}

	@DisplayName("When calling show, return Comment by id")
	@Test
	void getShow_shouldReturn_CommentById() throws Exception {
		// given
		Comment comment = new Comment();
		comment.setId(1);
		comment.setTextContent("Software Engineering");
		when(commentService.show(any(Integer.class))).thenReturn(comment);

		// when
		mockMvc.perform(get("/v1/comments/1").contentType("text/plain")).andDo(print()).andReturn();

		// then
		verify(commentService, times(1)).show(any(Integer.class));
		verifyNoMoreInteractions(commentService);
	}

	@DisplayName("When post to comments, create and return Comment")
	@Test
	@WithMockUser(username = "SkillGuildUser")
	void whenPostComment_thenCreateAndReturnComment() throws Exception {
		// given
		Comment comment = new Comment();
		comment.setId(1);
		comment.setTextContent("Interesting, informative and helpful!");
		when(commentService.create(any(Integer.class), any(Comment.class), any(String.class))).thenReturn(comment);

		// when
		MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json",
				java.nio.charset.Charset.forName("UTF-8"));
		mockMvc.perform(post("/v1/contents/1/comments").principal(principal).accept(MEDIA_TYPE_JSON_UTF8)
				.content(convertObjectToJsonBytes(comment)).contentType(MEDIA_TYPE_JSON_UTF8)).andDo(print())
				.andReturn();

		// then
		verify(commentService, times(1)).create(any(Integer.class), any(Comment.class), any(String.class));
		verifyNoMoreInteractions(commentService);
	}

	@DisplayName("When delete comment by ID, delete and return boolean")
	@Test
	void wheDeleteCommentById_thenDeleteAndReturnBoolean() throws Exception {
		// given
		when(commentService.delete(any(Integer.class))).thenReturn(true);

		// when
		mockMvc.perform(delete("/v1/comments/1")).andDo(print()).andReturn();

		// then
		verify(commentService, times(1)).delete(any(Integer.class));
		verifyNoMoreInteractions(commentService);
	}

	@DisplayName("When updating comment by ID, update and return Comment")
	@Test
	void whenPutComment_thenUpdateAndReturnComment() throws Exception {
		// given
		Comment comment = new Comment();
		comment.setId(1);
		comment.setTextContent("Data Engineering");
		when(commentService.update(any(Integer.class), any(Comment.class))).thenReturn(comment);

		// when
		MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json",
				java.nio.charset.Charset.forName("UTF-8"));
		mockMvc.perform(put("/v1/comments/1").accept(MEDIA_TYPE_JSON_UTF8).content(convertObjectToJsonBytes(comment))
				.contentType(MEDIA_TYPE_JSON_UTF8)).andDo(print()).andReturn();

		// then
		verify(commentService, times(1)).update(any(Integer.class), any(Comment.class));
		verifyNoMoreInteractions(commentService);
	}

	private static byte[] convertObjectToJsonBytes(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsBytes(object);
	}

	class PrincipalImpl implements Principal {
		@Override
		public String getName() {
			return "SkillGuildUser";
		}
	}

}
