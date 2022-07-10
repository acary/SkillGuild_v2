package com.skilldistillery.skillguild.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.skillguild.entities.Status;

public interface StatusRepository extends JpaRepository <Status, Integer> {

}
