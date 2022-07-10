package com.skilldistillery.skillguild.services;

import java.util.List;

import com.skilldistillery.skillguild.entities.InteractionType;

public interface InteractionTypeService {
	
	List<InteractionType> index();
	InteractionType create(InteractionType interactionType);
	boolean delete(int itId);

}
