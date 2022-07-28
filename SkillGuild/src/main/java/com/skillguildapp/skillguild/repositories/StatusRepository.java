package com.skillguildapp.skillguild.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skillguildapp.skillguild.entities.Status;

public interface StatusRepository extends JpaRepository <Status, Integer> {

}
