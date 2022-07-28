package com.skillguildapp.skillguild.services;

import java.util.List;

import com.skillguildapp.skillguild.entities.Resource;

public interface ResourceService {

	List<Resource> index();

	Resource show(int rid);

	Resource create(int rtid, Resource resource);
	
	boolean delete(int rid);
	
	Resource update(int rid, Resource resource);

}
