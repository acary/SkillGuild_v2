package com.skillguildapp.skillguild.services;

import com.skillguildapp.skillguild.entities.User;

public interface AuthService {
	public User register(User user);
	public User getUserByUsername(String username);
}
