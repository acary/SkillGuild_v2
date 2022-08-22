package com.skillguildapp.skillguild.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillguildapp.skillguild.entities.Category;
import com.skillguildapp.skillguild.services.CategoryService;

@RestController
@CrossOrigin({ "*", "http://localhost" })
@RequestMapping("v1")
public class CategoryController {

	@Autowired
	CategoryService catServ;

	@GetMapping("categories")
	public List<Category> index() {
		return catServ.index();
	}

	@GetMapping("categories/{cid}")
	public Category show(HttpServletRequest req, HttpServletResponse res, @PathVariable int cid) {

		Category category = catServ.show(cid);

		if (category == null) {
			res.setStatus(404);
		}

		return category;
	}

	@PostMapping("categories")
	public Category create(HttpServletRequest req, HttpServletResponse res, @RequestBody Category category) {

		Category newCategory = new Category();
		try {
			newCategory = category;
			catServ.create(newCategory);
			res.setStatus(201);
			StringBuffer url = req.getRequestURL();
			res.setHeader("Location", url.toString());
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus(400);
		}
		return newCategory;
	}

	@DeleteMapping("categories/{cid}")
	public boolean deleteCategory(@PathVariable Integer cid, HttpServletResponse res) {
		if (catServ.delete(cid)) {
			res.setStatus(204);
			return true;
		} else {
			res.setStatus(404);
			return false;
		}
	}

	@PutMapping("categories/{cid}")
	public Category updateCategory(
			// Principal principal,
			@PathVariable("cid") int cid, @RequestBody Category category, HttpServletResponse res) {
		Category updateCategory = new Category();
		updateCategory = category;
		try {
			updateCategory = catServ.update(cid, updateCategory);
			if (updateCategory == null) {
				res.setStatus(404);
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus(400);
			updateCategory = null;
		}
		return updateCategory;
	}

}
