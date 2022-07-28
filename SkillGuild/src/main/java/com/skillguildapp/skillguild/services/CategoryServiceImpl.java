package com.skillguildapp.skillguild.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skillguildapp.skillguild.entities.Category;
import com.skillguildapp.skillguild.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepository categoryRepo;

	@Override
	public List<Category> index() {
		return categoryRepo.findAll();
	}

	@Override
	public Category show(int qid) {
		Optional<Category> op = categoryRepo.findById(qid);
		if (op.isPresent()) {
			Category result = op.get();
			return result;
		}
		return null;
	}

	@Override
	public Category create(Category category) {
		return categoryRepo.saveAndFlush(category);
	}

	@Override
	public Category update(int cid, Category category) {
		Optional<Category> op = categoryRepo.findById(cid);
		if (op.isPresent()) {
			Category result = op.get();
			result.setId(cid);
			result.setName(category.getName());
			result.setDescription(category.getDescription());
			result.setImgUrl(category.getImgUrl());
			return categoryRepo.saveAndFlush(result);
		}
		return null;
	}

	@Override
	public boolean delete(int cid) {
		Optional<Category> opt = categoryRepo.findById(cid);
		if (opt.isPresent()) {
			Category category = opt.get();
			categoryRepo.delete(category);
			return true;
		} else {
			return false;
		}
	}

}