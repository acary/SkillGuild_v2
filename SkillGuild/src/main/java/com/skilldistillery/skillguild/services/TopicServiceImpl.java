package com.skilldistillery.skillguild.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.skillguild.entities.Topic;
import com.skilldistillery.skillguild.repositories.ContentRepository;
import com.skilldistillery.skillguild.repositories.TopicRepository;

@Service
public class TopicServiceImpl implements TopicService {

	@Autowired
	TopicRepository topicRepo;

	@Autowired
	ContentRepository contentRepo;

	@Override
	public List<Topic> index() {
		return topicRepo.findAll();
	}

	@Override
	public Topic show(int tid) {

		Optional<Topic> op = topicRepo.findById(tid);
		if (op.isPresent()) {
			Topic result = op.get();
			return result;
		}
		return null;
	}

	@Override
	public Topic create(Topic topic) {
		return topicRepo.saveAndFlush(topic);
	}

	@Override
	public boolean delete(int topicId) {
		Optional<Topic> topicOpt = topicRepo.findById(topicId);
		if (topicOpt.isPresent()) {
			Topic topic = topicOpt.get();
			topicRepo.delete(topic);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Topic update(int tid, Topic topic) {
		Optional<Topic> op = topicRepo.findById(tid);
		if (op.isPresent()) {
			Topic result = op.get();
			result = topic;
			result.setId(tid);
			return topicRepo.saveAndFlush(result);
		}
		return null;
	}

}
