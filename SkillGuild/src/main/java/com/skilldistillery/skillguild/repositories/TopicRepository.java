package com.skilldistillery.skillguild.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.skillguild.entities.Topic;

public interface TopicRepository extends JpaRepository<Topic, Integer> {

}
