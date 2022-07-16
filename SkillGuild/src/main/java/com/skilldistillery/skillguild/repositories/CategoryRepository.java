package com.skilldistillery.skillguild.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.skilldistillery.skillguild.entities.Category;

public interface CategoryRepository extends JpaRepository <Category, Integer> {

	@Query("select cat from Category cat where cat.name = :name")
	Category getCategoryByName(@Param("name") String name);

}
