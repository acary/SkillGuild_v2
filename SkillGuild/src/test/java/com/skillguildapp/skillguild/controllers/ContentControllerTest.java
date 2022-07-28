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
import com.skillguildapp.skillguild.entities.Content;
import com.skillguildapp.skillguild.entities.Member;
import com.skillguildapp.skillguild.services.ContentService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ContentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ContentService contentService;

	@Mock
	private MockHttpServletRequest req;

	@Mock
	private MockHttpServletResponse res;

	@Mock
	private Content content;

	@Mock
	Principal principal = new PrincipalImpl();

	@Mock
	private Member member;

	@DisplayName("When calling index, return list of Contents")
	@Test
	void getIndex_shouldReturn_ListContents() throws Exception {
		// given
		List<Content> contents = new ArrayList<>();
		contents.add(new Content());
		contents.add(new Content());
		contents.add(new Content());
		when(contentService.index()).thenReturn(contents);

		// when
		mockMvc.perform(get("/v1/contents")).andReturn();

		// then
		verify(contentService, times(1)).index();
		verifyNoMoreInteractions(contentService);
	}

	@DisplayName("When calling show, return Content by id")
	@Test
	void getShow_shouldReturn_ContentById() throws Exception {
		// given
		Content content = new Content();
		content.setId(1);
		content.setTitle("Software Engineering");
		content.setDescription("Activities and interests related to the Software Engineering discipline");
		when(contentService.show(any(Integer.class))).thenReturn(content);

		// when
		mockMvc.perform(get("/v1/contents/1").contentType("text/plain")).andDo(print()).andReturn();

		// then
		verify(contentService, times(1)).show(any(Integer.class));
		verifyNoMoreInteractions(contentService);
	}

	@DisplayName("Show guild content")
	@Test
	@WithMockUser(username = "SkillContentUser")
	void getShowGuildContent_shouldReturn_Content() throws Exception {
		// given
		Content content = new Content();
		when(contentService.showGuildContent(any(Integer.class), any(Integer.class))).thenReturn(content);

		// when
		mockMvc.perform(get("/v1/guilds/1/contents/1").contentType("text/plain")).andDo(print()).andReturn();

		// then
		verify(contentService, times(1)).showGuildContent(any(Integer.class), any(Integer.class));
		verifyNoMoreInteractions(contentService);
	}

	@DisplayName("Get user content")
	@Test
	@WithMockUser(username = "SkillContentUser")
	void getShowUserContent_shouldReturnContent() throws Exception {
		// given
		Content content = new Content();
		when(contentService.showGuildContent(any(Integer.class), any(Integer.class))).thenReturn(content);

		// when
		mockMvc.perform(get("/v1/users/1/contents/1").contentType("text/plain")).andDo(print()).andReturn();

		// then
		verify(contentService, times(1)).showGuildContent(any(Integer.class), any(Integer.class));
		verifyNoMoreInteractions(contentService);
	}

	@DisplayName("When post to contents, create and return Content")
	@Test
	@WithMockUser(username = "SkillContentUser")
	void whenPostContent_thenCreateAndReturnContent() throws Exception {
		// given
		Content content = new Content();
		content.setId(1);
		content.setTitle("Software Architecture");
		content.setDescription("Activities and interests related to the Software Architecture discipline");
		when(contentService.create(any(Integer.class), any(Integer.class), any(Integer.class), any(Content.class)))
				.thenReturn(content);

		// when
		MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json",
				java.nio.charset.Charset.forName("UTF-8"));
		mockMvc.perform(post("/v1/users/1/guilds/1/statuses/1/contents").principal(principal).accept(MEDIA_TYPE_JSON_UTF8)
				.content(convertObjectToJsonBytes(content)).contentType(MEDIA_TYPE_JSON_UTF8)).andDo(print())
				.andReturn();

		// then
		verify(contentService, times(1)).create(any(Integer.class), any(Integer.class), any(Integer.class),
				any(Content.class));
		verifyNoMoreInteractions(contentService);
	}

	@DisplayName("When delete content by ID, delete and return boolean")
	@Test
	void wheDeleteContentById_thenDeleteAndReturnBoolean() throws Exception {
		// given
		when(contentService.delete(any(Integer.class))).thenReturn(true);

		// when
		mockMvc.perform(delete("/v1/contents/1")).andDo(print()).andReturn();

		// then
		verify(contentService, times(1)).delete(any(Integer.class));
		verifyNoMoreInteractions(contentService);
	}

	@DisplayName("When updating content by ID, update and return Content")
	@Test
	void whenPutContent_thenUpdateAndReturnContent() throws Exception {
		// given
		Content content = new Content();
		content.setId(1);
		content.setTitle("Data Engineering");
		content.setDescription("Activities and interests related to the Data Engineering discipline");
		when(contentService.update(any(Integer.class), any(Content.class))).thenReturn(content);

		// when
		MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json",
				java.nio.charset.Charset.forName("UTF-8"));
		mockMvc.perform(put("/v1/contents/1").accept(MEDIA_TYPE_JSON_UTF8).content(convertObjectToJsonBytes(content))
				.contentType(MEDIA_TYPE_JSON_UTF8)).andDo(print()).andReturn();

		// then
		verify(contentService, times(1)).update(any(Integer.class), any(Content.class));
		verifyNoMoreInteractions(contentService);
	}

	@DisplayName("When post joinContent, join and return boolean")
	@Test
	@WithMockUser(username = "SkillGuildUser")
	void whenPostJoinContent_thenJoinAndReturnBoolean() throws Exception {
		// given
		Content content = new Content();
		when(contentService.createNewContent(any(Integer.class), any(Integer.class), any(Content.class),
				any(String.class))).thenReturn(content);

		// when
		MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json",
				java.nio.charset.Charset.forName("UTF-8"));
		mockMvc.perform(post("/v1/guilds/1/statuses/1/contents").accept(MEDIA_TYPE_JSON_UTF8)
				.principal(principal)
				.content(convertObjectToJsonBytes(content))
				.contentType(MEDIA_TYPE_JSON_UTF8))
				.andDo(print()).andReturn();

		// then
		verify(contentService, times(1)).createNewContent(any(Integer.class), any(Integer.class), any(Content.class),
				any(String.class));
		verifyNoMoreInteractions(contentService);
	}

	private static byte[] convertObjectToJsonBytes(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsBytes(object);
	}

	class PrincipalImpl implements Principal {
		@Override
		public String getName() {
			return "SkillContentUser";
		}
	}

}
