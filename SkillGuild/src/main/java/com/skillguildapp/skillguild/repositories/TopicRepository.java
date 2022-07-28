package com.skillguildapp.skillguild.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skillguildapp.skillguild.entities.Topic;

public interface TopicRepository extends JpaRepository<Topic, Integer> {

}
