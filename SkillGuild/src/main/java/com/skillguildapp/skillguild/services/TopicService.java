package com.skillguildapp.skillguild.services;

import java.util.List;

import com.skillguildapp.skillguild.entities.Topic;

public interface TopicService {

	List<Topic> index();

	Topic show(int tid);

	Topic create(Topic topic);
	
	boolean delete(int topicId);

	Topic update(int tid, Topic topic);
}
