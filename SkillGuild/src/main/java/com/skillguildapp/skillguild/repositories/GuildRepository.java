package com.skillguildapp.skillguild.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skillguildapp.skillguild.entities.Guild;
import com.skillguildapp.skillguild.entities.User;

public interface GuildRepository extends JpaRepository<Guild, Integer> {

	List<Guild> findByMembers_User(User user);
	
}
