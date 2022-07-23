package com.skilldistillery.skillguild.controllers;

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

import com.skilldistillery.skillguild.entities.Comment;
import com.skilldistillery.skillguild.services.CommentService;

@RestController
@CrossOrigin({ "*", "http://localhost" })
@RequestMapping("v1")
public class CommentController {

	@Autowired
	CommentService commentServ;

	@GetMapping("comments")
	public List<Comment> index() {
		return commentServ.index();
	}

	@GetMapping("comments/{cid}")
	public Comment show(HttpServletRequest req, HttpServletResponse res, @PathVariable int cid) {

		Comment comment = commentServ.show(cid);

		if (comment == null) {
			res.setStatus(404);
		}

		return comment;
	}
	
	@GetMapping("contents/{contentId}/comments")
	public List<Comment> showContentComments(Principal principal, HttpServletRequest req, HttpServletResponse res,
			@PathVariable int contentId) {
		
		List<Comment> comments = commentServ.showContentComments(contentId);
		
		if (comments.isEmpty()) {
			res.setStatus(404);
		}
		
		return comments;
	}
	
	@PostMapping("contents/{contentId}/comments")
	public Comment create(Principal principal, HttpServletRequest req, HttpServletResponse res,
			@PathVariable int contentId, @RequestBody Comment comment) {
		
		try {
			commentServ.create(contentId, comment, principal.getName());
			res.setStatus(201);
			StringBuffer url = req.getRequestURL().append("/").append(comment.getId());
			res.setHeader("Location", url.toString());
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus(400);
			
		}
		
		return comment;
		
	}

	@PutMapping("comments/{cid}")
	public Comment update(HttpServletRequest req, HttpServletResponse res, @PathVariable int cid,
			@RequestBody Comment comment) {

		// System.out.println("*** Comment: " + comment);
		Comment newComment;
		try {
			newComment = commentServ.update(cid, comment);
			if (newComment == null) {
				res.setStatus(404);
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus(400);
			newComment = null;
		}

		return newComment;

	}

	@DeleteMapping("comments/{cid}")
	public void destroy(HttpServletRequest req, HttpServletResponse res, @PathVariable int cid) {

		try {
			if (commentServ.delete(cid)) {

				res.setStatus(204);

			} else {

				res.setStatus(404);

			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus(400);
		}

	}

}
