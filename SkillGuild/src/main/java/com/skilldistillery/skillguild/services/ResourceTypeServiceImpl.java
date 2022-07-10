package com.skilldistillery.skillguild.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.skillguild.entities.ResourceType;
import com.skilldistillery.skillguild.repositories.ResourceTypeRepository;

@Service
public class ResourceTypeServiceImpl implements ResourceTypeService {

	@Autowired
	ResourceTypeRepository resourceTypeRepo;

	@Override
	public List<ResourceType> index() {
		return resourceTypeRepo.findAll();
	}

	@Override
	public ResourceType show(int ResourceTypeId) {
		Optional<ResourceType> ResourceTypeOpt = resourceTypeRepo.findById(ResourceTypeId);
		if (ResourceTypeOpt.isPresent()) {
			return ResourceTypeOpt.get();
		}
		return null;
	}

	@Override
	public boolean delete(int ResourceTypeId) {
		Optional<ResourceType> ResourceTypeOpt = resourceTypeRepo.findById(ResourceTypeId);
		if (ResourceTypeOpt.isPresent()) {
			ResourceType ResourceType = ResourceTypeOpt.get();
			resourceTypeRepo.delete(ResourceType);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public ResourceType update(int rid, ResourceType ResourceType) {
		Optional<ResourceType> op = resourceTypeRepo.findById(rid);
		if (op.isPresent()) {
			ResourceType result = op.get();
			result = ResourceType;
			result.setId(rid);
			return resourceTypeRepo.saveAndFlush(result);
		}
		return null;
	}

	public ResourceType create(ResourceType resource) {

		return resourceTypeRepo.saveAndFlush(resource);
	}

}
