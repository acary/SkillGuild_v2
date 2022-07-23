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
import com.skilldistillery.skillguild.entities.Guild;
import com.skilldistillery.skillguild.entities.Member;
import com.skilldistillery.skillguild.services.GuildService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class GuildControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private GuildService guildService;

	@Mock
	private MockHttpServletRequest req;

	@Mock
	private MockHttpServletResponse res;

	@Mock
	private Guild guild;

	@Mock
	Principal principal = new PrincipalImpl();
	
	@Mock
	private Member member;

	@DisplayName("When calling index, return list of Guilds")
	@Test
	void getIndex_shouldReturn_ListGuilds() throws Exception {
		// given
		List<Guild> guilds = new ArrayList<>();
		guilds.add(new Guild());
		guilds.add(new Guild());
		guilds.add(new Guild());
		when(guildService.index()).thenReturn(guilds);

		// when
		mockMvc.perform(get("/v1/guilds")).andReturn();

		// then
		verify(guildService, times(1)).index();
		verifyNoMoreInteractions(guildService);
	}

	@DisplayName("When calling show, return Guild by id")
	@Test
	void getShow_shouldReturn_GuildById() throws Exception {
		// given
		Guild guild = new Guild();
		guild.setId(1);
		guild.setName("Software Engineering");
		guild.setDescription("Activities and interests related to the Software Engineering discipline");
		when(guildService.show(any(Integer.class))).thenReturn(guild);

		// when
		mockMvc.perform(get("/v1/guilds/1").contentType("text/plain")).andDo(print()).andReturn();

		// then
		verify(guildService, times(1)).show(any(Integer.class));
		verifyNoMoreInteractions(guildService);
	}

	@DisplayName("When calling myguilds, return member of guilds")
	@Test
	@WithMockUser(username = "SkillGuildUser")
	void getMemberOfGuild_shouldReturn_Guilds() throws Exception {
		// given
		List<Guild> guilds = new ArrayList<Guild>();
		Guild guild = new Guild();
		guild.setId(1);
		guild.setName("Software Engineering");
		guilds.add(guild);
		Guild guild2 = new Guild();
		guild.setId(2);
		guild.setName("Data Engineering");
		guilds.add(guild2);
		when(guildService.memberOfGuild(any(String.class))).thenReturn(guilds);

		// when
		mockMvc.perform(get("/v1/guilds/myguilds").contentType("text/plain")).andDo(print()).andReturn();

		// then
		verify(guildService, times(1)).memberOfGuild(any(String.class));
		verifyNoMoreInteractions(guildService);
	}

	@DisplayName("Get members by guild ID")
	@Test
	@WithMockUser(username = "SkillGuildUser")
	void getGuildMembers_shouldReturn_Members() throws Exception {
		// given
		List<Member> members = new ArrayList<Member>();
		Member member = new Member();
		members.add(member);
		Member member2 = new Member();
		members.add(member2);
		when(guildService.getGuildMembers(any(Integer.class))).thenReturn(members);

		// when
		mockMvc.perform(get("/v1/guilds/1/members").contentType("text/plain")).andDo(print()).andReturn();

		// then
		verify(guildService, times(1)).getGuildMembers(any(Integer.class));
		verifyNoMoreInteractions(guildService);
	}

	@DisplayName("When post to guilds, create and return Guild")
	@Test
	@WithMockUser(username = "SkillGuildUser")
	void whenPostGuild_thenCreateAndReturnGuild() throws Exception {
		// given
		Guild guild = new Guild();
		guild.setId(1);
		guild.setName("Software Architecture");
		guild.setDescription("Activities and interests related to the Software Architecture discipline");
		when(guildService.create(any(Guild.class), any(String.class))).thenReturn(guild);

		// when
		MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json",
				java.nio.charset.Charset.forName("UTF-8"));
		mockMvc.perform(post("/v1/guilds").principal(principal).accept(MEDIA_TYPE_JSON_UTF8)
				.content(convertObjectToJsonBytes(guild)).contentType(MEDIA_TYPE_JSON_UTF8)).andDo(print()).andReturn();

		// then
		verify(guildService, times(1)).create(any(Guild.class), any(String.class));
		verifyNoMoreInteractions(guildService);
	}

	@DisplayName("When delete guild by ID, delete and return boolean")
	@Test
	void wheDeleteGuildById_thenDeleteAndReturnBoolean() throws Exception {
		// given
		when(guildService.delete(any(Integer.class))).thenReturn(true);

		// when
		mockMvc.perform(delete("/v1/guilds/1")).andDo(print()).andReturn();

		// then
		verify(guildService, times(1)).delete(any(Integer.class));
		verifyNoMoreInteractions(guildService);
	}

	@DisplayName("When updating guild by ID, update and return Guild")
	@Test
	void whenPutGuild_thenUpdateAndReturnGuild() throws Exception {
		// given
		Guild guild = new Guild();
		guild.setId(1);
		guild.setName("Data Engineering");
		guild.setDescription("Activities and interests related to the Data Engineering discipline");
		when(guildService.update(any(Integer.class), any(Guild.class))).thenReturn(guild);

		// when
		MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json",
				java.nio.charset.Charset.forName("UTF-8"));
		mockMvc.perform(put("/v1/guilds/1").accept(MEDIA_TYPE_JSON_UTF8).content(convertObjectToJsonBytes(guild))
				.contentType(MEDIA_TYPE_JSON_UTF8)).andDo(print()).andReturn();

		// then
		verify(guildService, times(1)).update(any(Integer.class), any(Guild.class));
		verifyNoMoreInteractions(guildService);
	}
	
	@DisplayName("When post joinGuild, join and return boolean")
	@Test
	void whenPostJoinGuild_thenJoinAndReturnBoolean() throws Exception {
		// given
		when(guildService.join(any(Integer.class), any(Integer.class))).thenReturn(true);

		// when
		MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json",
				java.nio.charset.Charset.forName("UTF-8"));
		mockMvc.perform(post("/v1/guilds/1/1").accept(MEDIA_TYPE_JSON_UTF8)
				.contentType(MEDIA_TYPE_JSON_UTF8)).andDo(print()).andReturn();

		// then
		verify(guildService, times(1)).join(any(Integer.class), any(Integer.class));
		verifyNoMoreInteractions(guildService);
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
