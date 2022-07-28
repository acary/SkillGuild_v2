package com.skillguildapp.skillguild.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skillguildapp.skillguild.entities.Resource;

public interface ResourceRepository extends JpaRepository<Resource, Integer> {

}
