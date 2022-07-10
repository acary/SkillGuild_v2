package com.skilldistillery.skillguild.services;

import java.util.List;

import com.skilldistillery.skillguild.entities.Question;

public interface QuestionService {

	List<Question> index();

	Question show(int qid);

	Question create(int cid, Question question);

	Question update(int qid, Question question);

	boolean delete(int qid);

	List<Question> showContentQuestions(int cid);
}
