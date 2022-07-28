package com.skillguildapp.skillguild.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skillguildapp.skillguild.entities.Comment;
import com.skillguildapp.skillguild.entities.Content;
import com.skillguildapp.skillguild.entities.User;
import com.skillguildapp.skillguild.repositories.CommentRepository;
import com.skillguildapp.skillguild.repositories.ContentRepository;
import com.skillguildapp.skillguild.repositories.UserRepository;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	CommentRepository commentRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	ContentRepository contentRepo;

	@Override
	public List<Comment> index() {
		return commentRepo.findAll();
	}

	@Override
	public Comment show(int cid) {

		Optional<Comment> op = commentRepo.findById(cid);
		if (op.isPresent()) {
			Comment result = op.get();
			return result;
		}

		return null;
	}
	
	@Override
	public List<Comment> showContentComments(int contentId) {
		return commentRepo.findByContent_id(contentId);
	}

//	@Override
//	public Comment create(int uid, int contentId, Comment comment) {
//
//		Optional<User> userOp = userRepo.findById(uid);
//		if (userOp.isPresent()) {
//			User user = userOp.get();
//			comment.setUser(user);
//
//			Optional<Content> contentOp = contentRepo.findById(contentId);
//			if (contentOp.isPresent()) {
//				Content content = contentOp.get();
//				comment.setContent(content);
//
//				return commentRepo.saveAndFlush(comment);
//			}
//		}
//
//		return null;
//	}
	
	@Override
	public Comment create(int contentId, Comment comment, String username) {
		
		User user = userRepo.findByUsername(username);
		if (user != null) {
			comment.setUser(user);
			
			Optional<Content> contentOp = contentRepo.findById(contentId);
			if (contentOp.isPresent()) {
				Content content = contentOp.get();
				comment.setContent(content);
				
				return commentRepo.saveAndFlush(comment);
			}
		}
		
		return null;
	}

	@Override
	public Comment update(int cid, Comment comment) {

		Optional<Comment> op = commentRepo.findById(cid);
		if (op.isPresent()) {
			Comment result = op.get();
			comment.setId(cid);
			comment.setUser(result.getUser());
			comment.setContent(result.getContent());
			return commentRepo.saveAndFlush(comment);
		}

		return null;
	}
	
	@Override
	public boolean delete(int cid) {

		Optional<Comment> op = commentRepo.findById(cid);
		if (op.isPresent()) {
			commentRepo.deleteById(cid);
			op = commentRepo.findById(cid);
			return !op.isPresent();

		}
		return false;
	}

}
