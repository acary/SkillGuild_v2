package com.skilldistillery.skillguild.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.skillguild.entities.ResourceType;

public interface ResourceTypeRepository extends JpaRepository<ResourceType, Integer> {

}
