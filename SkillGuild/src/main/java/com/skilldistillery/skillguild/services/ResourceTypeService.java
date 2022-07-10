package com.skilldistillery.skillguild.services;

import java.util.List;

import com.skilldistillery.skillguild.entities.ResourceType;

public interface ResourceTypeService {

	List<ResourceType> index();
	
	ResourceType show(int rid);

	ResourceType create(ResourceType resourceType);
	
	boolean delete(int rid);
	
	ResourceType update(int rid, ResourceType resourceType);

}
