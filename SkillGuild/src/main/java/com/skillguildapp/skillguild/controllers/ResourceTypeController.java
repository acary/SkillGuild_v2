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

import com.skillguildapp.skillguild.entities.ResourceType;
import com.skillguildapp.skillguild.services.ResourceTypeService;

@RestController
@CrossOrigin({ "*", "http://localhost" })
@RequestMapping("v1")
public class ResourceTypeController {

	@Autowired
	ResourceTypeService resourceTypeServ;

	@GetMapping("resourcetypes")
	public List<ResourceType> index() {
		return resourceTypeServ.index();
	}

	@GetMapping("resourcetypes/{rid}")
	public ResourceType show(HttpServletRequest req, HttpServletResponse res, @PathVariable int rid) {

		ResourceType resource = resourceTypeServ.show(rid);

		if (resource == null) {
			res.setStatus(404);
		}

		return resource;
	}

	@PostMapping("resourcetypes")
	public ResourceType create(HttpServletRequest req, HttpServletResponse res,
			@RequestBody ResourceType resourceType) {

		try {
			resourceTypeServ.create(resourceType);
			res.setStatus(201);
			StringBuffer url = req.getRequestURL();
			res.setHeader("Location", url.toString());
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus(400);
		}
		return resourceType;

	}

	@DeleteMapping("resourcetypes/{rid}")
	public void delete(HttpServletRequest req, HttpServletResponse res, @PathVariable int rid) {

		try {
			if (resourceTypeServ.delete(rid)) {

				res.setStatus(204);

			} else {

				res.setStatus(404);

			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus(400);
		}

	}

	@PutMapping("resourcetypes/{rid}")
	public ResourceType updateResource(
			// Principal principal,
			@PathVariable("rid") int rid, @RequestBody ResourceType resourceType, HttpServletResponse res) {
		try {
			resourceType = resourceTypeServ.update(rid, resourceType);
			if (resourceType == null) {
				res.setStatus(404);
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus(400);
			resourceType = null;
		}
		return resourceType;
	}
}
