package com.skilldistillery.skillguild.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.skillguild.entities.Content;

public interface ContentRepository extends JpaRepository<Content, Integer> {
	
	List<Content> findByGuild_id(int gid);

	List<Content> findByUserCreatedContent(int uid);

	List<Content> findByUserCreatedContent_username(String username);

	

}
