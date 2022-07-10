package com.skilldistillery.skillguild.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.skillguild.entities.Comment;
import com.skilldistillery.skillguild.entities.Content;
import com.skilldistillery.skillguild.entities.Question;
import com.skilldistillery.skillguild.repositories.ContentRepository;
import com.skilldistillery.skillguild.repositories.QuestionRepository;

@Service
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	QuestionRepository questionRepo;

	@Autowired
	ContentRepository contentRepo;

	@Override
	public List<Question> index() {
		return questionRepo.findAll();
	}

	@Override
	public Question show(int qid) {
		Optional<Question> op = questionRepo.findById(qid);
		if (op.isPresent()) {
			Question result = op.get();
			return result;
		}
		return null;
	}

	@Override
	public Question create(int cid, Question question) {

		Question newQuestion = new Question();
		
		try {
			Optional<Content> op = contentRepo.findById(cid);
			if (op.isPresent()) {
				Content result = op.get();
				newQuestion.setContent(result);
			}
		} catch (Error err) {
			System.out.println(err);
		}

		try {
			newQuestion.setQuestion(question.getQuestion());
			newQuestion.setCorrectAnswer(question.getCorrectAnswer());
			return questionRepo.saveAndFlush(newQuestion);
		} catch (Error err) {
			System.out.print(err);
		}
		return null;
	}

	@Override
	public Question update(int qid, Question question) {
		Optional<Question> op = questionRepo.findById(qid);
		if (op.isPresent()) {
			Question result = op.get();
			question.setId(qid);
			question.setContent(result.getContent());
			return questionRepo.saveAndFlush(question);
		}
		return null;
	}

	@Override
	public boolean delete(int qid) {
		Optional<Question> opt = questionRepo.findById(qid);
		if (opt.isPresent()) {
			Question question = opt.get();
			questionRepo.delete(question);
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public List<Question> showContentQuestions(int cid) {
		return 	questionRepo.findByContent_id(cid);
	}

}