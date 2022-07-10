package com.skilldistillery.skillguild.controllers;

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

import com.skilldistillery.skillguild.entities.Topic;
import com.skilldistillery.skillguild.services.TopicService;

@RestController
@CrossOrigin({ "*", "http://localhost" })
@RequestMapping("v1")
public class TopicController {

	@Autowired
	TopicService topicServ;

	@GetMapping("topics")
	public List<Topic> index() {
		return topicServ.index();
	}

	@GetMapping("topics/{tid}")
	public Topic show(HttpServletRequest req, HttpServletResponse res, @PathVariable int tid) {

		Topic topic = topicServ.show(tid);
		if (topic == null) {
			res.setStatus(404);
		}
		return topic;
	}

	@PostMapping("topics")
	public Topic create(HttpServletRequest req, HttpServletResponse res, @RequestBody Topic topic) {

		try {
			topicServ.create(topic);
			res.setStatus(201);
			StringBuffer url = req.getRequestURL();
			res.setHeader("Location", url.toString());
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus(400);

		}
		return topic;
	}

	@DeleteMapping("topics/{topicId}")
	public boolean deleteTopic(@PathVariable Integer topicId, HttpServletResponse res) {
		if (topicServ.delete(topicId)) {
			res.setStatus(204);
			return true;
		} else {
			res.setStatus(404);
			return false;
		}
	}

	@PutMapping("topics/{tid}")
	public Topic updateTopicd(
			// Principal principal,
			@PathVariable("tid") int tid, @RequestBody Topic topic, HttpServletResponse res) {
		try {
			topic = topicServ.update(tid, topic);
			if (topic == null) {
				res.setStatus(404);
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus(400);
			topic = null;
		}
		return topic;
	}

}
