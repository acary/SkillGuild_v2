package com.skillguildapp.skillguild.controllers;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillguildapp.skillguild.entities.Comment;
import com.skillguildapp.skillguild.entities.Question;
import com.skillguildapp.skillguild.services.QuestionService;

@RestController
@CrossOrigin({ "*", "http://localhost" })
@RequestMapping("v1")
public class QuestionController {

	@Autowired
	QuestionService questionServ;

	@GetMapping("questions")
	public List<Question> index() {
		return questionServ.index();
	}

	@GetMapping("questions/{qid}")
	public Question show(HttpServletRequest req, HttpServletResponse res, @PathVariable int qid) {

		Question question = questionServ.show(qid);

		if (question == null) {
			res.setStatus(404);
		}

		return question;
	}

	@PostMapping("questions/{cid}")
	public Question create(HttpServletRequest req, HttpServletResponse res, @PathVariable int cid,
			@RequestBody Question newQuestion) {
		try {
			questionServ.create(cid, newQuestion);
			res.setStatus(201);
			StringBuffer url = req.getRequestURL();
			res.setHeader("Location", url.toString());
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus(400);
		}
		return newQuestion;
	}

	@DeleteMapping("questions/{qid}")
	public boolean deleteQuestion(@PathVariable Integer qid, HttpServletResponse res) {
		if (questionServ.delete(qid)) {
			res.setStatus(204);
			return true;
		} else {
			res.setStatus(404);
			return false;
		}
	}

	@PutMapping("questions/{qid}")
	public Question updateQuestion(
			// Principal principal,
			@PathVariable("qid") int qid, @RequestBody Question question, HttpServletResponse res) {
		try {
			question = questionServ.update(qid, question);
			if (question == null) {
				res.setStatus(404);
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus(400);
			question = null;
		}
		return question;
	}
	
	@GetMapping("questions/{cid}/questions")
	public List<Question> showContentQuestions(Principal principal, HttpServletRequest req, HttpServletResponse res,
			@PathVariable int cid) {
		
		List<Question> questions = questionServ.showContentQuestions(cid);
		
		if (questions.isEmpty()) {
			res.setStatus(404);
		}
		
		return questions;
	}

}
