package com.skillguildapp.skillguild.services;

import java.util.List;

import com.skillguildapp.skillguild.entities.Category;

public interface CategoryService {

	List<Category> index();

	Category show(int cid);

	boolean delete(int cid);

	Category update(int cid, Category category);

	Category create(Category category);

}
