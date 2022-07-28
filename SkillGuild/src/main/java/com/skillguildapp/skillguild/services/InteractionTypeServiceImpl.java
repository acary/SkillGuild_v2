package com.skillguildapp.skillguild.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skillguildapp.skillguild.entities.InteractionType;
import com.skillguildapp.skillguild.repositories.InteractionTypeRepository;

@Service
public class InteractionTypeServiceImpl implements InteractionTypeService {

	@Autowired
	InteractionTypeRepository interactionTypeRepo;

	@Override
	public List<InteractionType> index() {
		return interactionTypeRepo.findAll();
	}


	@Override
	public InteractionType create(InteractionType interactionType) {
		return interactionTypeRepo.saveAndFlush(interactionType);
	
	}

	@Override
	public boolean delete(int cid) {

		Optional<InteractionType> op = interactionTypeRepo.findById(cid);
		if (op.isPresent()) {
			interactionTypeRepo.deleteById(cid);
			op = interactionTypeRepo.findById(cid);
			return !op.isPresent();

		}
		return false;
	}

}
