package com.skilldistillery.skillguild.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GuildTest {

	private static EntityManagerFactory emf;
	private EntityManager em;
	private Guild guild;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	 emf = Persistence.createEntityManagerFactory("SkillGuildJPA");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		emf.close();
	}

	@BeforeEach
	void setUp() throws Exception {
		em = emf.createEntityManager();
		guild = em.find(Guild.class, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
		em.close();
		guild = null;
	}

	@Test
	void test_entity() {
		assertNotNull(guild);
		assertEquals("Frontend Development", guild.getName());
	}

}
