package com.skillguildapp.skillguild.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skillguildapp.skillguild.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUsername(String username);
}
