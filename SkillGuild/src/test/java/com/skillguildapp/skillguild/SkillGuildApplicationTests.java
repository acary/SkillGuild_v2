package com.skillguildapp.skillguild;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SkillGuildApplicationTests {
	
	@Mock
	EntityManager entityManager;

	@Test
	void contextLoads() {
	}

}
