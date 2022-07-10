package com.skilldistillery.skillguild.services;

import java.util.List;

import com.skilldistillery.skillguild.entities.Resource;

public interface ResourceService {

	List<Resource> index();

	Resource show(int rid);

	Resource create(int rtid, Resource resource);
	
	boolean delete(int rid);
	
	Resource update(int rid, Resource resource);

}
