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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillguildapp.skillguild.entities.InteractionType;
import com.skillguildapp.skillguild.services.InteractionTypeService;

@RestController
@CrossOrigin({ "*", "http://localhost" })
@RequestMapping("v1")
public class InteractionTypeController {

	@Autowired
	InteractionTypeService itServ;

	@GetMapping("interactiontypes")
	public List<InteractionType> index() {
		return itServ.index();
	}


	@PostMapping("interactiontypes/{id}")
	public InteractionType create(HttpServletRequest req, HttpServletResponse res, @PathVariable int id,
			@RequestBody InteractionType interactionType) {
		try {
			itServ.create(interactionType);
			res.setStatus(201);
			StringBuffer url = req.getRequestURL();
			res.setHeader("Location", url.toString());
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus(400);
		}
		return interactionType;
	}
	

	@DeleteMapping("interactiontypes/{id}")
	public void delete(HttpServletRequest req, HttpServletResponse res, @PathVariable int id) {

		try {
			if (itServ.delete(id)) {

				res.setStatus(204);
			} else {

				res.setStatus(404);
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus(400);
		}

	}

}
