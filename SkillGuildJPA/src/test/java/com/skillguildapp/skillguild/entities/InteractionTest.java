package com.skillguildapp.skillguild.entities;

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

import com.skillguildapp.skillguild.entities.Interaction;

class InteractionTest {

	private static EntityManagerFactory emf;
	private EntityManager em;
	private Interaction interaction;
	
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
		interaction = em.find(Interaction.class, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
		em.close();
		interaction = null;
	}

	@Test
	void test_entity() {
		assertNotNull(interaction);
		assertEquals(1, interaction.getId());
	}

}
