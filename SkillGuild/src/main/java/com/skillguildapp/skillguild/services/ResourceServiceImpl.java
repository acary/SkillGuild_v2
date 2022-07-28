package com.skillguildapp.skillguild.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skillguildapp.skillguild.entities.Resource;
import com.skillguildapp.skillguild.entities.ResourceType;
import com.skillguildapp.skillguild.repositories.ContentRepository;
import com.skillguildapp.skillguild.repositories.ResourceRepository;
import com.skillguildapp.skillguild.repositories.ResourceTypeRepository;

@Service
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	ResourceRepository resourceRepo;

	@Autowired
	ContentRepository contentRepo;

	@Autowired
	ResourceTypeRepository resourceTypeRepo;

	@Override
	public List<Resource> index() {
		return resourceRepo.findAll();
	}

	@Override
	public Resource show(int rid) {

		Optional<Resource> op = resourceRepo.findById(rid);
		if (op.isPresent()) {
			Resource result = op.get();
			return result;
		}

		return null;
	}

	public Resource create(int rtid, Resource resource) {

		Optional<ResourceType> op = resourceTypeRepo.findById(rtid);
		if (op.isPresent()) {
			ResourceType resourceType = op.get();
			resource.setResourceType(resourceType);
			return resourceRepo.saveAndFlush(resource);
		}

		return null;

	}

	@Override
	public boolean delete(int rid) {

		Optional<Resource> op = resourceRepo.findById(rid);
		if (op.isPresent()) {
			resourceRepo.deleteById(rid);
			op = resourceRepo.findById(rid);
			return !op.isPresent();

		}
		return false;
	}
	
	@Override
	public Resource update(int tid, Resource Resource) {
		Optional<Resource> op = resourceRepo.findById(tid);
		if (op.isPresent()) {
			Resource result = op.get();
			result = Resource;
			result.setId(tid);
			return resourceRepo.saveAndFlush(result);
		}
		return null;
	}

}
