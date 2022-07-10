package com.skilldistillery.skillguild.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.skilldistillery.skillguild.entities.User;
import com.skilldistillery.skillguild.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PasswordEncoder encoder;

	@Override
	public List<User> index() {
		return userRepo.findAll();
	}

	@Override
	public User show(int userId, String username) {

		Optional<User> userOpt = userRepo.findById(userId);

		boolean isAdmin = userRepo.findByUsername(username).getRole().equals("data_admin");

		System.err.println(userOpt.isPresent());

		if (userOpt.isPresent()) {

			User user = userOpt.get();

			if (user.getUsername().equals(username) || isAdmin) {

				return user;

			}

		}

		return null;

	}

	@Override
	public User showProfile(String username) {

		User user = userRepo.findByUsername(username);

		if (user != null) {

			return user;

		}

		return null;

	}

	@Override
	public boolean deleteUser(int userId) {
		Optional<User> userOpt = userRepo.findById(userId);
		if (userOpt.isPresent()) {
			User user = userOpt.get();
			userRepo.delete(user);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public User updateAsAdmin(int uid, User user, String username) {

		User requestingUser = userRepo.findByUsername(username);

		if (requestingUser != null) {

			if (requestingUser.getRole().equals("data_admin")) {

				Optional<User> op = userRepo.findById(uid);
				User result = op.get();
				if (op.isPresent()) {
					result = user;
					result.setId(uid);
					
					if (user.getPassword() == null || user.getPassword().equals("")) {

						result.setPassword(userRepo.findById(uid).get().getPassword());

					} else {
						
						result.setPassword(encoder.encode(user.getPassword()));

					}

				}
				return userRepo.saveAndFlush(result);
			}
		}
		return null;
	}

	@Override
	public User updateAsUser(User user, String username) {

		User requestingUser = userRepo.findByUsername(username);

		if (requestingUser != null) {

			if (requestingUser.getUsername().equals(username)) {

				requestingUser.setFirstName(user.getFirstName());
				requestingUser.setLastName(user.getLastName());
				requestingUser.setEmail(user.getEmail());
				requestingUser.setProfileImgUrl(user.getProfileImgUrl());
				requestingUser.setAboutMe(user.getAboutMe());

				return userRepo.saveAndFlush(requestingUser);
			}
		}
		return null;
	}

}
