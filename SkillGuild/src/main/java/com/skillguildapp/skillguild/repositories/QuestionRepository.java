package com.skillguildapp.skillguild.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skillguildapp.skillguild.entities.Question;

public interface QuestionRepository extends JpaRepository <Question, Integer> {

	List<Question> findByContent_id(int cid);

}
