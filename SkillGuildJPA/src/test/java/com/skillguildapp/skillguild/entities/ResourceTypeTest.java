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

import com.skillguildapp.skillguild.entities.ResourceType;

class ResourceTypeTest {

	private static EntityManagerFactory emf;
	private EntityManager em;
	private ResourceType resourceType;
	
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
		resourceType = em.find(ResourceType.class, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
		em.close();
		resourceType = null;
	}

	@Test
	void test_entity() {
		assertNotNull(resourceType);
		// assertEquals("slides", resourceType.getName());
	}

}
