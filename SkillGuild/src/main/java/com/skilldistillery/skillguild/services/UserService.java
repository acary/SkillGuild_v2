package com.skilldistillery.skillguild.services;

import java.util.List;

import com.skilldistillery.skillguild.entities.User;

public interface UserService {

	List<User> index();

	User show(int uid, String username);
	
	User showProfile(String username);

	boolean deleteUser(int userId);

	User updateAsAdmin(int uid, User user, String username);
	
	User updateAsUser(User user, String username);

}
