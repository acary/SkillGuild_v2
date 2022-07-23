package com.skilldistillery.skillguild.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.io.IOException;
import java.security.Principal;

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
import com.skilldistillery.skillguild.entities.User;
import com.skilldistillery.skillguild.services.AuthService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AuthControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AuthService authService;

	@Mock
	private MockHttpServletRequest req;

	@Mock
	private MockHttpServletResponse res;

	@Mock
	Principal principal = new PrincipalImpl();

	@DisplayName("When post to register, register and return User")
	@Test
	@WithMockUser(username = "SkillAuthUser")
	void whenPostRegister_thenRegisterAndReturnUser() throws Exception {
		// given
		User user = new User();
		user.setId(1);
		when(authService.register(any(User.class))).thenReturn(user);

		// when
		MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json",
				java.nio.charset.Charset.forName("UTF-8"));
		mockMvc.perform(post("/register").principal(principal).accept(MEDIA_TYPE_JSON_UTF8)
				.content(convertObjectToJsonBytes(user)).contentType(MEDIA_TYPE_JSON_UTF8)).andDo(print()).andReturn();

		// then
		verify(authService, times(1)).register(any(User.class));
		verifyNoMoreInteractions(authService);
	}

	@DisplayName("Get user auth")
	@Test
	@WithMockUser(username = "SkillAuthUser")
	void getShowUserAuth_shouldReturnAuth() throws Exception {
		// given
		User user = new User();
		user.setId(1);
		when(authService.getUserByUsername(any(String.class))).thenReturn(user);

		// when
		mockMvc.perform(get("/authenticate").principal(principal).contentType("text/plain")).andDo(print()).andReturn();

		// then
		verify(authService, times(1)).getUserByUsername(any(String.class));
		verifyNoMoreInteractions(authService);
	}

	private static byte[] convertObjectToJsonBytes(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsBytes(object);
	}

	class PrincipalImpl implements Principal {
		@Override
		public String getName() {
			return "SkillAuthUser";
		}
	}

}
