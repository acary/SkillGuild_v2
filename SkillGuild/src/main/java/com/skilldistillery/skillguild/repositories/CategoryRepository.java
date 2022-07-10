package com.skilldistillery.skillguild.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.skillguild.entities.Category;

public interface CategoryRepository extends JpaRepository <Category, Integer> {

}
