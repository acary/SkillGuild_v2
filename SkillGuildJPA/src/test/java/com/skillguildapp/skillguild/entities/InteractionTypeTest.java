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

import com.skillguildapp.skillguild.entities.InteractionType;

class InteractionTypeTest {

	private static EntityManagerFactory emf;
	private EntityManager em;
	private InteractionType interactionType;
	
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
		interactionType = em.find(InteractionType.class, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
		em.close();
		interactionType = null;
	}

	@Test
	void test_entity() {
		assertNotNull(interactionType);
		assertEquals("question", interactionType.getName());
	}

}
