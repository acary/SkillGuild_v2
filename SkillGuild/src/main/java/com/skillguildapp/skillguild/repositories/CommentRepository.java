package com.skillguildapp.skillguild.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skillguildapp.skillguild.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>{
	
	List<Comment> findByContent_id(int contentId);

}
