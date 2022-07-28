package com.skillguildapp.skillguild.services;

import java.util.List;

import com.skillguildapp.skillguild.entities.InteractionType;

public interface InteractionTypeService {
	
	List<InteractionType> index();
	InteractionType create(InteractionType interactionType);
	boolean delete(int itId);

}
