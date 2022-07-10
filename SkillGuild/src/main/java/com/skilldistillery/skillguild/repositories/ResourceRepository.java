package com.skilldistillery.skillguild.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.skillguild.entities.Resource;

public interface ResourceRepository extends JpaRepository<Resource, Integer> {

}
