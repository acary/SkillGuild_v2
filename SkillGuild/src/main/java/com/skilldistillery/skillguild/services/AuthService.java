package com.skilldistillery.skillguild.services;

import com.skilldistillery.skillguild.entities.User;

public interface AuthService {
	public User register(User user);
	public User getUserByUsername(String username);
}
