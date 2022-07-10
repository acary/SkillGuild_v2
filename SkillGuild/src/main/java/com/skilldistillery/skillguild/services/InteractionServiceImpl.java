package com.skilldistillery.skillguild.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.skillguild.entities.Interaction;
import com.skilldistillery.skillguild.entities.Content;
import com.skilldistillery.skillguild.entities.User;
import com.skilldistillery.skillguild.repositories.InteractionRepository;
import com.skilldistillery.skillguild.repositories.ContentRepository;
import com.skilldistillery.skillguild.repositories.UserRepository;

@Service
public class InteractionServiceImpl implements InteractionService {

	@Autowired
	InteractionRepository intRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	ContentRepository contentRepo;

	@Override
	public List<Interaction> index() {
		return intRepo.findAll();
	}

	@Override
	public Interaction show(int iid) {

		Optional<Interaction> op = intRepo.findById(iid);
		if (op.isPresent()) {
			Interaction result = op.get();
			return result;
		}

		return null;
	}

	@Override
	public Interaction create(int uid, int contentId, Interaction interaction) {

		Optional<User> userOp = userRepo.findById(uid);
		if (userOp.isPresent()) {
			User user = userOp.get();
			interaction.setUser(user);

			Optional<Content> contentOp = contentRepo.findById(contentId);
			if (contentOp.isPresent()) {
				Content content = contentOp.get();
				interaction.setContent(content);

				return intRepo.saveAndFlush(interaction);
			}
		}

		return null;
	}

	@Override
	public Interaction update(int iid, Interaction interaction) {

		Optional<Interaction> op = intRepo.findById(iid);
		if (op.isPresent()) {
			Interaction result = op.get();
			interaction.setId(iid);
			interaction.setUser(result.getUser());
			interaction.setContent(result.getContent());
			return intRepo.saveAndFlush(interaction);
		}

		return null;
	}
	
	@Override
	public boolean delete(int iid) {

		Optional<Interaction> op = intRepo.findById(iid);
		if (op.isPresent()) {
			intRepo.deleteById(iid);
			op = intRepo.findById(iid);
			return !op.isPresent();

		}
		return false;
	}

//	@Override
//	public List<Interaction> showUserInteractions(int uid) {
//		return intRepo.findByUserId(uid);
//	}
//
//	@Override
//	public List<Interaction> showContentInteractions(int cid) {
//		return intRepo.findbyContentId(cid);
//	}

}
