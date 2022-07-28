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

import org.junit.jupiter.api.Disabled;
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
import com.skillguildapp.skillguild.entities.User;
import com.skillguildapp.skillguild.services.UserService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@Mock
	private MockHttpServletRequest req;

	@Mock
	private MockHttpServletResponse res;

	@Mock
	private User user;
	
	@Mock
	Principal principal = new PrincipalImpl();

	@DisplayName("When calling index, return list of Users")
	@Test
	void getIndex_shouldReturn_ListUsers() throws Exception {
		// given
		List<User> users = new ArrayList<>();
		users.add(new User());
		users.add(new User());
		users.add(new User());
		when(userService.index()).thenReturn(users);

		// when
		mockMvc.perform(get("/v1/users")).andReturn();

		// then
		verify(userService, times(1)).index();
		verifyNoMoreInteractions(userService);
	}

	@DisplayName("When calling show, return User by id")
	@Test
	@WithMockUser(username = "SkillGuildUser")
	void getShow_shouldReturn_UserById() throws Exception {
		// given
		User user = new User();
		user.setId(1);
		user.setEmail("engineering@skillguild.app");
		when(userService.show(any(Integer.class), any(String.class))).thenReturn(user);

		// when
		mockMvc.perform(get("/v1/users/1").contentType("text/plain")).andDo(print()).andReturn();

		// then
		verify(userService, times(1)).show(any(Integer.class), any(String.class));
		verifyNoMoreInteractions(userService);
	}
	
	@DisplayName("When getByCredentials, return User profile")
	@Test
	@WithMockUser(username = "SkillGuildUser")
	void getByCredentials_shouldReturn_UserProfile() throws Exception {
		// given
		User user = new User();
		user.setId(1);
		user.setEmail("engineering@skillguild.app");
		when(userService.showProfile(any(String.class))).thenReturn(user);

		// when
		mockMvc.perform(get("/v1/users/profile").contentType("text/plain")).andDo(print()).andReturn();

		// then
		verify(userService, times(1)).showProfile(any(String.class));
		verifyNoMoreInteractions(userService);
	}

	@Disabled
	@DisplayName("When post to users, create and return User")
	@Test
	@WithMockUser(username = "SkillGuildUser")
	void whenPostUser_thenCreateAndReturnUser() throws Exception {
		// given
		User user = new User();
		user.setId(1);
		user.setEmail("engineering@skillguild.app");
		// when(userService.create(any(Integer.class), any(User.class), any(String.class))).thenReturn(user);

		// when
		MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json",
				java.nio.charset.Charset.forName("UTF-8"));
		mockMvc.perform(post("/v1/contents/1/users").principal(principal).accept(MEDIA_TYPE_JSON_UTF8)
				.content(convertObjectToJsonBytes(user)).contentType(MEDIA_TYPE_JSON_UTF8)).andDo(print())
				.andReturn();

		// then
		// verify(userService, times(1)).create(any(Integer.class), any(User.class), any(String.class));
		verifyNoMoreInteractions(userService);
	}

	@DisplayName("When delete user by ID, delete and return boolean")
	@Test
	void wheDeleteUserById_thenDeleteAndReturnBoolean() throws Exception {
		// given
		when(userService.deleteUser(any(Integer.class))).thenReturn(true);

		// when
		mockMvc.perform(delete("/v1/users/1")).andDo(print()).andReturn();

		// then
		verify(userService, times(1)).deleteUser(any(Integer.class));
		verifyNoMoreInteractions(userService);
	}

	@DisplayName("When updating user by ID, update and return User")
	@Test
	@WithMockUser(username = "SkillGuildUser")
	void whenPutUser_thenUpdateAndReturnUser() throws Exception {
		// given
		User user = new User();
		user.setId(1);
		user.setEmail("engineering@skillguild.app");
		when(userService.updateAsAdmin(any(Integer.class), any(User.class), any(String.class))).thenReturn(user);

		// when
		MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json",
				java.nio.charset.Charset.forName("UTF-8"));
		mockMvc.perform(put("/v1/users/1").accept(MEDIA_TYPE_JSON_UTF8).content(convertObjectToJsonBytes(user))
				.contentType(MEDIA_TYPE_JSON_UTF8)).andDo(print()).andReturn();

		// then
		verify(userService, times(1)).updateAsAdmin(any(Integer.class), any(User.class), any(String.class));
		verifyNoMoreInteractions(userService);
	}
	
	@DisplayName("When editprofile, updateAsUser and return User")
	@Test
	@WithMockUser(username = "SkillGuildUser")
	void whenEditProfile_thenUpdateAsUserAndReturnUser() throws Exception {
		// given
		User user = new User();
		user.setId(1);
		user.setEmail("engineering@skillguild.app");
		when(userService.updateAsUser(any(User.class), any(String.class))).thenReturn(user);

		// when
		MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json",
				java.nio.charset.Charset.forName("UTF-8"));
		mockMvc.perform(put("/v1/users/editprofile").accept(MEDIA_TYPE_JSON_UTF8).content(convertObjectToJsonBytes(user))
				.contentType(MEDIA_TYPE_JSON_UTF8)).andDo(print()).andReturn();

		// then
		verify(userService, times(1)).updateAsUser(any(User.class), any(String.class));
		verifyNoMoreInteractions(userService);
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
