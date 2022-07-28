package com.skillguildapp.skillguild.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skillguildapp.skillguild.entities.ResourceType;

public interface ResourceTypeRepository extends JpaRepository<ResourceType, Integer> {

}
