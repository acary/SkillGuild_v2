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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillguildapp.skillguild.entities.User;
import com.skillguildapp.skillguild.services.UserService;

@RestController
@RequestMapping("v1")
@CrossOrigin({ "*", "http://localhost" })
public class UserController {

	@Autowired
	private UserService userSvc;

	@GetMapping("users")
	public List<User> index() {
		return userSvc.index();
	}

	@GetMapping("users/{userId}")
	private User getById(Principal principal, @PathVariable int userId, HttpServletRequest req, HttpServletResponse res) {
		User user = userSvc.show(userId, principal.getName());
		if (user == null) {
			res.setStatus(404);
		}
		return user;
	}
	
	@GetMapping("users/profile")
	private User getByCredentials(Principal principal, HttpServletRequest req, HttpServletResponse res) {
		User user = userSvc.showProfile(principal.getName());
		if (user == null) {
			res.setStatus(404);
		}
		return user;
	}

	@DeleteMapping("users/{userId}")
	public boolean deleteUser(@PathVariable Integer userId, HttpServletResponse res) {
		if (userSvc.deleteUser(userId)) {
			res.setStatus(204);
			return true;
		} else {
			res.setStatus(404);
			return false;
		}
	}

	@PutMapping("users/{uid}")
	public User updateAsAdmin(
			 Principal principal,
			@PathVariable("uid") int uid, @RequestBody User user, HttpServletResponse res) {
		try {
			
			user = userSvc.updateAsAdmin(uid, user, principal.getName());
			if (user == null) {
				res.setStatus(404);
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus(400);
			user = null;
		}
		return user;
	}
	
	@PutMapping("users/editprofile")
	public User updateAsUser(Principal principal, @RequestBody User user, HttpServletResponse res) {
		
		try {
			
			user = userSvc.updateAsUser(user, principal.getName());
			if (user == null) {
				res.setStatus(404);
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus(400);
			user = null;
		}
		return user;
	}

}

//	// SMOKE TEST ONLY, DELETE/COMMENT OUT LATER
//	@GetMapping("test/users/{userId}")
//	public User getUserForTest(
//	  @PathVariable Integer userId,
//	  HttpServletResponse res
//	) {
//	  User user = userSvc.getUserById(userId);
//	  if (user == null) {
//	    res.setStatus(404);
//	  }
//	  return user;
//	}
