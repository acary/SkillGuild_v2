package com.skillguildapp.skillguild;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@ComponentScan({"com.skillguildapp.skillguild"})
public class SkillGuildApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkillGuildApplication.class, args);
	}
	
	@Bean
	public PasswordEncoder configurePasswordEncoder() {
	   return new BCryptPasswordEncoder();
	}

}
