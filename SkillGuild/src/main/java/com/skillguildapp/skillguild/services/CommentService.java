package com.skillguildapp.skillguild.services;

import java.util.List;

import com.skillguildapp.skillguild.entities.Comment;

public interface CommentService {
	
	List<Comment> index();
	List<Comment> showContentComments(int contentId);
	Comment show(int cid);
//	Comment create(int uid, int contentId, Comment comment);
	Comment create(int contentId, Comment comment, String username);
	Comment update(int cid, Comment comment);
	boolean delete(int cid);

}
